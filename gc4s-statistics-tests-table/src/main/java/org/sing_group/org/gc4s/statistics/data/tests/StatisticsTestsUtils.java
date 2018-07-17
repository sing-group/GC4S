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

import org.sing_group.org.gc4s.statistics.data.Dataset;

/**
 * A utility class to help with the usage of tests.
 *
 * @author hlfernandez
 *
 */
public class StatisticsTestsUtils {

	/**
	 * Returns the most appropriate test for a dataset of {@code Boolean}
	 * depending on the number of conditions and samples in it.
	 *
	 * @param dataset a dataset of {@code Boolean}
	 * @return the most appropiate test
	 */
	public static Test<Boolean> decideBooleanStatisticTest(
		Dataset<Boolean> dataset
	) {
		if (dataset.getUniqueConditionNames().size() > 2) {
			if (dataset.getSamples().length > 1000) {
				return new MultipleConditionsBooleanChiSquareTest();
			} else {
				return new MultiClassBooleanRandomizationTest();
			}
		} else {
			if (dataset.getSamples().length > 1000) {
				return new TwoConditionsBooleanYatesChiSquareTest();
			} else {
				return new TwoConditionsBooleanFisherTest();
			}
		}
	}
}
