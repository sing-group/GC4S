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

import org.sing_group.gc4s.dialog.ExportCsvDialog;
import org.sing_group.gc4s.input.csv.CsvFormat;
import org.sing_group.gc4s.input.csv.CsvPanel;

/**
 * Extends {@code AbstractCsvTableExporter} to provide the default table
 * exporter for CSV. It is configured using a {@code CsvFormat} that can be
 * obtained with components such as the {@code CsvPanel} or
 * {@code ExportCsvDialog}.
 *
 * @author hlfernandez
 *
 * @see AbstractCsvTableExporter
 * @see CsvPanel
 * @see ExportCsvDialog
 *
 */
public class DefaultCsvTableExporter extends AbstractCsvTableExporter {
	private CsvFormat format;

	/**
	 * Creates a new {@code DefaultCsvTableExporter} configured with the
	 * specified format.
	 *
	 * @param format the {@code CsvFormat}.
	 */
	public DefaultCsvTableExporter(CsvFormat format) {
		this.format = format;
	}

	@Override
	public String getColumnSeparator() {
		return this.format.getColumnSeparator();
	}

	@Override
	public String getLineBreak() {
		return this.format.getLineBreak();
	}

	@Override
	public boolean quoteFields() {
		return this.format.isQuoteFields();
	}

	@Override
	protected DecimalFormat getDecimalFormatter() {
		return this.format.getDecimalFormatter();
	}
}
