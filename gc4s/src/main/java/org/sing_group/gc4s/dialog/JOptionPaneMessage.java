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
package org.sing_group.gc4s.dialog;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * A {@code JOptionPaneMessage} object represents a message for a
 * {@code JOptionPane} and adds a check box to allow the user to not show the
 * message again.
 * </p>
 * 
 * @author hlfernandez
 *
 */
public class JOptionPaneMessage {

	private static final String CHECK_BOX_TEXT = "Don't show this message again";
	private JPanel component;
	private JCheckBox showMessageCB;
	private String message;

	/**
	 * Creates a new {@code JOptionPaneMessage} with the specified {@code message}.
	 * @param message the text to show.
	 */
	public JOptionPaneMessage(String message) {
		this.message = message;
		this.showMessageCB = new JCheckBox(CHECK_BOX_TEXT, false);
	}
	
	/**
	 * Returns an {@code Object} to pass to the {@code JOptionPane} methods.
	 * @return an {@code Object} to pass to the {@code JOptionPane} methods.
	 */
	public Object getMessage() {
		if (this.component == null) {
			this.component = new JPanel(new BorderLayout());
			
			JLabel label = new JLabel(this.message);
			Font font = label.getFont().deriveFont(Font.PLAIN);
			label.setFont(font);
			this.showMessageCB.setFont(font);
			this.showMessageCB.setHorizontalAlignment(JCheckBox.RIGHT);
			
			this.component.add(label, BorderLayout.CENTER);
			this.component.add(showMessageCB, BorderLayout.SOUTH);
		}
		return this.component;
	}

	/**
	 * Returns {@code true} if the message should be shown.
	 * @return Returns {@code true} if the message should be shown.
	 */
	public boolean shouldBeShown() {
		return !showMessageCB.isSelected();
	}
}
