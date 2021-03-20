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

package org.codepenguin.example.tdd.vehicle_restriction.utils;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.right;
import static org.apache.commons.lang.math.NumberUtils.INTEGER_ONE;

/**
 * Utility for String extraction.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
public final class ExtractionUtil {
    private ExtractionUtil() {
    }

    /**
     * Extracts the last character of the string as a integer.
     *
     * @param s the string. It must have a digit as last character.
     * @return the last character as an integer.
     * @throws NumberFormatException if the string is {@code null} or empty or if it doesn't end with a digit.
     */
    public static int extractLastCharacterAsInteger(final String s) {
        return parseInt(right(s, INTEGER_ONE));
    }
}
