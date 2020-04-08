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

import java.io.File;
import java.util.Arrays;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;
import org.sing_group.gc4s.visualization.table.ColumnSummaryTableCellRenderer;
import org.sing_group.gc4s.visualization.table.ExtendedDefaultTableModel;

/**
 * An example showing the use of {@link ColumnSummaryTableCellRenderer}.
 * 
 * @author hlfernandez
 *
 */
public class ColumnSummaryTableCellRendererDemo {
	private static final String COLUMN_NAMES[] = 
		{ "String", "Double", "Float", "Long", "Integer" , "File"};

	private static final Object DATA[][] = {
		{ "1", 1d, 1f, 1l, 1, new File("test.txt") },
		{ "2", 2d, 2f, 2l, 2, new File("test.txt") },
		{ "3", 3d, 3f, 3l, 3, new File("test2.txt") },
		{ "4", 4d, 4f, 4l, 4, new File("test2.txt") }
	};
	
	public static void main(String[] args) {
		java.util.Locale.setDefault(java.util.Locale.ENGLISH);
		
		JXTable table = new JXTable(
			new ExtendedDefaultTableModel(DATA, COLUMN_NAMES)
		);
		table.getTableHeader().setDefaultRenderer(
			new ColumnSummaryTableCellRenderer(
				table.getTableHeader().getDefaultRenderer(),
				Arrays.asList(0, 1, 2, 3, 4, 5),
				table.getModel()
			)
		);

		showComponent(new JScrollPane(table),
			"ColumnSummaryTableCellRenderer demo dialog");
	}
}
