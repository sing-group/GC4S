// A demo of the usage of the JSparkLines bar chart builders provided by this 
// module. Note that there is a builder factory called
// JSparklinesBarChartTableCellRendererBuilderFactory to facilitate the creation
// of the builders. Builder classes are located in the 
// org.sing_group.gc4s.jsparklines.builders.barchartrenderer package. This way
// is an alternative to the usage of the 
// JSparklinesBarChartTableCellRendererFactory
/*
 * #%L
 * GC4S JSparkLines factory demo
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
package org.sing_group.gc4s.jsparklines.demo;

import static java.awt.Color.CYAN;
import static java.awt.Color.GREEN;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.ORANGE;
import static org.sing_group.gc4s.visualization.VisualizationUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.sing_group.gc4s.jsparklines.builders.JSparklinesBarChartTableCellRendererBuilderFactory;

import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

public class JSparklinesBarChartTableCellRendererBuilderDemo {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		showComponent(new JScrollPane(getDemoTable()));
	}

	private static JComponent getDemoTable() {
		// First, a demo demo JTable with six columns that will be configured
		// with a different JSparklinesBarChartTableCellRenderer at each column
		// is created. Bar chart renderers created using different builders will
		// be established on each column.
		JTable table = new DemoTable();
		
		// The builder factory is used to set a MaximumBarChartRendererBuilder.
		// Since the max value is not provided, the builder automatically
		// determines it.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer = 
			JSparklinesBarChartTableCellRendererBuilderFactory
				.newMaximumBarChartRenderer(table, 0)
			.build();
		sparkLinesRenderer.showNumberAndChart(true, 40);
		
		// The builder factory is used to set a
		// MaximumColoredBarChartRendererBuilder. Since the max value is not 
		// provided, the builder automatically determines it. The color of the
		// positive values is set trough the builder.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer2 = 
			JSparklinesBarChartTableCellRendererBuilderFactory
				.newMaximumColoredBarChartRenderer(table, 1)
				.withPositiveValuesColor(ORANGE)
			.build();
		sparkLinesRenderer2.showNumberAndChart(true, 40);
		
		// The builder factory is used to set a
		// MaximumXYDataSignificanceBarChartRendererBuilder. Since the max value
		// is not provided, the builder automatically determines it. The colors
		// of the positive and the non significant values are set trough the 
		// builder.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer3 = 
			JSparklinesBarChartTableCellRendererBuilderFactory
				.newMaximumXYDataSignificanceBarChartRenderer(table, 2)
				.withPositiveValuesColor(CYAN)
				.withNonSignificanteValuesColor(LIGHT_GRAY)
			.build();
		sparkLinesRenderer3.showNumberAndChart(true, 40);

		// The builder factory is used to set a
		// MaximumMinimumXYDataSignificanceBarChartRendererBuilder. Since the 
		// max and min values are not provided, the builder automatically 
		// determines them. The color of the non significant values is set
		// trough the builder
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer4 = 
			JSparklinesBarChartTableCellRendererBuilderFactory
				.newMaximumMinimumXYDataSignificanceBarChartRenderer(table, 3)
				.withNonSignificanteValuesColor(LIGHT_GRAY)
				.withSignificanceLevel(0.05d)
			.build();
		sparkLinesRenderer4.showNumberAndChart(true, 40);

		// The builder factory is used to set a
		// MaximumMinimumBarChartRendererBuilder. Since the max and min values 
		// are not provided, the builder automatically determines them.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer5 = 
			JSparklinesBarChartTableCellRendererBuilderFactory
				.newMaximumMinimumBarChartRenderer(table, 4)
			.build();
		sparkLinesRenderer5.showNumberAndChart(true, 40);

		// The builder is used to set a
		// MaximumMinimumColoredBarChartRendererBuilder. Since the max and min
		// values are not provided, the builder automatically determines them.
		// The colors of the positive and negative values are set trough the 
		// builder.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer6 = 
			JSparklinesBarChartTableCellRendererBuilderFactory
				.newMaximumMinimumColoredBarChartRenderer(table, 5)
				.withPositiveValuesColor(GREEN.darker())
				.withNegativeValuesColor(GREEN.brighter())
			.build();
		sparkLinesRenderer6.showNumberAndChart(true, 40);

		return table;
	}
}
