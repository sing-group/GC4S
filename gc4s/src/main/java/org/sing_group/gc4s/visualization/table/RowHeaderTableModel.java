/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.visualization.table;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 * A {@code RowHeaderTableModel} wraps a {@code TableModel} adding row names.
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
