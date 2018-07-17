/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.visualization.table.csv;

import java.text.DecimalFormat;

/**
 * An abstract {@code CsvTableExporter} implementation.
 *
 * @author hlfernandez
 *
 */
public abstract class AbstractCsvTableExporter implements CsvTableExporter {

	@Override
	public String parseValue(Object value) {
		if (value == null) {
			return "";
		}
		if (value instanceof Double ||
			value instanceof Long	||
			value instanceof Float
		) {
			if (value.equals(Double.NaN) || value.equals(Float.NaN)) {
				return "NaN";
			} else {
				return getDecimalFormatter().format(value);
			}
		} else {
			return String.valueOf(value);
		}
	}

	/**
	 * Return a {@link DecimalFormat} object to format decimal values.
	 *
	 * @return a {@link DecimalFormat} object to format decimal values
	 */
	protected abstract DecimalFormat getDecimalFormatter();
}
