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
package org.sing_group.gc4s.jsparklines;

import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMaxColumnValue;
import static org.sing_group.gc4s.jsparklines.util.TableUtils.getMinColumnValue;

import java.awt.Color;

import javax.swing.JTable;

import org.jfree.chart.plot.PlotOrientation;

import no.uib.jsparklines.data.XYDataPoint;
import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;

/**
 * This factory provides methods to create different types of JSparkLines bar
 * chart renderers. Mainly, this class avoids the need to specify the maximum
 * and minimum values and automatically determines them by looking at the values
 * of the specified columns. Also, methods are overloaded so that renderers can
 * be created only with the parameters needed, using default values for the
 * rest.
 *
 * @author hlfernandez
 *
 */
public class JSparklinesBarChartTableCellRendererFactory {
	public static final Color DEFAULT_POSITIVES_COLOR = new Color(255, 51, 51);
	public static final Color DEFAULT_NEGATIVES_COLOR = new Color(51, 51, 255);
	public static final Color DEFAULT_POSITIVES_COLOR_LARGE_NUMBERS = new Color(
		110, 196, 97);
	public static final Color DEFAULT_NON_SIGNIFICANT_COLOR = Color.GRAY;
	public static final PlotOrientation DEFAULT_ORIENTATION = PlotOrientation.HORIZONTAL;
	public static final boolean DEFAULT_LARGE_NUMBERS_ARE_GOOD = true;
	public static final double DEFAULT_SIGNIFICANCE_LEVEL = 0.05d;

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}) and default large
	 * numbers are good ({@code true}). Use this constructor when only positive
	 * values are to be plotted.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. Make sure that this column
	 * contains numeric values.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueBarChartRenderer(
		JTable table, int column
	) {
		return createMaxValueBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			DEFAULT_LARGE_NUMBERS_ARE_GOOD);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default large
	 * numbers are good ({@code true}). Use this constructor when only positive
	 * values are to be plotted.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. Make sure that this column
	 * contains numeric values.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation
	) {
		return createMaxValueBarChartRenderer(table, column, plotOrientation,
			getMaxColumnValue(table, column), DEFAULT_LARGE_NUMBERS_ARE_GOOD);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}). Use this constructor
	 * when only positive values are to be plotted.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. Make sure that this column
	 * contains numeric values.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param largeNumbersAreGood makes sure that different colors are used for
	 *        bars where large numbers are "good", versus when small numbers are
	 *        "good"
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueBarChartRenderer(
		JTable table, int column, boolean largeNumbersAreGood) {
		return createMaxValueBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			largeNumbersAreGood);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it. Use this constructor when
	 * only positive values are to be plotted.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. Make sure that this column
	 * contains numeric values.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param largeNumbersAreGood makes sure that different colors are used for
	 *        bars where large numbers are "good", versus when small numbers are
	 *        "good"
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		boolean largeNumbersAreGood
	) {
		return createMaxValueBarChartRenderer(table, column, plotOrientation,
			getMaxColumnValue(table, column), largeNumbersAreGood);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it. Use this constructor when
	 * only positive values are to be plotted.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable (this is the same as setting the minimum value to
	 *        0)
	 * @param largeNumbersAreGood makes sure that different colors are used for
	 *        bars where large numbers are "good", versus when small numbers are
	 *        "good"
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Double maxValue, boolean largeNumbersAreGood
	) {
		JSparklinesBarChartTableCellRenderer renderer = new JSparklinesBarChartTableCellRenderer(
			plotOrientation, maxValue, largeNumbersAreGood);
		table.getColumnModel().getColumn(column).setCellRenderer(renderer);

		return renderer;
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the specified
	 * {@code positiveValuesColor} for positive values and the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}). Use this constructor
	 * when only positive values are to be plotted.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. Make sure that this column
	 * contains numeric values.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueColoredBarChartRenderer(
		JTable table, int column, Color positiveValuesColor
	) {
		return createMaxValueColoredBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			positiveValuesColor);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the specified
	 * {@code positiveValuesColor} for positive values. Use this constructor
	 * when only positive values are to be plotted.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. Make sure that this column
	 * contains numeric values.
	 *
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueColoredBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Color positiveValuesColor
	) {
		return createMaxValueColoredBarChartRenderer(table, column,
			plotOrientation, getMaxColumnValue(table, column),
			positiveValuesColor);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the specified
	 * {@code positiveValuesColor} for positive values. Use this constructor
	 * when only positive values are to be plotted.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable (this is the same as setting the minimum value to
	 *        0)
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxValueColoredBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Double maxValue, Color positiveValuesColor
	) {
		JSparklinesBarChartTableCellRenderer renderer = new JSparklinesBarChartTableCellRenderer(
			plotOrientation, maxValue, positiveValuesColor);
		table.getColumnModel().getColumn(column).setCellRenderer(renderer);

		return renderer;
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default values
	 * for plot orientation, significance level and colors.
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. As column must be of type
	 * {@link XYDataPoint}, the {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column
	) {
		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			DEFAULT_POSITIVES_COLOR, DEFAULT_NON_SIGNIFICANT_COLOR,
			DEFAULT_SIGNIFICANCE_LEVEL);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}) and default significance
	 * level ({@link DEFAULT_SIGNIFICANCE_LEVEL}).
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. As column must be of type
	 * {@link XYDataPoint}, the {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column, Color positiveValuesColor,
		Color nonSignificantColor
	) {
		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			positiveValuesColor, nonSignificantColor,
			DEFAULT_SIGNIFICANCE_LEVEL);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}).
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. As column must be of type
	 * {@link XYDataPoint}, the {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column, Color positiveValuesColor,
		Color nonSignificantColor, double significanceLevel
	) {
		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			positiveValuesColor, nonSignificantColor, significanceLevel);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default
	 * significance level ({@link DEFAULT_SIGNIFICANCE_LEVEL}).
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. As column must be of type
	 * {@link XYDataPoint}, the {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Color positiveValuesColor, Color nonSignificantColor
	) {
		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			plotOrientation, getMaxColumnValue(table, column),
			positiveValuesColor, nonSignificantColor,
			DEFAULT_SIGNIFICANCE_LEVEL);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it.
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * The maximum value used to create the renderer is automatically computed
	 * using the values of the column in the table. As column must be of type
	 * {@link XYDataPoint}, the {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Color positiveValuesColor, Color nonSignificantColor,
		double significanceLevel
	) {
		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			plotOrientation, getMaxColumnValue(table, column),
			positiveValuesColor, nonSignificantColor, significanceLevel);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer and sets it as
	 * renderer for the specified {@code column}, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}).
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable (this is the same as setting the minimum value to
	 *        0)
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column, double maxValue, Color positiveValuesColor,
		Color nonSignificantColor, double significanceLevel
	) {
		return createMaxXYDataSignificanceBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, maxValue, positiveValuesColor,
			nonSignificantColor, significanceLevel);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer and sets it as
	 * renderer for the specified {@code column}.
	 *
	 * Use this constructor when only positive values are to be plotted. Note
	 * that to use the significance color coding the object in the table cell
	 * has to be of type {@link XYDataPoint}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable (this is the same as setting the minimum value to
	 *        0)
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxXYDataSignificanceBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Double maxValue, Color positiveValuesColor, Color nonSignificantColor,
		double significanceLevel
	) {
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer = new JSparklinesBarChartTableCellRenderer(
			plotOrientation, maxValue, positiveValuesColor, nonSignificantColor,
			significanceLevel);
		table.getColumnModel().getColumn(column)
			.setCellRenderer(sparkLinesRenderer);

		return sparkLinesRenderer;
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer and sets it as
	 * renderer for the specified {@code column}, using default values for plot
	 * orientation, positive and negative values colors, significance level and
	 * non significant values color.
	 *
	 * Use this constructor when positive and negative values are to be plotted.
	 * Note that to use the significance color coding the object in the table
	 * cell has to be of type {@link XYDataPoint}.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table. As
	 * column must be of type {@link XYDataPoint}, the
	 * {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinXYDataSignificanceBarChartRenderer(
		JTable table, int column
	) {
		return createMaxMinXYDataSignificanceBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMinColumnValue(table, column),
			getMaxColumnValue(table, column), DEFAULT_NEGATIVES_COLOR,
			DEFAULT_POSITIVES_COLOR, DEFAULT_NON_SIGNIFICANT_COLOR,
			DEFAULT_SIGNIFICANCE_LEVEL);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer and sets it as
	 * renderer for the specified {@code column}, using default values for plot
	 * orientation and positive and negative values colors.
	 *
	 * Use this constructor when positive and negative values are to be plotted.
	 * Note that to use the significance color coding the object in the table
	 * cell has to be of type {@link XYDataPoint}.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table. As
	 * column must be of type {@link XYDataPoint}, the
	 * {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinXYDataSignificanceBarChartRenderer(
		JTable table, int column, Color nonSignificantColor,
		double significanceLevel
	) {
		return createMaxMinXYDataSignificanceBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMinColumnValue(table, column),
			getMaxColumnValue(table, column), DEFAULT_NEGATIVES_COLOR,
			DEFAULT_POSITIVES_COLOR, nonSignificantColor, significanceLevel);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer and sets it as
	 * renderer for the specified {@code column}.
	 *
	 * Use this constructor when positive and negative values are to be plotted.
	 * Note that to use the significance color coding the object in the table
	 * cell has to be of type {@link XYDataPoint}.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table. As
	 * column must be of type {@link XYDataPoint}, the
	 * {@link XYDataPoint#getX()} are used.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param negativeValuesColor the color to use for the negative values if
	 *        two sided data is shown
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinXYDataSignificanceBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Color negativeValuesColor, Color positiveValuesColor,
		Color nonSignificantColor, double significanceLevel
	) {
		return createMaxMinXYDataSignificanceBarChartRenderer(table, column,
			plotOrientation, getMinColumnValue(table, column),
			getMaxColumnValue(table, column), negativeValuesColor,
			positiveValuesColor, nonSignificantColor, significanceLevel);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer and sets it as
	 * renderer for the specified {@code column}.
	 *
	 * Use this constructor when positive and negative values are to be plotted.
	 * Note that to use the significance color coding the object in the table
	 * cell has to be of type {@link XYDataPoint}.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param minValue the minimum value to be plotted, used to make sure that
	 *        all plots in the same column has the same minimum value and are
	 *        thus comparable
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable
	 * @param negativeValuesColor the color to use for the negative values if
	 *        two sided data is shown
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @param nonSignificantColor the color to use for the non-significant
	 *        values
	 * @param significanceLevel the upper level for when to use the significant
	 *        values color
	 * @throws IllegalArgumentException if maxValue is 0.0
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinXYDataSignificanceBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Double minValue, Double maxValue, Color negativeValuesColor,
		Color positiveValuesColor, Color nonSignificantColor,
		double significanceLevel) {
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer = new JSparklinesBarChartTableCellRenderer(
			plotOrientation, minValue, maxValue, negativeValuesColor,
			positiveValuesColor, nonSignificantColor, significanceLevel);
		table.getColumnModel().getColumn(column)
			.setCellRenderer(sparkLinesRenderer);
		return sparkLinesRenderer;
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it. Use this constructor when
	 * positive and negative values are to be plotted.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesBarChartRenderer(
		JTable table, int column
	) {
		return createMaxMinValuesBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMinColumnValue(table, column),
			getMaxColumnValue(table, column));
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}). Use this constructor
	 * when positive and negative values are to be plotted.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation
	) {
		return createMaxMinValuesBarChartRenderer(table, column,
			plotOrientation, getMinColumnValue(table, column),
			getMaxColumnValue(table, column));
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}). Use this constructor
	 * when positive and negative values are to be plotted.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param minValue the minimum value to be plotted, used to make sure that
	 *        all plots in the same column has the same minimum value and are
	 *        thus comparable
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable (this is the same as setting the minimum value to
	 *        0)
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesBarChartRenderer(
		JTable table, int column, Double minValue, Double maxValue
	) {
		return createMaxMinValuesBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, minValue, maxValue);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it. Use this constructor when
	 * positive and negative values are to be plotted.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param minValue the minimum value to be plotted, used to make sure that
	 *        all plots in the same column has the same minimum value and are
	 *        thus comparable
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable (this is the same as setting the minimum value to
	 *        0)
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Double minValue, Double maxValue
	) {
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer = new JSparklinesBarChartTableCellRenderer(
			plotOrientation, minValue, maxValue);
		table.getColumnModel().getColumn(column)
			.setCellRenderer(sparkLinesRenderer);

		return sparkLinesRenderer;
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default positive
	 * and negative values colors and the default plot orientation
	 * ({@code PlotOrientation.HORIZONTAL}). Use this constructor when positive
	 * and negative values are to be plotted.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesColoredBarChartRenderer(
		JTable table, int column
	) {
		return createMaxMinValuesColoredBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			DEFAULT_POSITIVES_COLOR, getMinColumnValue(table, column),
			DEFAULT_NEGATIVES_COLOR);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default positive
	 * and negative values colors. Use this constructor when positive and
	 * negative values are to be plotted.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesColoredBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation
	) {
		return createMaxMinValuesColoredBarChartRenderer(table, column,
			plotOrientation, getMaxColumnValue(table, column),
			DEFAULT_POSITIVES_COLOR, getMinColumnValue(table, column),
			DEFAULT_NEGATIVES_COLOR);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it, using the default plot
	 * orientation ({@code PlotOrientation.HORIZONTAL}). Use this constructor
	 * when positive and negative values are to be plotted.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param negativeValuesColor the color to use for the negative values if
	 *        two sided data is shown
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesColoredBarChartRenderer(
		JTable table, int column, Color positiveValuesColor,
		Color negativeValuesColor
	) {
		return createMaxMinValuesColoredBarChartRenderer(table, column,
			DEFAULT_ORIENTATION, getMaxColumnValue(table, column),
			positiveValuesColor, getMinColumnValue(table, column),
			negativeValuesColor);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it. Use this constructor when
	 * positive and negative values are to be plotted.
	 *
	 * The maximum and minimum values used to create the renderer are
	 * automatically computed using the values of the column in the table.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param negativeValuesColor the color to use for the negative values if
	 *        two sided data is shown
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesColoredBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Color positiveValuesColor, Color negativeValuesColor
	) {
		return createMaxMinValuesColoredBarChartRenderer(table, column,
			plotOrientation, getMaxColumnValue(table, column),
			positiveValuesColor, getMinColumnValue(table, column),
			negativeValuesColor
		);
	}

	/**
	 * Creates a new JSparklinesBarChartTableCellRenderer for the specified
	 * {@code column} and sets it as renderer for it. Use this constructor when
	 * positive and negative values are to be plotted.
	 *
	 * @param table the {@code JTable}
	 * @param column the column of the table for which the renderer must be
	 *        created
	 * @param plotOrientation the orientation of the plot
	 * @param minValue the minimum value to be plotted, used to make sure that
	 *        all plots in the same column has the same minimum value and are
	 *        thus comparable
	 * @param maxValue the maximum value to be plotted, used to make sure that
	 *        all plots in the same column has the same maximum value and are
	 *        thus comparable
	 * @param negativeValuesColor the color to use for the negative values if
	 *        two sided data is shown
	 * @param positiveValuesColor the color to use for the positive values if
	 *        two sided data is shown, and the color used for one sided data
	 * @throws IllegalArgumentException if minValue is greater than maxValue
	 * @return the created {@code JSparklinesBarChartTableCellRenderer}
	 */
	public static final JSparklinesBarChartTableCellRenderer createMaxMinValuesColoredBarChartRenderer(
		JTable table, int column, PlotOrientation plotOrientation,
		Double maxValue, Color positiveValuesColor, Double minValue,
		Color negativeValuesColor
	) {
		JSparklinesBarChartTableCellRenderer sparkLinesRenderer = new JSparklinesBarChartTableCellRenderer(
			plotOrientation, minValue, maxValue, negativeValuesColor,
			positiveValuesColor);
		table.getColumnModel().getColumn(column)
			.setCellRenderer(sparkLinesRenderer);

		return sparkLinesRenderer;
	}
}
