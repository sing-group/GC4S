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

import org.sing_group.org.gc4s.statistics.data.FeatureValues;

/**
 * An abstract extension of {@code AbstractNumberTest} to compute t-tests. Note
 * that this test is used to compare two conditions, thus the test method will
 * raise an {@code IllegalArgumentException} if a different number of conditions
 * is provided. Concrete classes must implement the specific {@code test}
 * method.
 *
 * @author hlfernandez
 *
 */
public abstract class AbstractTTest extends AbstractNumberTest {
	@Override
	public double test(FeatureValues<Number> values) {
		if (values.getConditionNames().length != 2) {
			throw new IllegalArgumentException("Two conditions are required for this test");
		}

		String classA = values.getConditionNames()[0];
		String classB = values.getConditionNames()[1];

		return test(
			asDoubleArray(values.getConditionValues(classA)),
			asDoubleArray(values.getConditionValues(classB))
		);
	}

	protected abstract double test(double[] sample1, double[] sample2);
}
