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

import java.time.LocalDateTime;

import static org.codepenguin.example.tdd.vehicle_restriction.TestConstants.EVEN_PLATE;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit test for {@link MedellinVehicleRestrictionEvaluator}.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
class MedellinVehicleRestrictionEvaluatorTest {

    private final MedellinVehicleRestrictionEvaluator evaluator = new MedellinVehicleRestrictionEvaluator();

    @Test
    void givenPlateNullWhenApplyThenReturnFalse() {
        assertFalse(evaluator.apply(null, LocalDateTime.now()));
    }

    @Test
    void givenDateTimeNullWhenApplyThenReturnFalse() {
        assertFalse(evaluator.apply(EVEN_PLATE, null));
    }

    @Test
    void givenPlateNullAndDateTimeNullWhenApplyThenReturnFalse() {
        assertFalse(evaluator.apply(null, null));
    }

    @Test
    void givenAnyPlateAndAnyDateTimeWhenApplyThenReturnFalse() {
        assertFalse(evaluator.apply(EVEN_PLATE, LocalDateTime.now()));
    }
}