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


import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A {@link JLimitedTextField} is an extension of {@link JTextField} that allows
 * specifying the maximum extension of the document ({@code Integer.MAX_VALUE}
 * by default).
 * 
 * @author hlfernandez
 * @see JTextField
 *
 */
public class JLimitedTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	private int limit = Integer.MAX_VALUE;

	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param limit
	 *            the document limit.
	 */
	public JLimitedTextField(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param string
	 *            the initial text.
	 */
	public JLimitedTextField(String string) {
		super();
		insertString(0, string);
	}

	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param string
	 *            the initial text.
	 * @param limit
	 *            the document limit.
	 */
	public JLimitedTextField(String string, int limit) {
		super();
		this.limit = limit;
		insertString(0, string);
	}
	
	/**
	 * Constructs a new instance of {@code JLimitedTextField}.
	 * 
	 * @param string
	 *            the initial text.
	 * @param limit
	 *            the document limit.
	 * @param cols
	 *            the number of columns.
	 */
	public JLimitedTextField(String string, int limit, int cols) {
		super(cols);
		this.limit = limit;
		insertString(0, string);
	}

	private void insertString(int offset, String s) {
		try {
			getDocument().insertString(offset, s, null);
		} catch (BadLocationException e) {
		}
	}

	@Override
	protected Document createDefaultModel() {
		return new LimitDocument();
	}

	private class LimitDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}

	}
}