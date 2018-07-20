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
package org.sing_group.gc4s.jsparklines.builders;

import javax.swing.JTable;

import org.sing_group.gc4s.jsparklines.builders.barchartrenderer.MaximumBarChartRendererBuilder;
import org.sing_group.gc4s.jsparklines.builders.barchartrenderer.MaximumColoredBarChartRendererBuilder;
import org.sing_group.gc4s.jsparklines.builders.barchartrenderer.MaximumMinimumBarChartRendererBuilder;
import org.sing_group.gc4s.jsparklines.builders.barchartrenderer.MaximumMinimumColoredBarChartRendererBuilder;
import org.sing_group.gc4s.jsparklines.builders.barchartrenderer.MaximumMinimumXYDataSignificanceBarChartRendererBuilder;
import org.sing_group.gc4s.jsparklines.builders.barchartrenderer.MaximumXYDataSignificanceBarChartRendererBuilder;

/**
 * This factory provides methods to create renderer builder objects for bar
 * charts. These so called renderer builders are an alternative to the usage of
 * the static methods provided by the
 * {@code JSparklinesBarChartTableCellRendererFactory}.
 *
 * @author hlfernandez
 *
 */
public class JSparklinesBarChartTableCellRendererBuilderFactory {
	/**
	 * Returns a new {@code MaximumBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @return a new {@code MaximumBarChartRendererBuilder}
	 */
	public static MaximumBarChartRendererBuilder newMaximumBarChartRenderer(
		JTable table, int column
	) {
		return new MaximumBarChartRendererBuilder(table, column);
	}

	/**
	 * Returns a new {@code MaximumColoredBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @return a new {@code MaximumColoredBarChartRendererBuilder}
	 */

	public static MaximumColoredBarChartRendererBuilder newMaximumColoredBarChartRenderer(
		JTable table, int column
	) {
		return new MaximumColoredBarChartRendererBuilder(table, column);
	}

	/**
	 * Returns a new {@code MaximumXYDataSignificanceBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @return a new {@code MaximumXYDataSignificanceBarChartRendererBuilder}
	 */

	public static MaximumXYDataSignificanceBarChartRendererBuilder newMaximumXYDataSignificanceBarChartRenderer(
		JTable table, int column
	) {
		return new MaximumXYDataSignificanceBarChartRendererBuilder(table, column);
	}

	/**
	 * Returns a new {@code MaximumMinimumXYDataSignificanceBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @return a new {@code MaximumMinimumXYDataSignificanceBarChartRendererBuilder}
	 */

	public static MaximumMinimumXYDataSignificanceBarChartRendererBuilder newMaximumMinimumXYDataSignificanceBarChartRenderer(
		JTable table, int column
	) {
		return new MaximumMinimumXYDataSignificanceBarChartRendererBuilder(table, column);
	}

	/**
	 * Returns a new {@code MaximumMinimumBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @return a new {@code MaximumMinimumBarChartRendererBuilder}
	 */

	public static MaximumMinimumBarChartRendererBuilder newMaximumMinimumBarChartRenderer(
		JTable table, int column
	) {
		return new MaximumMinimumBarChartRendererBuilder(table, column);
	}

	/**
	 * Returns a new {@code MaximumMinimumColoredBarChartRendererBuilder} for the specified
	 * {@code table} and {@code column}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @return a new {@code MaximumMinimumColoredBarChartRendererBuilder}
	 */

	public static MaximumMinimumColoredBarChartRendererBuilder newMaximumMinimumColoredBarChartRenderer(
		JTable table, int column
	) {
		return new MaximumMinimumColoredBarChartRendererBuilder(table, column);
	}
}
