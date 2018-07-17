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

import static java.util.Optional.empty;

import java.util.Optional;

import org.sing_group.org.gc4s.statistics.data.FeatureValues;

/**
 * The interface that defines a statistical test that is applied to a
 * {@code FeatureValues} object.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in the set of feature values to be tested
 */
public interface Test<T> {
	/**
	 * Returns the p-value that results of the application of the statistical
	 * test to the specified {@code values}.
	 *
	 * @param values a {@code FeatureValues} object
	 * @return the p-value of the test
	 */
	public double test(FeatureValues<T> values);

	/**
	 * Returns the name of the test.
	 *
	 * @return the name of the test
	 */
	public String getName();

	/**
	 * Returns the description of the test wrapped as an {@code Optional}.
	 *
	 * @return the description of the test
	 */
	public default Optional<String> getDescription() {
		return empty();
	}

	/**
	 * Returns the additional information URL of the test wrapped as an
	 * {@code Optional}.
	 *
	 * @return the additional information URL of the test
	 */
	public default Optional<String> getAdditionalInfoUrl() {
		return empty();
	}
}
