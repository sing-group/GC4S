package org.sing_group.gc4s.visualization.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * An extension of the {@code DefaultTableModel} that provides an implementation
 * of the {@code DefaultTableModel#getColumnClass(int)}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedDefaultTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public ExtendedDefaultTableModel(int rowCount, int columnCount) {
		super(columnCount, rowCount);
	}

	public ExtendedDefaultTableModel(Vector<?> columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public ExtendedDefaultTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public ExtendedDefaultTableModel(Vector<?> data, Vector<?> columnNames) {
		super(data, columnNames);
	}

	public ExtendedDefaultTableModel(Object[][] datavalues,
		String[] columnnames) {
		super(datavalues, columnnames);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return super.getValueAt(0, columnIndex).getClass();
	}
}
