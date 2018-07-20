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

import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_NEGATIVES_COLOR;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_ORIENTATION;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_POSITIVES_COLOR;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.createMaxMinValuesColoredBarChartRenderer;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMaxColumnValue;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMinColumnValue;

import java.awt.Color;

import javax.swing.JTable;

import org.jfree.chart.plot.PlotOrientation;

import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

/**
 * A builder class for the creation of JSparkLines bar chart renderers that
 * account for both the maximum and minimum column values and allow the usage of
 * custom colors for the bars.
 *
 * @author hlfernandez
 *
 */
public class MaximumMinimumColoredBarChartRendererBuilder {
	private int column;
	private JTable table;
	private Double maxValue;
	private Double minValue;
	private Color positiveValuesColor = DEFAULT_POSITIVES_COLOR;
	private Color negativeValuesColor = DEFAULT_NEGATIVES_COLOR;
	private PlotOrientation plotOrientation = DEFAULT_ORIENTATION;

	/**
	 * Creates a new {@code MaximumMinimumColoredBarChartRendererBuilder} for the
	 * specified {@code table} and {@code column}.
	 *
	 * @param table a {@code JTable}
	 * @param column the column for which the renderer must be created
	 */
	public MaximumMinimumColoredBarChartRendererBuilder(JTable table, int column) {
		this.table = table;
		this.column = column;
	}

	/**
	 * Sets the plot orientation.
	 *
	 * @param plotOrientation the {@code PlotOrientation} value
	 * @return the {@code MaximumMinimumBarChartRendererBuilder} instance
	 */
	public MaximumMinimumColoredBarChartRendererBuilder withPlotOrientation(
		PlotOrientation plotOrientation
	) {
		this.plotOrientation = plotOrientation;
		return this;
	}

	/**
	 * Sets the maximum value of the column that must be used.
	 *
	 * @param maxValue the maximum value of the column that must be used
	 * @return the {@code MaximumMinimumBarChartRendererBuilder} instance
	 */
	public MaximumMinimumColoredBarChartRendererBuilder withMaxValue(
		Double maxValue
	) {
		this.maxValue = maxValue;
		return this;
	}

	/**
	 * Sets the color that must be used for positive values.
	 *
	 * @param positiveValuesColor the color for the positive values
	 * @return the {@code MaximumMinimumColoredBarChartRendererBuilder} instance
	 */
	public MaximumMinimumColoredBarChartRendererBuilder withPositiveValuesColor(
		Color positiveValuesColor
	) {
		this.positiveValuesColor = positiveValuesColor;
		return this;
	}

	/**
	 * Sets the minimum value of the column that must be used.
	 *
	 * @param minValue the minimum value of the column that must be used
	 * @return the {@code MaximumMinimumBarChartRendererBuilder} instance
	 */
	public MaximumMinimumColoredBarChartRendererBuilder withMinValue(
		Double minValue
	) {
		this.minValue = minValue;
		return this;
	}

	/**
	 * Sets the color that must be used for negative values.
	 *
	 * @param negativeValuesColor the color for the negative values
	 * @return the {@code MaximumMinimumColoredBarChartRendererBuilder} instance
	 */
	public MaximumMinimumColoredBarChartRendererBuilder withNegativeValuesColor(
		Color negativeValuesColor
	) {
		this.negativeValuesColor = negativeValuesColor;
		return this;
	}

	/**
	 * Builds the {@code JSparklinesBarChartTableCellRenderer} using the
	 * introduced configuration.
	 *
	 * @return a new {@code JSparklinesBarChartTableCellRenderer} instance
	 */
	public JSparklinesBarChartTableCellRenderer build() {
		checkMaxMinValues();

		return createMaxMinValuesColoredBarChartRenderer(table, column,
			plotOrientation, maxValue, positiveValuesColor, minValue,
			negativeValuesColor);
	}

	private void checkMaxMinValues() {
		if (this.maxValue == null) {
			this.maxValue = getMaxColumnValue(table, column);
		}
		if (this.minValue == null) {
			this.minValue = getMinColumnValue(table, column);
		}
	}
}
