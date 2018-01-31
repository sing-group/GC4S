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

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;

/**
 * An extension of {@code JXTextField} that provides additional features.
 *
 * @author hlfernandez
 *
 */
public class ExtendedJXTextField extends JXTextField {
	private static final long serialVersionUID = 1L;

	private Color emptyColor;
	private boolean textChangedDocumenListenerAdded = false;
	private DocumentListener textChangedDocumentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			textChanged();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			textChanged();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			textChanged();
		}
	};

	/**
	 * Creates a new {@code ExtendedJXTextField} with the specified prompt text.
	 *
	 * @param promptText the text field prompt text.
	 */
	public ExtendedJXTextField(String promptText) {
		super(promptText);
	}

	/**
	 * Sets the color that must be set when the text field is empty.
	 *
	 * @param color the color to use when the text field is empty.
	 */
	public void setEmptyTextFieldColor(Color color) {
		this.emptyColor = color;
		this.addTextChangedDocumentListener();
		textChanged();
	}

	private void addTextChangedDocumentListener() {
		if (!textChangedDocumenListenerAdded) {
			this.getDocument().addDocumentListener(textChangedDocumentListener);
			textChangedDocumenListenerAdded = true;
		}
	}

	private void textChanged() {
		if(this.getText().isEmpty()) {
			this.setBackground(emptyColor);
		} else {
			this.setBackground(null);
		}
	}
}
