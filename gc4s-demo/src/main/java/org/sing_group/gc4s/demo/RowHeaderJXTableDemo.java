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

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.Font;

import javax.swing.JScrollPane;

import org.sing_group.gc4s.visualization.table.RowHeaderExtendedJXTable;

/**
 * An example showing the use of {@link RowHeaderExtendedJXTable}.
 * 
 * @author hlfernandez
 *
 */
public class RowHeaderJXTableDemo {
	private static final String columnNames[] = 
		{ "Column 1", "Column 2", "Column 3" };

	private static final String rowNames[] = 
		{ "Row 1", "Row 2", "Row 3", "Row 4" };

	private static final String dataValues[][] =
		{
			{ "12", "234", "67" },
			{ "-123", "43", "853" },
			{ "93", "89.2", "109" },
			{ "279", "9033", "3092" }
		};
	
	public static void main(String[] args) {
		java.util.Locale.setDefault(java.util.Locale.ENGLISH);
		
		RowHeaderExtendedJXTable table = 
			new RowHeaderExtendedJXTable(dataValues, columnNames, rowNames);
		table.setColumnControlVisible(true);
		table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		
		showComponent(new JScrollPane(table), "RowHeaderJXTable demo");
	}
}
