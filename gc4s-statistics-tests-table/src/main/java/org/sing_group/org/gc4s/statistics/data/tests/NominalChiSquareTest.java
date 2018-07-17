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
package org.sing_group.org.gc4s.statistics.data.tests;

import static java.util.Optional.of;
import static org.apache.commons.math3.stat.inference.TestUtils.chiSquareTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.sing_group.org.gc4s.statistics.data.FeatureValues;

/**
 * A {@code Test<String>} implementation to compute the Chi-squared test on
 * feature values of string, that is, with nominal values.
 *
 * @author hlfernandez
 *
 */
public class NominalChiSquareTest implements Test<String> {

	@Override
	public double test(FeatureValues<String> values) {

		Set<String> nominalValuesSet = new HashSet<>();
		Map<String, Map<String, Integer>> conditionsCounts = new HashMap<>();

		for(String condition : values.getConditionNames()) {
			for(String value : values.getConditionValues(condition)) {
				nominalValuesSet.add(value);
				conditionsCounts.putIfAbsent(condition, new HashMap<>());
				conditionsCounts.get(condition).put(
					value,
					conditionsCounts.get(condition).getOrDefault(value, 0) + 1
				);
			}
		}

		String[] nominalValues = nominalValuesSet
			.toArray(new String[nominalValuesSet.size()]);

		int conditionNamesLength = values.getConditionNames().length;
		long[][] counts =
			new long[nominalValues.length][conditionNamesLength];

		for (int column = 0; column < conditionNamesLength; column++) {
			for (int row = 0; row < nominalValues.length; row++) {
				counts[row][column] = conditionsCounts
					.get(values.getConditionNames()[column])
					.getOrDefault(nominalValues[row], 0);
			}
		}

		return chiSquareTest(counts);
	}

	@Override
	public String getName() {
		return "Chi-squared test";
	}

	@Override
	public Optional<String> getAdditionalInfoUrl() {
		return of("http://www.biostathandbook.com/chiind.html");
	}
}
