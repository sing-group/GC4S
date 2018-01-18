package org.sing_group.gc4s.demo;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.sing_group.gc4s.visualization.table.ExtendedJXTable;

/**
 * An example showing the use of {@link ExtendedJXTable}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJXTableDemo {
	private static final String columnNames[] = 
		{ "Column 1", "Column 2", "Column 3" };

	private static final String dataValues[][] =
			{
				{ "12", "234", "67" },
				{ "-123", "43", "853" },
				{ "93", "89.2", "109" },
				{ "279", "9033", "3092" }
			};
	
	public static void main(String[] args) {
		java.util.Locale.setDefault(java.util.Locale.ENGLISH);
		
		ExtendedJXTable table = new ExtendedJXTable(dataValues, columnNames);
		table.setColumnControlVisible(true);
		table.addAction(new AbstractAction("Demo Action") {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Demo action");
			}
		});
		table.setColumVisibilityActionsEnabled(false);
		
		DemoUtils.showComponent(new JScrollPane(table), "ExtendedJXTable demo dialog");
	}
}
