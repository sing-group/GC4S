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
package org.sing_group.gc4s.input.combobox;

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