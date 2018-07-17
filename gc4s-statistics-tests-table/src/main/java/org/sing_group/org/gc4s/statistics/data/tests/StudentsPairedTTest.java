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
import static org.apache.commons.math3.stat.inference.TestUtils.pairedTTest;

import java.util.Optional;

/**
 * An implementation of {@code AbstractTTest} to compute the two-sample, paired,
 * two-tailed t-test statistic.
 *
 * @author hlfernandez
 *
 */
public class StudentsPairedTTest extends AbstractTTest {

	@Override
	public String getName() {
		return "Student's t-test (paired)";
	}

	@Override
	public Optional<String> getAdditionalInfoUrl() {
		return of("http://www.biostathandbook.com/pairedttest.html");
	}

	@Override
	protected double test(double[] sample1, double[] sample2) {
		return pairedTTest(sample1, sample2);
	}
}
