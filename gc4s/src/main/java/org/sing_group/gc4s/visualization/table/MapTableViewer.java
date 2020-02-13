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

import java.util.Map;

import org.jdesktop.swingx.JXTable;

/**
 * A component that allows showing the entries of a map in a table using a
 * {@code MapTableModel}, which provides one row for each entry with the key in
 * the first column and the value in the second column.
 * 
 * @author hlfernandez
 *
 * @param <K> the type of the keys in the map
 * @param <V> the type of the values in the map
 */
public class MapTableViewer<K, V> extends JXTable {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code MapTableViewer} for the specified map.
	 * 
	 * @param map the source data to create the {@code MapTableModel}
	 */
	public MapTableViewer(Map<K, V> map) {
		super(new MapTableModel<>(map));
	}

	/**
	 * Creates a new {@code MapTableViewer} for the specified model.
	 * 
	 * @param tableModel a {@code MapTableModel}
	 */
	public MapTableViewer(MapTableModel<K, V> tableModel) {
		super(tableModel);
	}
}
