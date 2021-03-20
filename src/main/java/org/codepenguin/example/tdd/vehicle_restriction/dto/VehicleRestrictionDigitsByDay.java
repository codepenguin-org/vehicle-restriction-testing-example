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

package org.codepenguin.example.tdd.vehicle_restriction.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Set;

/**
 * Contains the restricted digits for one day of the week.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleRestrictionDigitsByDay {

    private final DayOfWeek dayOfWeek;
    private Collection<Integer> restrictedDigits;

    /**
     * Constructor.
     *
     * @param dayOfWeek        the day of the week.
     * @param restrictedDigits the set of restricted digits.
     */
    public VehicleRestrictionDigitsByDay(final DayOfWeek dayOfWeek, final Integer... restrictedDigits) {
        this(dayOfWeek, Set.of(restrictedDigits));
    }
}
