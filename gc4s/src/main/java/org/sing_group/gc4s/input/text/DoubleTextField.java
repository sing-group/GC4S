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
package org.sing_group.gc4s.input.text;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * An extension of {@code JFormattedTextField} to create text fields for allow
 * the input of double values.
 * 
 * @author hlfernandez
 *
 */
public class DoubleTextField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;
	private static NumberFormat format = DecimalFormat
			.getInstance(Locale.ENGLISH);
	private static NumberFormatter formatter = new NumberFormatter(format);

	static {
		format.setMaximumFractionDigits(6);
		formatter.setValueClass(Double.class);
		formatter.setMinimum(-Double.MAX_VALUE);
		formatter.setMaximum(Double.MAX_VALUE);
		formatter.setCommitsOnValidEdit(true);
	}

	/**
	 * Constructs a new {@code DoubleTextField}.
	 * 
	 */
	public DoubleTextField() {
		super(formatter);
	}

	/**
	 * Constructs a new {@code DoubleTextField} and sets {@code value} as input
	 * value.
	 * 
	 * @param value the initial input value.
	 */
	public DoubleTextField(double value) {
		super(formatter);
		setValue(value);
	}
	
	@Override
	public Double getValue() {
		return (Double) super.getValue();
	}
}