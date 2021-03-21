/*
 * MIT License
 *
 * Copyright (c) 2021 codepenguin.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.codepenguin.example.tdd.vehicle_restriction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.codepenguin.example.tdd.vehicle_restriction.domain.ErrorResponse;
import org.codepenguin.example.tdd.vehicle_restriction.domain.ResponseBodyPayload;
import org.codepenguin.example.tdd.vehicle_restriction.domain.VehicleRestrictionRequest;
import org.codepenguin.example.tdd.vehicle_restriction.domain.VehicleRestrictionResponse;
import org.codepenguin.example.tdd.vehicle_restriction.service.VehicleRestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.DEFAULT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class VehicleRestrictionRestController {

    public static final Logger LOGGER = Logger.getLogger(VehicleRestrictionRestController.class.getName());

    private final VehicleRestrictionService service;

    /**
     * Constructor.
     *
     * @param service the vehicle restriction service.
     */
    @Autowired
    public VehicleRestrictionRestController(VehicleRestrictionService service) {
        this.service = service;
    }

    /**
     * Validates if a vehicle can travel according to its license plate, city and date/time indicated.
     *
     * @param body the vehicle restriction request
     * @return the vehicle restriction response, if the process is successful; otherwise, an error response.
     * @see VehicleRestrictionResponse
     * @see ErrorResponse
     */
    @Operation(
            summary = "Validates if a vehicle can travel according to its license plate, city and date/time indicated.",
            description = "Validates if a vehicle can travel according to its license plate, city and date/time indicated."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = VehicleRestrictionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping(value = "/", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBodyPayload> validate(
            @Parameter(in = DEFAULT, description = "the vehicle restriction request", schema = @Schema())
            @RequestBody VehicleRestrictionRequest body
    ) {
        LOGGER.log(Level.FINEST, "body = {0}", body);

        if (Objects.isNull(body)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Must have a request payload."));
        }

        if (Objects.isNull(body.getCity())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Must have a city."));
        }

        if (StringUtils.isBlank(body.getPlate())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Must have a plate."));
        }

        final var datetime = Objects.requireNonNullElseGet(body.getDatetime(), LocalDateTime::now);

        final boolean canDrive;
        try {
            canDrive = service.canDrive(body.getCity(), body.getPlate(), datetime);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, String.valueOf(body), e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }

        final var response = VehicleRestrictionResponse.builder().city(body.getCity())
                .plate(body.getPlate()).datetime(datetime).canDrive(canDrive).build();
        LOGGER.log(Level.FINEST, "response = {0}", response);

        return ResponseEntity.ok(response);
    }

}
