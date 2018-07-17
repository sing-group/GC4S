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
package org.sing_group.org.gc4s.statistics.data.util;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Random;

/**
 * A utility class to generate lists of random values.
 *
 * @author hlfernandez
 *
 */
public class StatisticsTestsDataUtils {
	public static final List<Boolean> randomValues(int n, int posProb,
		int seed
	) {
		Random random = new Random(seed);

		return range(0, n).mapToObj(i -> {
			if (random.nextInt(100) % 100 > posProb) {
				return true;
			} else {
				return false;
			}
		}).collect(toList());
	}

	public static final List<Integer> randomValues(int n, int seed) {
		Random random = new Random(seed);

		return range(0, n).mapToObj(i -> {
			return random.nextInt(100);
		}).collect(toList());
	}

	public static final List<String> randomValues(int n, int seed,
		List<String> alphabet
	) {
		Random random = new Random(seed);

		return range(0, n).mapToObj(i -> {
			return random.nextInt(alphabet.size());
		}).map(i -> {
			return alphabet.get(i);
		}).collect(toList());
	}
}
