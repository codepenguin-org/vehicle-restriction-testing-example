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

package org.codepenguin.example.tdd.vehicle_restriction.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codepenguin.example.tdd.vehicle_restriction.enums.City;

import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

/**
 * Request for vehicle restrictions.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public final class VehicleRestrictionRequest implements Serializable {

    private static final long serialVersionUID = 5468385106122384524L;

    @Schema(example = "ABC123", required = true, description = "Vehicle's license plate.")
    private String plate;

    @Schema(example = "BOGOTA", required = true, description = "City where the vehicle wishes to travel.")
    private City city;

    @Schema(description = "Date and time when the vehicle wishes to transit. If this value is not settled, it'll use " +
            "the current date and time.")
    private LocalDateTime datetime;
}
