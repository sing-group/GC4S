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

import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_ORIENTATION;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.createMaxMinValuesBarChartRenderer;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMaxColumnValue;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMinColumnValue;

import javax.swing.JTable;

import org.jfree.chart.plot.PlotOrientation;

import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

/**
 * A builder class for the creation of JSparkLines bar chart renderers that
 * account for both the maximum and minimum column values.
 *
 * @author hlfernandez
 *
 */
public class MaximumMinimumBarChartRendererBuilder {
	private int column;
	private JTable table;
	private Double maxValue;
	private Double minValue;
	private PlotOrientation plotOrientation = DEFAULT_ORIENTATION;

	/**
	 * Creates a new {@code MaximumMinimumBarChartRendererBuilder} for the
	 * specified {@code table} and {@code column}.
	 *
	 * @param table a {@code JTable}
	 * @param column the column for which the renderer must be created
	 */
	public MaximumMinimumBarChartRendererBuilder(JTable table, int column) {
		this.table = table;
		this.column = column;
	}

	/**
	 * Sets the plot orientation.
	 *
	 * @param plotOrientation the {@code PlotOrientation} value
	 * @return the {@code MaximumMinimumBarChartRendererBuilder} instance
	 */
	public MaximumMinimumBarChartRendererBuilder withPlotOrientation(
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
	public MaximumMinimumBarChartRendererBuilder withMaxValue(
		Double maxValue
	) {
		this.maxValue = maxValue;
		return this;
	}

	/**
	 * Sets the minimum value of the column that must be used.
	 *
	 * @param minValue the minimum value of the column that must be used
	 * @return the {@code MaximumMinimumBarChartRendererBuilder} instance
	 */
	public MaximumMinimumBarChartRendererBuilder withMinValue(
		Double minValue
	) {
		this.minValue = minValue;
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

		return createMaxMinValuesBarChartRenderer(table, column,
			plotOrientation, minValue, maxValue);
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
