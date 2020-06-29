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

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import org.sing_group.gc4s.event.TextFieldSelectionFocusListener;

/**
 * This class extends {@code JFormattedTextField} and only accepts integers as
 * input.
 *
 * @author hlfernandez
 *
 */
public class JIntegerTextField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code JIntegerTextField}.
	 *
	 */
	public JIntegerTextField() {
		this.setFormatter(configureFormatter());
		this.addFocusListener(new TextFieldSelectionFocusListener());
	}

	/**
	 * Creates a new {@code JIntegerTextField} and initializes it with the
	 * specified value.
	 *
	 * @param value the initial value
	 */
	public JIntegerTextField(Integer value) {
	    this.setFormatter(configureFormatter());
	    this.setValue(value);
	    this.addFocusListener(new TextFieldSelectionFocusListener());
	}

	private AbstractFormatter configureFormatter() {
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);

	    return formatter;
	}

	@Override
	public Integer getValue() {
		return (Integer) super.getValue();
	}
}
