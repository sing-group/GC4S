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

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.sing_group.gc4s.ui.tabbedpane.CloseableJTabbedPane;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link CloseableJTabbedPane}.
 * 
 * @author hlfernandez
 *
 */
public class CloseableJTabbedPaneDemo {
	public static void main(String[] args) {
		JTabbedPane demoPanel = new CloseableJTabbedPane();
		demoPanel.addTab("Tab 1", new JLabel("Tab 1"));
		demoPanel.addTab("Tab 2", new JLabel("Tab 2"));
		demoPanel.addTab("Tab 3", new JLabel("Tab 3"));
		demoPanel.setPreferredSize(new Dimension(300,200));
		VisualizationUtils.showComponent(demoPanel, "CloseableJTabbedPane demo");
	}
}
