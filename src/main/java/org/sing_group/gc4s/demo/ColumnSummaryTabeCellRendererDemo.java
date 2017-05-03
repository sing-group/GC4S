package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import java.io.File;
import java.util.Arrays;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

import org.sing_group.gc4s.table.ColumnSummaryTabeCellRenderer;
import org.sing_group.gc4s.table.ExtendedDefaultTableModel;

/**
 * An example showing the use of {@link ColumnSummaryTabeCellRenderer}.
 * 
 * @author hlfernandez
 *
 */
public class ColumnSummaryTabeCellRendererDemo {
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
			new ColumnSummaryTabeCellRenderer(
				table.getTableHeader().getDefaultRenderer(),
				Arrays.asList(0, 1, 2, 3, 4, 5),
				table.getModel()
			)
		);

		showComponent(new JScrollPane(table),
			"ColumnSummaryTabeCellRenderer demo dialog");
	}
}
