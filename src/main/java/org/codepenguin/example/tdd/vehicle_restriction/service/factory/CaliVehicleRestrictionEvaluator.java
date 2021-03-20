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

import org.codepenguin.example.tdd.vehicle_restriction.dto.VehicleRestrictionDigitsByDay;
import org.codepenguin.example.tdd.vehicle_restriction.enums.City;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.codepenguin.example.tdd.vehicle_restriction.utils.ExtractionUtil.extractLastCharacterAsInteger;

/**
 * Evaluator of vehicle restrictions for Cali.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @see City#CALI
 * @since 11
 */
final class CaliVehicleRestrictionEvaluator implements VehicleRestrictionEvaluator {

    private final Collection<VehicleRestrictionDigitsByDay> restrictions = Set.of(
            new VehicleRestrictionDigitsByDay(MONDAY, 1, 2),
            new VehicleRestrictionDigitsByDay(TUESDAY, 3, 4),
            new VehicleRestrictionDigitsByDay(WEDNESDAY, 5, 6),
            new VehicleRestrictionDigitsByDay(THURSDAY, 7, 8),
            new VehicleRestrictionDigitsByDay(FRIDAY, 9, 0)
    );

    @Override
    public boolean apply(String plate, LocalDateTime dateTime) {
        return restrictions.stream()
                .anyMatch(restriction -> apply(extractLastCharacterAsInteger(plate), dateTime.getDayOfWeek(), restriction));
    }

    private boolean apply(final int lastDigit, final DayOfWeek dayOfWeek,
                          final VehicleRestrictionDigitsByDay restriction) {
        return dayOfWeek.equals(restriction.getDayOfWeek()) && restriction.getRestrictedDigits().contains(lastDigit);
    }
}
