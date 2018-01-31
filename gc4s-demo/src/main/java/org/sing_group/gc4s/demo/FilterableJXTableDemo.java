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
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.sing_group.gc4s.visualization.VisualizationUtils;
import org.sing_group.gc4s.visualization.table.FilterableJXTable;

/**
 * An example showing the use of {@link FilterableJXTable}.
 * 
 * @author hlfernandez
 *
 */
public class FilterableJXTableDemo {
	private static final String columnNames[] = 
		{"#", "Column 1", "Column 2", "Column 3" };

	private static final String dataValues[][] =
			{
				{ "1", "12", "234", "67" },
				{ "2", "-123", "43", "853" },
				{ "3", "93", "89.3", "119" },
				{ "4", "94", "89.4", "139" },
				{ "5", "95", "89.5", "129" },
				{ "6", "96", "89.6", "119" },
				{ "7", "279", "9033", "3092" }
			};
	
	public static void main(String[] args) {
		java.util.Locale.setDefault(java.util.Locale.ENGLISH);
		
		FilterableJXTable table = new FilterableJXTable(dataValues, columnNames);
		table.setColumnControlVisible(true);
		table.setColumVisibilityActionsEnabled(false);
		
		VisualizationUtils.showComponent(createDemoPanel(table), "FilterableJXTable demo dialog");
	}

	private static JPanel createDemoPanel(FilterableJXTable table) {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel controlPanel = new JPanel(new BorderLayout());
		JTextField filter = new JTextField();
		JButton applyFilter = new JButton(new AbstractAction("Filter") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> rows = Arrays.asList(filter.getText().split(","));
				List<Integer> visibleRows = 
					rows.stream().map(Integer::new)
					.map(i -> new Integer(i-1))
					.collect(Collectors.toList());
				table.setVisibleRows(visibleRows);
			}
		});
		
		JButton removeFilter = new JButton(new AbstractAction("Remove filter") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				table.setAllRowsVisible();
			}
		});
		
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(applyFilter, BorderLayout.WEST);
		buttonsPanel.add(Box.createHorizontalStrut(5), BorderLayout.CENTER);
		buttonsPanel.add(removeFilter, BorderLayout.EAST);
		
		controlPanel.add(filter, BorderLayout.CENTER);
		controlPanel.add(buttonsPanel, BorderLayout.EAST);
		
		panel.add(controlPanel, BorderLayout.NORTH);
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}
}
