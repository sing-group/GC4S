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
package org.sing_group.gc4s.input.csv;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * A {@code CsvFormat} specifies the format of a CSV file.
 * 
 * @author hlfernandez
 *
 */
public class CsvFormat {

	public static enum FileFormat {
		CUSTOM("Custom"),
		EXCEL("Excel compatible CSV"), 
		LIBRE_OFFICE("Libre/Open Office compatible CSV");
		
		private String description;
		
		private FileFormat(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return this.description;
		}
		
		@Override
		public String toString() {
			return getDescription();
		}
	};
	
	private String lineBreak;
	private String columnSeparator;
	private boolean quoteFields;
	private char decimalSeparator = '.';
	private final DecimalFormat decimalFormatter =
		new DecimalFormat("0.0000");
	private final DecimalFormatSymbols symbols =
		new DecimalFormatSymbols(Locale.getDefault());
	
	/**
	 * Constructs a new {@code CsvFormat} instance.
	 * 
	 * @param format the {@code FileFormat} to initialize the CSV format.
	 * @throws IllegalArgumentException if format is {@code CUSTOM}.
	 */
	public CsvFormat(FileFormat format) throws IllegalArgumentException {
		switch (format) {
		case EXCEL:
			this.columnSeparator = ";";
			this.lineBreak = "\r\n";
			this.quoteFields = false;
			break;
		case LIBRE_OFFICE:
			if (symbols.getDecimalSeparator() == ',') {
				this.columnSeparator = ";";
			} else {
				this.columnSeparator = ",";
			}
			this.lineBreak = "\n";
			this.quoteFields = false;
			break;
		case CUSTOM:
			throw new IllegalArgumentException(
				"FileFormat.CUSTOM cannot be used to construct a new instance."
				+ " Use constructor with all options in this case.");
		}

		decimalSeparator = symbols.getDecimalSeparator();
		
		configureFormaters();
	}
	
	/**
	 * Constructs a new {@code CsvFormat} instance.
	 * 
	 * @param columnSeparator the column separator.
	 * @param decimalSeparator the decimal separator.
	 * @param quoteFields true if fields must be delimited by double quotes.
	 * @param lineBreak the line break.
	 */
	public CsvFormat(String columnSeparator, char decimalSeparator,
			boolean quoteFields, String lineBreak) {
		this.columnSeparator = columnSeparator;
		this.lineBreak = lineBreak;
		this.quoteFields = quoteFields;
		this.decimalSeparator = decimalSeparator;

		configureFormaters();
	}

	private void configureFormaters() {
		if (decimalSeparator != symbols.getDecimalSeparator()) {
			symbols.setDecimalSeparator(decimalSeparator);
			this.decimalFormatter.setDecimalFormatSymbols(symbols);
		}
	}

	/**
	 * Returns the line break.
	 * 
	 * @return the line break.
	 */
	public String getLineBreak() {
		return lineBreak;
	}
	
	/**
	 * Returns the column separator.
	 * 
	 * @return the column separator.
	 */
	public String getColumnSeparator() {
		return columnSeparator;
	}
	
	/**
	 * Returns true if fields must be delimited by duoble quotes.
	 * 
	 * @return true if fields must be delimited by duoble quotes.
	 */
	public boolean isQuoteFields() {
		return quoteFields;
	}
	
	/**
	 * Returns the decimal formatter.
	 * 
	 * @return the decimal formatter.
	 */
	public DecimalFormat getDecimalFormatter() {
		return decimalFormatter;
	}

	/**
	 * Returns the decimal separator character.
	 * 
	 * @return the decimal separator character.
	 */
	public char getDecimalSeparator() {
		return this.decimalSeparator;
	}
}
