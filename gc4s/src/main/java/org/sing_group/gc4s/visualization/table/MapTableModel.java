/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2020 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
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

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

/**
 * A table model for easily displaying all the entries of a map. This model
 * provides one row for each entry with the key in the first column and the
 * value in the second column. The order of the rows is obtained by ordering
 * the keys (using the string comparisons).
 * 
 * @author hlfernandez
 *
 * @param <K> the type of the keys in the map
 * @param <V> the type of the values in the map
 */
public class MapTableModel<K, V> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private Map<K, V> map;
	private List<K> rows;

	/**
	 * Creates a new {@code MapTableModel} for the specified map.
	 * 
	 * @param map the data source of the model
	 */
	public MapTableModel(Map<K, V> map) {
		this.map = map;
		this.rows = sortRows(this.map.keySet());
	}

	protected List<K> sortRows(Set<K> keys) {
		List<K> rows = new LinkedList<>();
		rows.addAll(this.map.keySet());
		Collections.sort(rows, new Comparator<K>() {

			@Override
			public int compare(K o1, K o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		return rows;
	}

	@Override
	public int getRowCount() {
		return map.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnIndex == 0 ? "Key" : "Value";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		K row = this.rows.get(rowIndex);
		return columnIndex == 0 ? row.toString() : this.map.get(row).toString();
	}
}
