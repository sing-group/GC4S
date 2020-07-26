/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2020 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
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

import static javax.swing.UIManager.getColor;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;
import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.utilities.ColorUtils;

/**
 * An extension of {@code JXTextField} to input e-mails.
 *
 * @author hlfernandez
 *
 */
public class EmailJXTextField extends JXTextField {
	private static final long serialVersionUID = 1L;

	private static final Color COLOR_VALID_INPUT = getColor("TextField.background");

	protected Color invalidEmailColor;
	private boolean allowEmpty;
	private boolean textChangedDocumenListenerAdded = false;
	private DocumentListener textChangedDocumentListener = new DocumentAdapter() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			textChanged();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			textChanged();
		}
	};

	/**
	 * Creates a new {@code EmailJXTextField}.
	 *
	 */
	public EmailJXTextField() {
		this(ColorUtils.COLOR_INVALID_INPUT, false);
	}

	/**
	 * Creates a new {@code EmailJXTextField} with the specified color for invalid e-mails and whether an empty input is
	 * valid or not.
	 * 
	 * @param invalidEmailColor the color for invalid e-mails
	 * @param allowEmpty whether an empty input is valid or not
	 */
	public EmailJXTextField(Color invalidEmailColor, boolean allowEmpty) {
		super("your@email.com");

		this.allowEmpty = allowEmpty;
		this.invalidEmailColor = invalidEmailColor;
		this.addTextChangedDocumentListener();
		this.textChanged();
	}

	private void addTextChangedDocumentListener() {
		if (!textChangedDocumenListenerAdded) {
			this.getDocument().addDocumentListener(textChangedDocumentListener);
			textChangedDocumenListenerAdded = true;
		}
	}

	private void textChanged() {
		if (!isValidInput()) {
			this.setBackground(this.invalidEmailColor);
		} else {
			this.setBackground(COLOR_VALID_INPUT);
		}
	}

	private static boolean isEmailValid(String email) {
		final Pattern EMAIL_REGEX =
			Pattern.compile(
				"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
				Pattern.CASE_INSENSITIVE
			);
		return EMAIL_REGEX.matcher(email).matches();
	}

	/**
	 * Returns {@code true} if the current input is valid and {@code false} otherwise.
	 * 
	 * @return {@code true} if the current input is valid and {@code false} otherwise
	 */
	public boolean isValidInput() {
		String email = this.getText();
		if ((!email.isEmpty() && !isEmailValid(email)) || (email.isEmpty() && !this.allowEmpty)) {
			return false;
		} else {
			return true;
		}
	}
}
