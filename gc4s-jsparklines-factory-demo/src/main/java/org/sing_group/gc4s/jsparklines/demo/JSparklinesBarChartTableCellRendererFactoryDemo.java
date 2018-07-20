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

import org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory;

import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

// A demo of the usage of the JSparklinesBarChartTableCellRendererFactory 
// provided by this module. This factory provides methods to create
// JSparkLines bar chart renderers with different configurations. This way
// is an alternative to the usage of the 
// JSparklinesBarChartTableCellRendererBuilderFactory that provides builders 
// for the bar chart renderers.
public class JSparklinesBarChartTableCellRendererFactoryDemo {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		showComponent(new JScrollPane(getDemoTable()));
	}

	private static JComponent getDemoTable() {
		// Creation of the demo JTable with six columns that will be configured
		// with a different JSparklinesBarChartTableCellRenderer at each column.
		JTable table = new DemoTable();

		// The method createMaxValueBarChartRenderer is used to set a simple
		// bar chart renderer that automatically determines the maximum value of
		// the column.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer = 
			JSparklinesBarChartTableCellRendererFactory
				.createMaxValueBarChartRenderer(table, 0);
		sparkLinesRenderer.showNumberAndChart(true, 40);

		// The method createMaxValueColoredBarChartRenderer is used to set a 
		// bar chart renderer with the specified color that automatically
		// determines the maximum value of the column.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer2 = 
			JSparklinesBarChartTableCellRendererFactory
			.createMaxValueColoredBarChartRenderer(table, 1, ORANGE);
		sparkLinesRenderer2.showNumberAndChart(true, 40);

		// The method createMaxMinXYDataSignificanceBarChartRenderer is used to
		// set a simple bar chart renderer for XY values (values of the 
		// XYDataPoint class), indicating also the color for positive and 
		// non significant values. Since the max value is not provided, the
		// method automatically determines it.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer3 = 
			JSparklinesBarChartTableCellRendererFactory
				.createMaxXYDataSignificanceBarChartRenderer(
					table, 2, CYAN, LIGHT_GRAY);
		sparkLinesRenderer3.showNumberAndChart(true, 40);

		// The method createMaxXYDataSignificanceBarChartRenderer is used to
		// set a simple bar chart renderer for XY values (values of the
		// XYDataPoint class), indicating also the color for non significant
		// values and the significance level used to determine them. Since the
		// max value is not provided, the method automatically determines it.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer4 = 
			JSparklinesBarChartTableCellRendererFactory
				.createMaxMinXYDataSignificanceBarChartRenderer(
					table, 3, LIGHT_GRAY, 0.05d);
		sparkLinesRenderer4.showNumberAndChart(true, 40);
	
		// The method createMaxMinValuesBarChartRenderer is used to set a simple
		// bar chart renderer that automatically determines the maximum and 
		// minimum values of the column.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer5 = 
			JSparklinesBarChartTableCellRendererFactory
				.createMaxMinValuesBarChartRenderer(table, 4);
		sparkLinesRenderer5.showNumberAndChart(true, 40);
	
		
		// The method createMaxMinValuesColoredBarChartRenderer is used to set
		// a bar chart renderer that automatically determines the maximum and 
		// minimum values of the column. The colors of the positive and negative
		// values are also specified.
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer6 = 
			JSparklinesBarChartTableCellRendererFactory
				.createMaxMinValuesColoredBarChartRenderer(
					table, 5, GREEN.darker(), GREEN.brighter());
		sparkLinesRenderer6.showNumberAndChart(true, 40);

		return table;
	}
}
