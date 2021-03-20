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

package org.codepenguin.example.tdd.vehicle_restriction.service;

import org.codepenguin.example.tdd.vehicle_restriction.enums.City;
import org.codepenguin.example.tdd.vehicle_restriction.service.factory.VehicleRestrictionEvaluatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for vehicle restrictions.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
@Service
public final class VehicleRestrictionService {

    private final VehicleRestrictionEvaluatorFactory factory;

    /**
     * Constructor.
     *
     * @param factory the vehicle restrictions evaluator's factory.
     */
    @Autowired
    public VehicleRestrictionService(VehicleRestrictionEvaluatorFactory factory) {
        this.factory = factory;
    }

    /**
     * Indicates if a vehicle can drive based on the city, its plate, and the datetime.
     *
     * @param city     the city.
     * @param plate    the vehicle's plate.
     * @param dateTime the datetime.
     * @return {@code true} if the vehicle can drive; otherwise, {@code false}.
     */
    public boolean canDrive(final City city, final String plate, final LocalDateTime dateTime) {
        return !factory.build(city).apply(plate, dateTime);
    }
}
