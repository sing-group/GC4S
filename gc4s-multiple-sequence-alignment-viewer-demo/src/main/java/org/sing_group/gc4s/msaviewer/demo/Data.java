/*
 * #%L
 * GC4S multiple sequence alignment viewer demo
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
package org.sing_group.gc4s.msaviewer.demo;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.sing_group.gc4s.msaviewer.Sequence;

public class Data {

	public static final List<String> ALPHABET = asList("A", "C", "T", "G", "-"); 

	public static final String getRandomSequence(int length) {
		StringBuilder sequence = new StringBuilder(length);
		final Random random = new Random(length);
		random.setSeed(306);

		for (int i = 0; i < length; i++) {
			sequence.append(ALPHABET.get(random.nextInt(ALPHABET.size())));
		}

		return sequence.toString();
	}

	public static final String getRandomScores(int length) {
		StringBuilder scores = new StringBuilder(length);
		final Random random = new Random(length);

		for (int i = 0; i < length; i++) {
			scores.append(random.nextInt(10));
		}

		return scores.toString();
	}
	
	public static final List<Sequence> getRandomSequences(int n, int length) {
		final List<Sequence> sequences = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			final String seqIndex = Integer.toString(i);
			sequences.add(new Sequence() {

				@Override
				public String getHeader() {
					return "SEQUENCE" + seqIndex;
				}

				@Override
				public String getSequence() {
					return getRandomSequence(length);
				}
			});
		}

		return sequences;
	}
}
