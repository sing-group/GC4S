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

/**
 * The interface to export tables in CSV like formats.
 *
 * @author hlfernandez
 *
 */
public interface CsvTableExporter {

	/**
	 * Returns the column separator.
	 *
	 * @return the column separator
	 */
	String getColumnSeparator();

	/**
	 * Returns the line break.
	 *
	 * @return the line break
	 */
	String getLineBreak();

	/**
	 * Returns {@code true} if fields must be quoted and {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if fields must be quoted and {@code false}
	 *         otherwise
	 */
	boolean quoteFields();

	/**
	 * Returns an String representation of the specified {@code value}.
	 *
	 * @param value the value to parse
	 * @return the string representation of the specified value
	 */
	String parseValue(Object value);
}
