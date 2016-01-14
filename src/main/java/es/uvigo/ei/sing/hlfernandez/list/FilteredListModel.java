package es.uvigo.ei.sing.hlfernandez.list;

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