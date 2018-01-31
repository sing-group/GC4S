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

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;

/**
 * A {@code BindJXTextField} extends a {@code JXTextField} allowing to specify a
 * function to consume text field's value when it changes.
 * 
 * @author hlfernandez
 *
 */
public class BindJXTextField extends JXTextField {
	private static final long serialVersionUID = 1L;

	private String value;
	private Consumer<String> bind;

	/**
	 * Creates a new {@code BindJXTextField}.
	 * 
	 * @param prompt the prompt value.
	 * @param value the initial text value.
	 * @param bind the function to consume text field's value when it changes.
	 */
	public BindJXTextField(String prompt, String value, Consumer<String> bind) {
		super(prompt);

		this.value = value;
		this.bind = bind;

		this.init();
	}

	private void init() {
		if (!value.equals("")) {
			this.setText(value);
		}

		this.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				bind.accept(getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				bind.accept(getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}
}