package org.sing_group.gc4s.table;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 * A {@code RowHeaderTableModel} warps a {@code TableModel} adding row names.
 * 
 * @author hlfernandez
 *
 */
public class RowHeaderTableModel implements TableModel {
	
	private TableModel wrappedTM;
	private Object[] rowNames;

	/**
	 * Creates a new {@code RowHeaderTableModel} to wrap {@code m} and add
	 * row names.
	 *   
	 * @param m the wrapped {@code TableModel}.
	 * @param rowNames the row names.
	 */
	public RowHeaderTableModel(TableModel m, Object[] rowNames) {
		this.wrappedTM = m;
		this.rowNames = rowNames;
	}

	@Override
	public int getRowCount() {
		return wrappedTM.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return wrappedTM.getColumnCount() + 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnIndex == 0 ? "" : wrappedTM.getColumnName(columnIndex - 1);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return 	columnIndex == 0 ? 
				RowHeaderCell.class : wrappedTM.getColumnClass(columnIndex - 1);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return 	columnIndex == 0 ? 
				false : wrappedTM.isCellEditable(rowIndex, columnIndex - 1);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return new RowHeaderCell(this.rowNames[rowIndex]);
		} else {
			return wrappedTM.getValueAt(rowIndex, columnIndex - 1);
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		wrappedTM.setValueAt(aValue, rowIndex, columnIndex - 1);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		wrappedTM.addTableModelListener(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		wrappedTM.removeTableModelListener(l);
	}

	public static class RowHeaderCell {

		private Object object;

		public RowHeaderCell(Object object) {
			this.object = object;
		}

		@Override
		public String toString() {
			return object.toString();
		}
	}
}
