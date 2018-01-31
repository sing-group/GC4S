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
package org.sing_group.gc4s.input.list;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This class extends {@code AbstractListModel} and allows filtering 
 * a given {@code ListModel}.
 * 
 * Based on the idea given by ATrubka at:
 * http://stackoverflow.com/questions/14758313/filtering-jlist-based-on-jtextfield
 * 
 * @author hlfernandez
 *
 * @param <E> the type of elements in this model
 */
public class FilteredListModel<E> extends AbstractListModel<E> {
	private static final long serialVersionUID = 1L;

	public static interface Filter {
		boolean accept(Object element);
	}

	private final ListModel<E> _source;
	private Filter _filter;
	private final ArrayList<Integer> _indices = new ArrayList<Integer>();

	/**
	 * Constructs a {@code FilteredListModel} to encapsulate the source
	 * {@code ListModel}.
	 * 
	 * @param source
	 *            the source {@code ListModel}
	 */
	public FilteredListModel(ListModel<E> source) {
		if (source == null)
			throw new IllegalArgumentException("Source is null");
		_source = source;
		_source.addListDataListener(new ListDataListener() {
			public void intervalRemoved(ListDataEvent e) {
				doFilter();
			}

			public void intervalAdded(ListDataEvent e) {
				doFilter();
			}

			public void contentsChanged(ListDataEvent e) {
				doFilter();
			}
		});
	}

	/**
	 * Sets the model {@code Filter}.
	 * 
	 * @param f
	 *            the model {@code Filter}.
	 */
	public void setFilter(Filter f) {
		_filter = f;
		doFilter();
	}

	private void doFilter() {
		_indices.clear();

		Filter f = _filter;
		if (f != null) {
			int count = _source.getSize();
			for (int i = 0; i < count; i++) {
				Object element = _source.getElementAt(i);
				if (f.accept(element)) {
					_indices.add(i);
				}
			}
			fireContentsChanged(this, 0, getSize() - 1);
		}
	}

	public int getSize() {
		return (_filter != null) ? _indices.size() : _source.getSize();
	}

	/**
	 * Returns the element at the specified index.
	 * 
	 * @return the element at the specified index.
	 */
	public E getElementAt(int index) {
		return (_filter != null) ? _source.getElementAt(_indices.get(index))
				: _source.getElementAt(index);
	}

	/**
	 * Returns the enclosed {@code ListModel}.
	 * 
	 * @return the enclosed {@code ListModel}.
	 */
	public ListModel<E> getSourceListModel() {
		return this._source;
	}
}