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

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sing_group.gc4s.input.text.JLimitedTextField;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link JLimitedTextField}.
 * 
 * @author hlfernandez
 *
 */
public class JLimitedTextFieldDemo {
	public static void main(String[] args) {
		JPanel demoPanel = new JPanel(new GridLayout(2, 2));
		demoPanel.add(new JLabel("With limit 1:"));
		demoPanel.add(new JLimitedTextField("1", 1));
		demoPanel.add(new JLabel("With limit 5:"));
		demoPanel.add(new JLimitedTextField("12345", 5));
		demoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		VisualizationUtils.showComponent(demoPanel);
	}
}
