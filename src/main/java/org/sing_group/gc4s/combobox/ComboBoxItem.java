package org.sing_group.gc4s.combobox;

/**
 * A {@code ComboboxItem} wraps any object so that it can be easily added to
 * {@code JComboBox}.
 * 
 * @author hlfernandez
 *
 */
public class ComboBoxItem<T> {

	private T item;
	private String display;

	/**
	 * Constructs a new {@code ComboBoxItem} wrapping {@code item} and
	 * {@code display} as the string to be displayed in the combo box.
	 * 
	 * @param item the wrapped item
	 * @param display the string to be displayed in the combo box
	 */
	public ComboBoxItem(T item, String display) {
		this.item = item;
		this.display = display;
	}
	
	/**
	 * Returns the wrapped item.
	 * 
	 * @return the wrapped item
	 */
	public T getItem() {
		return this.item;
	}

	@Override
	public String toString() {
		return this.display;
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}

		if (!(aThat instanceof ComboBoxItem<?>)) {
			return false;
		}

		ComboBoxItem<?> that = ((ComboBoxItem<?>) aThat);

		return this.getItem().equals(that.getItem());
	}
}