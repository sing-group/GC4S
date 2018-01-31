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

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.JOptionPaneMessage;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@code JOptionPaneMessage}.
 * 
 * @author hlfernandez
 *
 */
public class JOptionPaneMessageDemo {

	public static void main(String[] args) {
		VisualizationUtils.showComponent(createJOptionPaneMessageDemooComponent());
	}

	private static JComponent createJOptionPaneMessageDemooComponent() {
		JPanel toret = new JPanel();
		toret.setPreferredSize(new Dimension(500, 600));
		JOptionPaneMessage message = new JOptionPaneMessage("A demo message.");
		JButton demoButton = new JButton(new AbstractAction("Demo action") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				demoButtonAction(toret,message);
			}
		});
		toret.add(demoButton);
		return toret;
	}

	protected static void demoButtonAction(JComponent parent, JOptionPaneMessage message) {
		if (message.shouldBeShown()) {
			JOptionPane.showMessageDialog(parent, message.getMessage());
		}
	}
}
