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

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;
import static org.sing_group.org.gc4s.statistics.data.util.StatisticsTestsDataUtils.randomValues;
import static org.sing_group.org.gc4s.statistics.table.TableDemoUtils.conditionNames;
import static org.sing_group.org.gc4s.statistics.table.TableDemoUtils.features;
import static org.sing_group.org.gc4s.statistics.table.TableDemoUtils.samples;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.sing_group.org.gc4s.statistics.data.Dataset;
import org.sing_group.org.gc4s.statistics.data.DefaultDataset;
import org.sing_group.org.gc4s.statistics.data.tests.FdrCorrection;
import org.sing_group.org.gc4s.statistics.data.tests.NominalChiSquareTest;
import org.sing_group.org.gc4s.statistics.data.tests.PValuesCorrection;
import org.sing_group.org.gc4s.statistics.data.tests.Test;
import org.sing_group.org.gc4s.statistics.table.ui.ConditionsSeparatorHighlighter;
import org.sing_group.org.gc4s.statistics.table.ui.StatisticsTestTableHeaderRenderer;

// An example to show the usage of the StatisticsTestTable with a Dataset of
// nominal values with two conditions and forty samples. In this case, a
// Chi-squared test (implemented by the NominalChiSquareTest class) is used to
// compute the p-values.
public class NominalTableDemo {
	public static void main(String[] args) {
		// Creation of the data needed to create the a Dataset of booleans
		// feature names, sample names and conditions and a random matrix of
		// values.
		int nFeatures = 40;
		int nSamples = 20;
		int nConditions = 4;

		String[] features = features(nFeatures);
		String[] samples = samples(nSamples);

		List<String> alphabet = Arrays.asList("A", "B", "C");

		final String[][] data = new String[nFeatures][nSamples];
		for (int i = 0; i < nFeatures; i++) {
			List<String> randomValues = randomValues(nSamples, i, alphabet);
			data[i] = randomValues.toArray(new String[nSamples]);
		}

		String[] conditionNames = conditionNames(nSamples, nConditions);

		// Creation of the Dataset of String required by the table using the
		// data created before.
		Dataset<String> dataset = new DefaultDataset<>(features,
			samples, data, conditionNames);

		// Creation of the statistical test of String which is also required by
		// the table. GC4S provides a Chi-squared test for nominal values, but
		// it is possible to provide custom tests by implementing Test<String>.
		Test<String> test = new NominalChiSquareTest();
		PValuesCorrection correction = new FdrCorrection();

		// Instantiation of the table itself. Note that the correction object is
		// optional, if it is not provided, then the q-values column is not
		// shown.
		StatisticsTestTable<String> table =
			new StatisticsTestTable<>(dataset, test, correction);

		// Now, the table is configured to set some options and add a renderer
		table.disableColumVisibilityActions();

		// A StatisticsTestTableHeaderRenderer is set in the header in order to
		// draw the names of the samples in different colors depending on their
		// conditions.
		table.getTableHeader().setDefaultRenderer(
			new StatisticsTestTableHeaderRenderer(
				table.getTableHeader().getDefaultRenderer(), 1)
		);

		// The ConditionsSeparatorHighlighter is set to draw a left border at
		// the first sample of each condition to enhance the distinction between
		// conditions.
		table.setHighlighters(new ConditionsSeparatorHighlighter<>(table));

		// Finally, the table is shown as a part of a JScrollPane to make the
		// header visible.
		showComponent(new JScrollPane(table), JFrame.MAXIMIZED_BOTH);
	}
}
