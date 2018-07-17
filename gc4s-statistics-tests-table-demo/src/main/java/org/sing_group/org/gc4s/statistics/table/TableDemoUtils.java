/*
 * #%L
 * GC4S statistics tests table demo
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
package org.sing_group.org.gc4s.statistics.table;

import static java.lang.String.valueOf;

import org.sing_group.org.gc4s.statistics.table.progress.ProgressEvent;
import org.sing_group.org.gc4s.statistics.table.progress.ProgressEventListener;

public class TableDemoUtils {

	public static final ProgressEventListener PROGRESS_EVENT_LISTENER = new ProgressEventListener() {

		@Override
		public void onStart() {
			System.err.println("Calulation started.");
		}

		@Override
		public void onProgress(ProgressEvent event) {
			System.err.println("Progress: " + event.getProgress());
		}

		@Override
		public void onFinish() {
			System.err.println("Calulation finished.");
		}
	};

	public static final String[] features(int nFeatures) {
		String[] features = new String[nFeatures];
		for (int i = 0; i < nFeatures; i++) {
			features[i] = "F" + i;
		}

		return features;
	}

	public static final String[] samples(int nSamples) {
		String[] samples = new String[nSamples];
		for (int i = 0; i < nSamples; i++) {
			samples[i] = "S" + i;
		}

		return samples;
	}

	public static final String[] conditionNames(int nSamples, int nConditions) {
		String[] conditionNames = new String[nSamples];
		int samplesByCondition = (nSamples / nConditions);
		for (int i = 0; i < nConditions; i++) {
			int offSet = i * samplesByCondition;
			for (int j = 0; j < samplesByCondition; j++) {
				conditionNames[j + offSet] = "Class" + valueOf(((i) + 1));
			}
		}
		return conditionNames;
	}
}
