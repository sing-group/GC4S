// An example to show the simplest usage of the StatisticsTestTable.
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

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.sing_group.org.gc4s.statistics.data.Dataset;
import org.sing_group.org.gc4s.statistics.data.DefaultDataset;
import org.sing_group.org.gc4s.statistics.data.tests.FdrCorrection;
import org.sing_group.org.gc4s.statistics.data.tests.PValuesCorrection;
import org.sing_group.org.gc4s.statistics.data.tests.Test;
import org.sing_group.org.gc4s.statistics.data.tests.TwoConditionsBooleanFisherTest;
import org.sing_group.org.gc4s.statistics.table.ui.BooleanHighlighter;
import org.sing_group.org.gc4s.statistics.table.ui.ConditionsSeparatorHighlighter;
import org.sing_group.org.gc4s.statistics.table.ui.StatisticsTestTableHeaderRenderer;
import org.sing_group.org.gc4s.statistics.table.ui.YesNoTableCellRenderer;

public class MinimalTableDemo {
	public static void main(String[] args) {
		// First, the data structures needed to create a Dataset of booleans are
		// created. These structures are: feature names, sample names and
		// conditions and a random matrix of values.
		String[] features = new String[] { "F1", "F2" };
		String[] samples = new String[] { "S1", "S2", "S3", "S4" };

		final Boolean[][] data = new Boolean[][] {
			{ true, true, false, false },
			{ false, false, true, true }
		};

		String[] conditionNames = new String[] { "A", "A", "B", "B" };

		// Then, the Dataset of Boolean required by the table is instantiated
		// using the data created before.
		Dataset<Boolean> dataset =
			new DefaultDataset<>(features, samples, data, conditionNames);

		// After that, it is created the statistical test of Boolean which is
		// also required by the table. Alternatively, the
		// decideBooleanStatisticTest method of the StatisticsTestsUtils class 
		// can be used to automatically choose the most appropriate test for
		// Boolean data.
		Test<Boolean> test = new TwoConditionsBooleanFisherTest();
		PValuesCorrection correction = new FdrCorrection();

		// And now, the table itself is instantiated. Note that the correction
		// object is optional, if it is not provided, then the q-values column
		// is not shown.
		StatisticsTestTable<Boolean> table =
			new StatisticsTestTable<>(dataset, test, correction);
		
		// And finally, the table is configured to set some options and add
		// renderers and highlighters. A YesNoTableCellRenderer is set to show
		// YES or NO instead of true and false.
		table.setDefaultRenderer(Boolean.class, new YesNoTableCellRenderer());

		// A StatisticsTestTableHeaderRenderer is set in the header in order to
		// draw the names of the samples in different colors depending on their
		// conditions.
		table.getTableHeader().setDefaultRenderer(
			new StatisticsTestTableHeaderRenderer(
				table.getTableHeader().getDefaultRenderer(), 1)
		);

		// Two highlighters are set. The BooleanHighlighter shows different
		// colors for true and false cells. The ConditionsSeparatorHighlighter
		// draws a left border at the first sample of each condition to enhance
		// the distinction between conditions.
		table.setHighlighters(
			new BooleanHighlighter(),
			new ConditionsSeparatorHighlighter<>(table)
		);

		// After applying the necessary configurations, the table is shown as a
		// part of a JScrollPane to make the header visible.
		showComponent(new JScrollPane(table), JFrame.MAXIMIZED_BOTH);
	}
}

