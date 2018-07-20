/*
 * #%L
 * GC4S demo
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
package org.sing_group.gc4s.demo;

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.sing_group.gc4s.input.filechooser.Mode.OPEN;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;

/**
 * An example showing the use of {@link AbstractInputJDialog}.
 * 
 * @author hlfernandez
 *
 */
public class AbstractInputJDialogDemo {
	public static void main(String[] args) {
		class InputJDialog extends AbstractInputJDialog {
			private static final long serialVersionUID = 1L;

			protected InputJDialog(JFrame parent) {
				super(parent);
			}

			protected String getDialogTitle() {
				return "Demo dialog";
			}

			protected String getDescription() {
				return "This is a custom dialog.";
			}

			protected JPanel getInputComponentsPane() {
				JPanel toret = new JPanel();
				toret.setLayout(new GridLayout(0, 1));
				toret.add(new JFileChooserPanel(OPEN, "Select a file:"));
				toret.setBorder(createEmptyBorder(10, 10, 10, 10));

				return toret;
			}
		}

		InputJDialog dialog = new InputJDialog(new JFrame());
		dialog.setVisible(true);
	}
}
