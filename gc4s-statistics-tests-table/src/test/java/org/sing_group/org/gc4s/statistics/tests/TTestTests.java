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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sing_group.org.gc4s.statistics.data.DefaultFeatureValues;
import org.sing_group.org.gc4s.statistics.data.tests.StudentsPairedTTest;
import org.sing_group.org.gc4s.statistics.data.tests.StudentsTTest;

public class TTestTests {
	@Test
	public void testT() {
		Map<String, List<Number>> classesValues = new HashMap<>();
		classesValues.put("Class A", asList(4d, 4d, 4d, 4d, 4d, 4d, 5d, 5d, 5d,
			5d, 5d, 6d, 6d, 6d, 6d, 6d, 6d));
		classesValues.put("Class B",
			asList(1d, 1d, 2d, 2d, 2d, 2d, 2d, 2d, 1.5d, 1.5d, 1d, 1.5d, 2d));
		
		assertEquals(4.192466e-13,
			new StudentsTTest().test(
				new DefaultFeatureValues<Number>("A", classesValues)),
			0.00000000001d);
	}
	
	@Test
	public void testPairedT() {
		Map<String, List<Number>> classesValues = new HashMap<>();
		classesValues.put("Class A",
			asList(4d, 4d, 4d, 4d, 4d, 4d, 5d, 5d, 5d, 5d, 5d, 6d, 6d));
		classesValues.put("Class B",
			asList(1d, 1d, 2d, 2d, 2d, 2d, 2d, 2d, 1.5d, 1.5d, 1d, 1.5d, 2d));

		assertEquals(2.260267e-08,
			new StudentsPairedTTest()
				.test(new DefaultFeatureValues<Number>("A", classesValues)),
			0.0000001d);
	}
}
