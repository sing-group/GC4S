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

import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_LARGE_NUMBERS_ARE_GOOD;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.DEFAULT_ORIENTATION;
import static org.sing_group.gc4s.jsparklines.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMaxColumnValue;

import javax.swing.JTable;

import org.jfree.chart.plot.PlotOrientation;

import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

/**
 * A builder class for the creation of simple JSparkLines bar chart renderers
 * that only account for the maximum column value.
 *
 * @author hlfernandez
 *
 */
public class MaximumBarChartRendererBuilder {
	private int column;
	private JTable table;
	private Double maxValue;
	private PlotOrientation plotOrientation = DEFAULT_ORIENTATION;
	private boolean largeNumbersAreGood = DEFAULT_LARGE_NUMBERS_ARE_GOOD;

	/**
	 * Creates a new {@code MaximumBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table a {@code JTable}
	 * @param column the column for which the renderer must be created
	 */
	public MaximumBarChartRendererBuilder(JTable table, int column) {
		this.table = table;
		this.column = column;
	}

	public MaximumBarChartRendererBuilder withPlotOrientation(
		PlotOrientation plotOrientation
	) {
		this.plotOrientation = plotOrientation;
		return this;
	}

	/**
	 * Sets the maximum value of the column that must be used.
	 *
	 * @param maxValue the maximum value of the column that must be used
	 * @return the {@code MaximumBarChartRendererBuilder} instance
	 */
	public MaximumBarChartRendererBuilder withMaxValue(
		Double maxValue
	) {
		this.maxValue = maxValue;
		return this;
	}

	/**
	 * Sets whether large numbers are good or not.
	 *
	 * @param largeNumbersAreGood makes sure that different colors are used for
	 *        bars where large numbers are "good", versus when small numbers are
	 *        "good"
	 * @return the {@code MaximumBarChartRendererBuilder} instance
	 */
	public MaximumBarChartRendererBuilder largeNumbersAreGood(
		boolean largeNumbersAreGood
	) {
		this.largeNumbersAreGood = largeNumbersAreGood;
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

		return createMaxValueBarChartRenderer(table, column, plotOrientation,
			maxValue, largeNumbersAreGood);
	}

	private void checkMaxValue() {
		if (this.maxValue == null) {
			this.maxValue = getMaxColumnValue(table, column);
		}
	}
}
