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

import java.util.List;
import java.util.Optional;

import javax.swing.RowFilter;

import org.jdesktop.swingx.JXTable;

/**
 * An extension of {@link JXTable} that allows to establish a row filter.
 * 
 * @author hlfernandez
 *
 */
public class FilterableJXTable extends ExtendedJXTable {
	private static final long serialVersionUID = 1L;
	private Optional<List<Integer>> visibleRows;

    /**
     * Instantiates an FilterableJXTable with data in a array or rows and column
     *  names.
     * 
     * @param datavalues row data, as a two-dimensional Array of Objects (by row,
     * 	for column).
     * @param columnnames column names, as a Array of Strings.
     */
	public FilterableJXTable(String[][] datavalues, String[] columnnames) {
		super(datavalues, columnnames);
		visibleRows = Optional.empty();
	}

	/**
	 * Sets all rows visible (i.e. removes the current row filter).
	 */
	public void setAllRowsVisible() {
		visibleRows = Optional.empty();
		updateRowFilter();
	}

	/**
	 * Sets visible only row indexes specified in {@code visibleRows}. 
	 * 
	 * @param visibleRows a list containing the row indexes that must remain
	 * 	visible.
	 */
	public void setVisibleRows(List<Integer> visibleRows) {
		this.visibleRows = Optional.of(visibleRows);
		updateRowFilter();
	}
	
	private void updateRowFilter() {
		this.setRowFilter(getRowFilter());
	}

	public TestRowFilter<Object, Object> getRowFilter() {
		return new TestRowFilter<>();
	}
	
	public Optional<List<Integer>> getVisibleRows() {
		return visibleRows;
	}

	class TestRowFilter<M, I> extends RowFilter<M, I> {
		
		@Override
		public boolean include(RowFilter.Entry<? extends M, ? extends I> entry) {
			if(getVisibleRows().isPresent()) {
				return getVisibleRows().get().contains(entry.getIdentifier());
			} else {
				return true;
			}
		}
	}
}