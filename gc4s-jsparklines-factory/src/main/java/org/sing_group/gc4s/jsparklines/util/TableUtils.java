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
package org.sing_group.gc4s.jsparklines.util;

import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import no.uib.jsparklines.data.XYDataPoint;

/**
 * This class provides utility methods to deal with {@code JTable}s.
 *
 * @author hlfernandez
 *
 */
public class TableUtils {
	/**
	 * Returns the maximum value of the specified column in {@code table}.
	 *
	 * @param table a {@code JTable}
	 * @param col the column where the maximum value must be found
	 * @return the maximum value of the specified column
	 */
	public static Double getMaxColumnValue(JTable table, int col) {
		return getColumnDoubleValues(table, col).stream().mapToDouble(d -> d)
			.max().orElse(Double.NaN);
	}

	/**
	 * Returns the minimum value of the specified column in {@code table}.
	 *
	 * @param table a {@code JTable}
	 * @param col the column where the minimum value must be found
	 * @return the minimum value of the specified column
	 */
	public static Double getMinColumnValue(JTable table, int col) {
		return getColumnDoubleValues(table, col).stream().mapToDouble(d -> d)
			.min().orElse(Double.NaN);
	}

	private static List<Double> getColumnDoubleValues(JTable table, int col) {
		TableModel tableModel = table.getModel();
		requireNumberclass(tableModel.getColumnClass(col));
		List<Object> columnValues = getColumnValues(tableModel, col);
		return columnValues.stream().map(TableUtils::toDouble).collect(toList());
	}

	private static void requireNumberclass(Class<?> columnClass) {
		if (!Number.class.isAssignableFrom(columnClass)	&&
			!XYDataPoint.class.isAssignableFrom(columnClass) &&
			!isPrimitiveNumeric(columnClass)
		) {
			throw new IllegalStateException("Numeric or XYDataPoint column required");
		}
	}

	private static boolean isPrimitiveNumeric(Class<?> columnClass) {
		return double.class.isAssignableFrom(columnClass)
			|| float.class.isAssignableFrom(columnClass)
			|| int.class.isAssignableFrom(columnClass)
			|| long.class.isAssignableFrom(columnClass);
	}

	private static Double toDouble(Object value) {
		if (Number.class.isAssignableFrom(value.getClass())) {
			return ((Number) value).doubleValue();
		} else if (XYDataPoint.class.isAssignableFrom(value.getClass())) {
			return ((XYDataPoint) value).getX();
		} else {
			throw new IllegalArgumentException("Numeric or XYDataPoint column required");
		}
	}

	private static List<Object> getColumnValues(TableModel tableModel,
		int col) {
		List<Object> values = new LinkedList<>();
		for (int row = 0; row < tableModel.getRowCount(); row++) {
			values.add(tableModel.getValueAt(row, col));
		}
		return values;
	}
}
