/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato, 
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.sing_group.gc4s.utilities;

import static org.sing_group.gc4s.utilities.MatrixUtils.center;
import static org.sing_group.gc4s.utilities.MatrixUtils.max;
import static org.sing_group.gc4s.utilities.MatrixUtils.mean;
import static org.sing_group.gc4s.utilities.MatrixUtils.min;
import static org.sing_group.gc4s.utilities.MatrixUtils.transform;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;
import org.sing_group.gc4s.utilities.MatrixUtils;

public class MatrixUtilsTest {

	private static final double[][] MATRIX = new double[][] {
			{1d, 2d},
			{1d, 3d},
			{1d, Double.NaN}
	};
	
	private static final double[] MEANS_NO_NANS = 
		new double[] { 1.5d, 2d, 1d};
	private static final double[] MEANS_NANS = 
		new double[] { 1.5d, 2d, Double.NaN};
	private static final double[] MEDIANS_NO_NANS = 
		new double[] { 1.5d, 2d, 1d};
	private static final double[] MEDIANS_NANS = 
		new double[] { 1.5d, 2d, Double.NaN};
	
	@Test
	public void maxTest() {
		double max = max(MATRIX);
		assertEquals(3d, max, 0d);
	}
	
	@Test
	public void minTest() {
		double min = min(MATRIX);
		assertEquals(1d, min, 0d);
	}
	
	@Test
	public void transformTest() {
		Function<Double, Double> square = (d) -> Math.pow(d, 2d);
		
		double[][] expected = new double[][] {
				{1d, 4d},
				{1d, 9d},
				{1d, Double.NaN}
		};
		
		double[][] actual = transform(MATRIX, square);
		
		assertMatrixEquals(expected, actual);
	}

	@Test
	public void meanWithoutNansTest() {
		for (int i = 0; i < MEANS_NO_NANS.length; i++) {
			double mean = mean(MATRIX[i], true);
			assertEquals(MEANS_NO_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void meanWithNansTest() {
		for (int i = 0; i < MEANS_NANS.length; i++) {
			double mean = mean(MATRIX[i], false);
			assertEquals(MEANS_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void medianWithoutNansTest() {
		for (int i = 0; i < MEDIANS_NO_NANS.length; i++) {
			double mean = mean(MATRIX[i], true);
			assertEquals(MEDIANS_NO_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void medianWithNansTest() {
		for (int i = 0; i < MEDIANS_NANS.length; i++) {
			double mean = mean(MATRIX[i], false);
			assertEquals(MEDIANS_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void meanCenteringTest() {
		double[][] actual = center(MATRIX, MatrixUtils::mean, true);
		
		double[][] expected = new double[][] {
				{-0.5d	, 0.5d},	
				{-1d	, 1d},	
				{0d		, Double.NaN}	
		};
		
		assertMatrixEquals(expected, actual);
	}
	
	private static void assertMatrixEquals(double[][] expected,
		double[][] actual
	) {
		for (int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], actual[i], 0d);
		}
	}
}
