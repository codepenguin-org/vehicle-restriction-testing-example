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

package org.codepenguin.example.tdd.vehicle_restriction.service.factory;

import org.codepenguin.example.tdd.vehicle_restriction.dto.VehicleRestrictionTimeSlot;
import org.codepenguin.example.tdd.vehicle_restriction.enums.City;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

import static org.codepenguin.example.tdd.vehicle_restriction.utils.ExtractionUtil.extractLastCharacterAsInteger;

/**
 * Evaluator of vehicle restrictions for Bogota.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @see City#BOGOTA
 * @since 11
 */
final class BogotaVehicleRestrictionEvaluator implements VehicleRestrictionEvaluator {

    private final Collection<VehicleRestrictionTimeSlot> timeSlots = Set.of(
            new VehicleRestrictionTimeSlot(LocalTime.of(6, 0), LocalTime.of(8, 30)),
            new VehicleRestrictionTimeSlot(LocalTime.of(15, 0), LocalTime.of(19, 30))
    );

    @Override
    public boolean apply(final String plate, final LocalDateTime dateTime) {
        return isEven(extractLastCharacterAsInteger(plate)) == isEven(dateTime.getDayOfMonth())
                && timeSlots.stream().anyMatch(timeSlot -> apply(dateTime.toLocalTime(), timeSlot));
    }

    private boolean isEven(final int i) {
        return i % 2 == 0;
    }

    private boolean apply(final LocalTime localTime, final VehicleRestrictionTimeSlot timeSlot) {
        return (timeSlot.getStartTime().equals(localTime) || timeSlot.getStartTime().isBefore(localTime))
                && (localTime.equals(timeSlot.getEndTime()) || localTime.isBefore(timeSlot.getEndTime()));
    }
}
