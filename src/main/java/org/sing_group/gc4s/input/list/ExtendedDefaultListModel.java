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
