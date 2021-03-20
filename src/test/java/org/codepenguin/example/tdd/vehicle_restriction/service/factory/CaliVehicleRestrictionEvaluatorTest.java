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

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.Month.MARCH;
import static org.codepenguin.example.tdd.vehicle_restriction.TestConstants.EVEN_PLATE;
import static org.codepenguin.example.tdd.vehicle_restriction.TestConstants.NO_VALID_PLATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@link CaliVehicleRestrictionEvaluator}.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
class CaliVehicleRestrictionEvaluatorTest {

    private final CaliVehicleRestrictionEvaluator evaluator = new CaliVehicleRestrictionEvaluator();

    @Test
    void givenPlateNullWhenApplyThenThrowsNumberFormatException() {
        final var now = LocalDateTime.now();
        assertThrows(NumberFormatException.class, () -> evaluator.apply(null, now));
    }

    @Test
    void giverDateTimeNullWhenApplyThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> evaluator.apply(EVEN_PLATE, null));
    }

    @Test
    void givenNoValidPlateWhenApplyThenThrowsNumberFormatException() {
        final var now = LocalDateTime.now();
        assertThrows(NumberFormatException.class, () -> evaluator.apply(NO_VALID_PLATE, now));
    }

    @Test
    void givenValidPlateAndWeekendDateWhenApplyThenReturnsFalse() {
        final var saturday = LocalDate.of(2021, MARCH, 6);
        assertFalse(evaluator.apply(EVEN_PLATE, saturday.atStartOfDay()));
    }

    @Test
    void givenValidPlateAndNonCorrespondingWeekdayDateWhenApplyThenReturnsFalse() {
        final var monday = LocalDate.of(2021, MARCH, 1);
        assertFalse(evaluator.apply(EVEN_PLATE, monday.atStartOfDay()));
    }

    @Test
    void givenValidPlateAndCorrespondingWeekdayDateWhenApplyThenReturnsTrue() {
        final var wednesday = LocalDate.of(2021, MARCH, 3);
        assertTrue(evaluator.apply(EVEN_PLATE, wednesday.atStartOfDay()));
    }


}