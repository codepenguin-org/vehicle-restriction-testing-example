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

import org.apache.commons.lang.StringUtils;
import org.codepenguin.example.tdd.vehicle_restriction.domain.ErrorResponse;
import org.codepenguin.example.tdd.vehicle_restriction.domain.VehicleRestrictionRequest;
import org.codepenguin.example.tdd.vehicle_restriction.domain.VehicleRestrictionResponse;
import org.codepenguin.example.tdd.vehicle_restriction.enums.City;
import org.codepenguin.example.tdd.vehicle_restriction.service.VehicleRestrictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static java.time.Month.MARCH;
import static org.apache.commons.lang.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang.math.NumberUtils.INTEGER_ZERO;
import static org.codepenguin.example.tdd.vehicle_restriction.TestConstants.EVEN_PLATE;
import static org.codepenguin.example.tdd.vehicle_restriction.enums.City.BOGOTA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

/**
 * Unit tests for {@link VehicleRestrictionRestController}.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
class VehicleRestrictionRestControllerTest {

    @Mock
    private VehicleRestrictionService serviceMock;

    private VehicleRestrictionRestController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);

        controller = new VehicleRestrictionRestController(serviceMock);
    }

    @Test
    void givenBodyNullWhenValidateThenReturnsBadRequestErrorResponse() {
        final var response = controller.validate(null);
        assertNotNull(response);
        assertEquals(BAD_REQUEST, response.getStatusCode());

        final var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof ErrorResponse);

        final var errorResponse = (ErrorResponse) responseBody;
        assertTrue(StringUtils.isNotBlank(errorResponse.getMessage()));

        verify(serviceMock, times(INTEGER_ZERO)).canDrive(any(City.class), anyString(), any(LocalDateTime.class));
    }

    @Test
    void givenBodyWithCityNullWhenValidateThenReturnsBadRequestErrorResponse() {
        final var requestBody = VehicleRestrictionRequest.builder().build();
        final var response = controller.validate(requestBody);
        assertNotNull(response);
        assertEquals(BAD_REQUEST, response.getStatusCode());

        final var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof ErrorResponse);

        final var errorResponse = (ErrorResponse) responseBody;
        assertTrue(StringUtils.isNotBlank(errorResponse.getMessage()));

        verify(serviceMock, times(INTEGER_ZERO)).canDrive(any(City.class), anyString(), any(LocalDateTime.class));
    }

    @Test
    void givenBodyWithPlateBlankWhenValidateThenReturnsBadRequestErrorResponse() {
        final var requestBody = VehicleRestrictionRequest.builder().city(BOGOTA).build();
        final var response = controller.validate(requestBody);
        assertNotNull(response);
        assertEquals(BAD_REQUEST, response.getStatusCode());

        final var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof ErrorResponse);

        final var errorResponse = (ErrorResponse) responseBody;
        assertTrue(StringUtils.isNotBlank(errorResponse.getMessage()));

        verify(serviceMock, times(INTEGER_ZERO)).canDrive(any(City.class), anyString(), any(LocalDateTime.class));
    }

    @Test
    void givenServiceThrowingRuntimeExceptionWhenValidateThenReturnsInternalServerErrorErrorResponse() {
        when(serviceMock.canDrive(any(City.class), anyString(), any(LocalDateTime.class)))
                .thenThrow(new RuntimeException("ExceptionMessage"));

        final var requestBody = VehicleRestrictionRequest.builder().city(BOGOTA).plate(EVEN_PLATE)
                .build();
        final var response = controller.validate(requestBody);
        assertNotNull(response);
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());

        final var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof ErrorResponse);

        final var errorResponse = (ErrorResponse) responseBody;
        assertTrue(StringUtils.isNotBlank(errorResponse.getMessage()));

        verify(serviceMock, times(INTEGER_ONE)).canDrive(any(City.class), anyString(), any(LocalDateTime.class));
    }

    @Test
    void givenRequestWithoutDatetimeWhenValidateThenReturnsSuccessfulVehicleRestrictionResponseWithCurrentDatetime() {
        when(serviceMock.canDrive(any(City.class), anyString(), any(LocalDateTime.class))).thenReturn(true);

        final var requestBody = VehicleRestrictionRequest.builder().city(BOGOTA).plate(EVEN_PLATE)
                .build();
        final var response = controller.validate(requestBody);
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());

        final var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof VehicleRestrictionResponse);

        final var restrictionResponse = (VehicleRestrictionResponse) responseBody;
        assertEquals(requestBody.getCity(), restrictionResponse.getCity());
        assertEquals(requestBody.getPlate(), restrictionResponse.getPlate());
        assertNotNull(restrictionResponse.getDatetime());
        assertTrue(restrictionResponse.isCanDrive());

        verify(serviceMock, times(INTEGER_ONE)).canDrive(any(City.class), anyString(), any(LocalDateTime.class));
    }

    @Test
    void givenRequestWithDatetimeWhenValidateThenReturnsSuccessfulVehicleRestrictionResponseWithTheRequestDatetime() {
        when(serviceMock.canDrive(any(City.class), anyString(), any(LocalDateTime.class))).thenReturn(true);

        final var year = 2021;
        final var dayOfMonth = 3;
        final var hour = 14;
        final var minute = 30;
        final var second = 0;
        final var requestBody = VehicleRestrictionRequest.builder().city(BOGOTA).plate(EVEN_PLATE)
                .datetime(LocalDateTime.of(year, MARCH, dayOfMonth, hour, minute, second)).build();

        final var response = controller.validate(requestBody);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());

        final var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof VehicleRestrictionResponse);

        final var restrictionResponse = (VehicleRestrictionResponse) responseBody;
        assertEquals(requestBody.getCity(), restrictionResponse.getCity());
        assertEquals(requestBody.getPlate(), restrictionResponse.getPlate());
        assertEquals(requestBody.getDatetime(), restrictionResponse.getDatetime());
        assertTrue(restrictionResponse.isCanDrive());

        verify(serviceMock, times(INTEGER_ONE)).canDrive(any(City.class), anyString(), any(LocalDateTime.class));
    }
}