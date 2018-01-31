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

import java.util.List;

import javax.swing.DefaultListModel;

import org.sing_group.gc4s.input.list.event.ExtendedDefaultListModelListener;

/**
 *
 * This class extends {@link DefaultListModel} to add move down/up element and
 * add several elements functionalities.
 *
 * @author hlfernandez
 *
 */
public class ExtendedDefaultListModel<E> extends DefaultListModel<E> {
	private static final long serialVersionUID = 1L;

	/**
	 * Moves down the element at {@code index} and returns true. If the element
	 * cannot be moved down (e.g. it is the last element) it returns false.
	 *
	 * @param index
	 *            The element index to move down.
	 * @return true if the element was moved and false if the element cannot be
	 *         moved down.
	 */
	public boolean moveDown(int index) {
		if (index < 0 || index >= (getSize() - 1)) {
			return false;
		} else {
			E selected = getElementAt(index);
			E other = getElementAt(index + 1);
			set(index + 1, selected);
			set(index, other);
			return true;
		}
	}

	/**
	 * Moves up the element at {@code index} and returns true. If the element
	 * cannot be moved down (e.g. it is the first element) it returns false.
	 *
	 * @param index
	 *            The element index to move up.
	 * @return true if the element was moved and false if the element cannot be
	 *         moved up.
	 */
	public boolean moveUp(int index) {
		if (index <= 0 || (index > getSize() - 1)) {
			return false;
		} else {
			E selected = getElementAt(index);
			E other = getElementAt(index - 1);
			set(index - 1, selected);
			set(index, other);
			return true;
		}
	}

	public void addElements(List<E> elements){
		for(E element : elements){
			addElement(element);
		}
		fireAddElementsEvent();
	}

	private void fireAddElementsEvent() {
		for(ExtendedDefaultListModelListener l : this.getExtendedDefaultListModelListeners()) {
			l.elementsAdded(this);
		}
	}

	/**
	 * Adds the specified {@code ExtendedDefaultListModelListener} to the listeners list.
	 *
	 * @param l a {@code ExtendedDefaultListModelListener}
	 */
	public synchronized void addExtendedDefaultListModelListener(ExtendedDefaultListModelListener l) {
		this.listenerList.add(ExtendedDefaultListModelListener.class, l);
	}

	/**
	 * Removes the specified {@code ExtendedDefaultListModelListener} to the listeners list.
	 *
	 * @param l a {@code ExtendedDefaultListModelListener}
	 */
	public synchronized void removeExtendedDefaultListModelListener(ExtendedDefaultListModelListener l) {
		this.listenerList.remove(ExtendedDefaultListModelListener.class, l);
	}

	/**
	 * Returns the list of all registered {@code ExtendedDefaultListModelListener}s.
	 *
	 * @return the list of all registered {@code ExtendedDefaultListModelListener}s
	 */
	public synchronized ExtendedDefaultListModelListener[] getExtendedDefaultListModelListeners() {
		return this.listenerList.getListeners(ExtendedDefaultListModelListener.class);
	}
}
