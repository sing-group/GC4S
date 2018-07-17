/*
 * #%L
 * GC4S statistics tests table
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
package org.sing_group.org.gc4s.statistics.data;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class DefaultDatasetTest {

	private static final String A = "A";
	private static final String B = "B";
	
	private static final String F1 = "F1";
	private static final String F2 = "F2";
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String S3 = "S3";
	private static final String S4 = "S4";
	
	private final String[] features = { F1, F2 };
	private final String[] samples = { S1, S2, S3, S4 };
	private final Integer[][] data = { 
		{ 11, 12, 13, 14 }, 
		{ 21, 22, 23, 24 } 
	};
	private final String[] classNames = { A, A, B, B };

	@Test(expected = IllegalArgumentException.class)
	public void createDatasetWithoutFeaturesTest() {
		new DefaultDataset<Integer>(new String[] {}, samples, data, classNames);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createDatasetWithoutSamplesTest() {
		new DefaultDataset<Integer>(features, new String[] {}, data,
			classNames);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createDatasetWithoutDataTest() {
		new DefaultDataset<Integer>(features, samples, new Integer[0][0],
			classNames);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createDatasetWithoutClassNamesTest() {
		new DefaultDataset<Integer>(features, samples, data, new String[] {});
	}

	@Test(expected = IllegalArgumentException.class)
	public void createDatasetWithInvalidClassNamesLengthTest() {
		new DefaultDataset<Integer>(features, samples, data,
			new String[] { A, A, B, B, B });
	}

	@Test
	public void createRightDatasetTest() {
		Dataset<Integer> dataset = 
			new DefaultDataset<Integer>(features, samples, data, classNames);
		
		assertEquals(A, dataset.getCondition(S1));
		assertEquals(A, dataset.getCondition(S2));
		assertEquals(B, dataset.getCondition(S3));
		assertEquals(B, dataset.getCondition(S4));
		
		assertEquals(new Integer(11), dataset.getValue(S1, F1));
		assertEquals(new Integer(12), dataset.getValue(S2, F1));
		assertEquals(new Integer(13), dataset.getValue(S3, F1));
		assertEquals(new Integer(14), dataset.getValue(S4, F1));
		assertEquals(new Integer(21), dataset.getValue(S1, F2));
		assertEquals(new Integer(22), dataset.getValue(S2, F2));
		assertEquals(new Integer(23), dataset.getValue(S3, F2));
		assertEquals(new Integer(24), dataset.getValue(S4, F2));
		
		FeatureValues<Integer> f1Values = dataset.getFeatureValues(F1);
		assertArrayEquals(new String[] { A, B }, f1Values.getConditionNames());
		assertEquals(Arrays.asList( 11, 12 ), f1Values.getConditionValues(A));
		assertEquals(Arrays.asList( 13, 14 ), f1Values.getConditionValues(B));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongFeature() {
		Dataset<Integer> dataset = 
			new DefaultDataset<Integer>(features, samples, data, classNames);
		
		dataset.getValue(S1, "F3");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWrongSample() {
		Dataset<Integer> dataset = 
			new DefaultDataset<Integer>(features, samples, data, classNames);
		
		dataset.getValue("S100", F1);
	}
}
