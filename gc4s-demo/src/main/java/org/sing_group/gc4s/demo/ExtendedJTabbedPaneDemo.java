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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sing_group.gc4s.ui.tabbedpane.ExtendedJTabbedPane;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * An example showing the use of {@link ExtendedJTabbedPane}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJTabbedPaneDemo {
	public static void main(String[] args) {
		ExtendedJTabbedPane tabbedPane = new ExtendedJTabbedPane();
		tabbedPane.setHideTabBarWhenSingleTab(true);
		tabbedPane.addTab("Tab 1", VisualizationUtils.createPanelAndCenterComponent(new JLabel("Tab 1")));
		tabbedPane.setPreferredSize(new Dimension(300,200));

		final JButton addTab = new JButton(new AbstractAction("Add tab") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String tabName = "Tab " + String.valueOf(tabbedPane.getTabCount()+1);
				tabbedPane.addTab(tabName, VisualizationUtils.createPanelAndCenterComponent(new JLabel(tabName)));
			}
		});
		final JButton removeLastTab = new JButton(new AbstractAction("Remove last tab") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeTabAt(tabbedPane.getTabCount()-1);
			}
		});
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				removeLastTab.setEnabled(tabbedPane.getTabCount() > 0);
			}
		});
		
		JPanel demoPanel = new JPanel(new BorderLayout());
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.add(addTab);
		toolbar.add(removeLastTab);
		demoPanel.add(toolbar, BorderLayout.NORTH);
		demoPanel.add(tabbedPane, BorderLayout.CENTER);
		
		demoPanel.setPreferredSize(new Dimension(300,200));
		VisualizationUtils.showComponent(demoPanel, "ExtendedJTabbedPane demo");
	}
}
