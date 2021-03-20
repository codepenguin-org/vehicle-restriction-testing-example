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

import org.codepenguin.example.tdd.vehicle_restriction.service.factory.VehicleRestrictionEvaluator;
import org.codepenguin.example.tdd.vehicle_restriction.service.factory.VehicleRestrictionEvaluatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static org.apache.commons.lang.math.NumberUtils.INTEGER_ONE;
import static org.codepenguin.example.tdd.vehicle_restriction.TestConstants.EVEN_PLATE;
import static org.codepenguin.example.tdd.vehicle_restriction.TestConstants.ODD_PLATE;
import static org.codepenguin.example.tdd.vehicle_restriction.enums.City.BOGOTA;
import static org.codepenguin.example.tdd.vehicle_restriction.enums.City.CALI;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link VehicleRestrictionService}.
 *
 * @author Jorge Garcia
 * @version 0.0.1
 * @since 11
 */
class VehicleRestrictionServiceTest {

    @Mock
    private VehicleRestrictionEvaluator evaluatorMock;

    @Mock
    private VehicleRestrictionEvaluatorFactory factoryMock;

    private VehicleRestrictionService service;

    @BeforeEach
    void setUp() {
        openMocks(this);

        service = new VehicleRestrictionService(factoryMock);
    }

    @Test
    void givenBogotaCityAndEvenPlateAndEvenDateWithTimeOutOfRangeWhenCanDriveThenReturnTrue() {
        when(evaluatorMock.apply(any(String.class), any(LocalDateTime.class))).thenReturn(false);
        when(factoryMock.build(BOGOTA)).thenReturn(evaluatorMock);

        assertTrue(service.canDrive(BOGOTA, EVEN_PLATE, LocalDateTime.of(2021, MAY, 12,
                4, 30, 0)));

        verify(evaluatorMock, times(INTEGER_ONE)).apply(any(String.class), any(LocalDateTime.class));
        verify(factoryMock, times(INTEGER_ONE)).build(BOGOTA);
    }

    @Test
    void givenCaliCityAndMatchingPlateAndDateWhenCanDriveThenReturnFalse() {
        when(evaluatorMock.apply(any(String.class), any(LocalDateTime.class))).thenReturn(true);
        when(factoryMock.build(CALI)).thenReturn(evaluatorMock);

        assertFalse(service.canDrive(CALI, ODD_PLATE, LocalDateTime.of(2021, MARCH, 16,
                8, 30, 0)));

        verify(evaluatorMock, times(INTEGER_ONE)).apply(any(String.class), any(LocalDateTime.class));
        verify(factoryMock, times(INTEGER_ONE)).build(CALI);
    }
}