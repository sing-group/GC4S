/*
 * #%L
 * GC4S JSparkLines factory
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
package org.sing_group.gc4s.jsparklines.builders.barchartrenderer;

import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_NON_SIGNIFICANT_COLOR;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_ORIENTATION;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_POSITIVES_COLOR;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_SIGNIFICANCE_LEVEL;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.createMaxXYDataSignificanceBarChartRenderer;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMaxColumnValue;

import java.awt.Color;

import javax.swing.JTable;

import org.jfree.chart.plot.PlotOrientation;

import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

/**
 * A builder class for the creation of JSparkLines bar chart renderers for
 * columns of {@code XYDataPoint} values that account for maximum column values.
 * These data points are meant to store a value with an associated significance
 * value.
 *
 * @author hlfernandez
 *
 */
public class MaximumXYDataSignificanceBarChartRendererBuilder {
	private int column;
	private JTable table;
	private Double maxValue;
	private Color positiveValuesColor = DEFAULT_POSITIVES_COLOR;
	private Double significanceLevel = DEFAULT_SIGNIFICANCE_LEVEL;
	private PlotOrientation plotOrientation = DEFAULT_ORIENTATION;
	private Color nonSignificantValuesColor = DEFAULT_NON_SIGNIFICANT_COLOR;

	/**
	 * Creates a new {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 * for the specified {@code table} and {@code column}.
	 *
	 * @param table a {@code JTable}
	 * @param column the column for which the renderer must be created
	 */
	public MaximumXYDataSignificanceBarChartRendererBuilder(JTable table,
		int column
	) {
		this.table = table;
		this.column = column;
	}

	/**
	 * Sets the plot orientation.
	 *
	 * @param plotOrientation the {@code PlotOrientation} value
	 * @return the {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 *         instance
	 */
	public MaximumXYDataSignificanceBarChartRendererBuilder withPlotOrientation(
		PlotOrientation plotOrientation
	) {
		this.plotOrientation = plotOrientation;
		return this;
	}

	/**
	 * Sets the maximum value of the column that must be used.
	 *
	 * @param maxValue the maximum value of the column that must be used
	 * @return the {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 *         instance
	 */
	public MaximumXYDataSignificanceBarChartRendererBuilder withMaxValue(
		Double maxValue
	) {
		this.maxValue = maxValue;
		return this;
	}

	/**
	 * Sets the color that must be used for positive values.
	 *
	 * @param positiveValuesColor the color for the positive values
	 * @return the {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 *         instance
	 */
	public MaximumXYDataSignificanceBarChartRendererBuilder withPositiveValuesColor(
		Color positiveValuesColor
	) {
		this.positiveValuesColor = positiveValuesColor;
		return this;
	}

	/**
	 * Sets the color that must be used for non significant values.
	 *
	 * @param nonSignificantValuesColor the color for non significant values
	 * @return the {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 *         instance
	 */
	public MaximumXYDataSignificanceBarChartRendererBuilder withNonSignificanteValuesColor(
		Color nonSignificantValuesColor
	) {
		this.nonSignificantValuesColor = nonSignificantValuesColor;
		return this;
	}

	/**
	 * Sets the significance level that must be used by the renderer.
	 *
	 * @param significanceLevel the significance level
	 * @return the {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 *         instance
	 */
	public MaximumXYDataSignificanceBarChartRendererBuilder withSignificanceLevel(
		Double significanceLevel
	) {
		this.significanceLevel = significanceLevel;
		return this;
	}

	/**
	 * Builds the {@code JSparklinesBarChartTableCellRenderer} using the
	 * introduced configuration.
	 *
	 * @return a new {@code JSparklinesBarChartTableCellRenderer} instance
	 */
	public JSparklinesBarChartTableCellRenderer build() {
		checkMaxValue();

		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			plotOrientation, maxValue, positiveValuesColor,
			nonSignificantValuesColor, significanceLevel);
	}

	private void checkMaxValue() {
		if (this.maxValue == null) {
			this.maxValue = getMaxColumnValue(table, column);
		}
	}
}
