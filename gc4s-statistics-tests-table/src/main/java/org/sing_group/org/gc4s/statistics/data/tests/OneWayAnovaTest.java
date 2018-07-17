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
import static org.apache.commons.math3.stat.inference.TestUtils.oneWayAnovaPValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.sing_group.org.gc4s.statistics.data.FeatureValues;

/**
 * An {@code AbstractNumberTest} to compute the One-way ANOVA test on a feature
 * values set with two or more conditions.
 *
 * @author hlfernandez
 *
 */
public class OneWayAnovaTest extends AbstractNumberTest {

	@Override
	public double test(FeatureValues<Number> values) {
		List<double[]> data = new LinkedList<>();
		for (String condition : values.getConditionNames()) {
			data.add(asDoubleArray(values.getConditionValues(condition)));
		}

		return oneWayAnovaPValue(data);
	}

	@Override
	public String getName() {
		return "One-way ANOVA";
	}

	@Override
	public Optional<String> getAdditionalInfoUrl() {
		return of("http://www.biostathandbook.com/onewayanova.html");
	}
}
