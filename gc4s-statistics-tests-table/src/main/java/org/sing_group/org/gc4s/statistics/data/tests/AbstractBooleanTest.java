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

import static es.uvigo.ei.sing.math.statistical.StatisticsTestsUtils.binaryVectorToNegPos;

import java.util.List;

import org.sing_group.org.gc4s.statistics.data.FeatureValues;

import es.uvigo.ei.sing.math.statistical.UnsupportedMatrixException;
import es.uvigo.ei.sing.math.statistical.tests.TestOfIndependence;

/**
 * An abstract implementation of {@code Test<Boolean>} to apply
 * {@code TestOfIndependence} tests on feature values sets of {@code Boolean}.
 *
 * @author hlfernandez
 */
public abstract class AbstractBooleanTest implements Test<Boolean> {
	private TestOfIndependence toi;

	/**
	 * Creates a new {@code AbstractBooleanTest} instance for applying the
	 * specified {@code TestOfIndependence}.
	 *
	 * @param toi a {@code TestOfIndependence} object.
	 */
	public AbstractBooleanTest(TestOfIndependence toi) {
		this.toi = toi;
	}

	@Override
	public double test(FeatureValues<Boolean> values) {
		try {
			return this.toi.test(countsMatrix(values));
		} catch (UnsupportedMatrixException | InterruptedException e1) {
			e1.printStackTrace();
			return Double.NaN;
		}
	}

	private static final int[][] countsMatrix(FeatureValues<Boolean> values) {
		final int[][] conditionsMatrix =
			new int[2][values.getConditionNames().length];

		int cmi = 0;
		for (String condition : values.getConditionNames()) {
			final int[] negPos = binaryVectorToNegPos(
				asArray(values.getConditionValues(condition))
			);
			conditionsMatrix[0][cmi] = negPos[0];
			conditionsMatrix[1][cmi++] = negPos[1];
		}

		return conditionsMatrix;
	}

	private static final Boolean[] asArray(List<Boolean> conditionValues) {
		return conditionValues.toArray(new Boolean[conditionValues.size()]);
	}
}
