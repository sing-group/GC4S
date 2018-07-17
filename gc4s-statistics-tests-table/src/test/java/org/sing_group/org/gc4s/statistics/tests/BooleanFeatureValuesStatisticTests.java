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
package org.sing_group.org.gc4s.statistics.tests;

import static org.sing_group.org.gc4s.statistics.data.util.StatisticsTestsDataUtils.randomValues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.sing_group.org.gc4s.statistics.data.DefaultFeatureValues;
import org.sing_group.org.gc4s.statistics.data.tests.MultiClassBooleanRandomizationTest;
import org.sing_group.org.gc4s.statistics.data.tests.MultipleConditionsBooleanChiSquareTest;
import org.sing_group.org.gc4s.statistics.data.tests.TwoConditionsBooleanFisherTest;
import org.sing_group.org.gc4s.statistics.data.tests.TwoConditionsBooleanYatesChiSquareTest;

public class BooleanFeatureValuesStatisticTests {

	@Test
	public void testFisher() {
		Map<String, List<Boolean>> classesValues = new HashMap<>();
		classesValues.put("Class A", Arrays.asList(true, true, true, true, true, true));
		classesValues.put("Class B", Arrays.asList(false, false, false, false, false, false));
		
		Assert.assertEquals(0.0021645021645021684,
			new TwoConditionsBooleanFisherTest().test(
				new DefaultFeatureValues<Boolean>("A", classesValues)),
			0d);
	}
	
	@Test
	public void testYatesChiSquare() {
		Map<String, List<Boolean>> classesValues = new HashMap<>();
		classesValues.put("Class A", randomValues(1200, 95, 0));
		classesValues.put("Class B", randomValues(1200, 95, 1));
		
		Assert.assertEquals(0.747203325100659,
			new TwoConditionsBooleanYatesChiSquareTest().test(
				new DefaultFeatureValues<Boolean>("A", classesValues)),
			0d);
	}
	
	@Test
	public void testMultiClassChiSquare() {
		Map<String, List<Boolean>> classesValues = new HashMap<>();
		classesValues.put("Class A", randomValues(1200, 95, 0));
		classesValues.put("Class B", randomValues(1200, 94, 1));
		classesValues.put("Class C", randomValues(1200, 93, 2));
		
		Assert.assertEquals(0.010453708035629172,
			new MultipleConditionsBooleanChiSquareTest().test(
				new DefaultFeatureValues<Boolean>("A", classesValues)),
			0d);
	}
	
	@Test
	public void testMultiClassRandomization() {
		Map<String, List<Boolean>> classesValues = new HashMap<>();
		classesValues.put("Class A", randomValues(100, 95, 0));
		classesValues.put("Class B", randomValues(100, 90, 1));
		classesValues.put("Class C", randomValues(100, 85, 2));

		Assert.assertEquals(0.0015,
			new MultiClassBooleanRandomizationTest().test(
				new DefaultFeatureValues<Boolean>("A", classesValues)),
			0d);
	}
}
