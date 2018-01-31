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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * An extension of {@code JComboBox} that allows specifying whether the size of
 * the combo box must be adapted to its items width when the drop down list is
 * being displayed.
 * 
 * @author hlfernandez
 * @see JComboBox
 *
 * @param <T>
 *            the class of the elements of the combo box.
 */
public class ExtendedJComboBox<T> extends JComboBox<T> {
	private static final int WIDTH_MARGIN = 25;
	private static final long serialVersionUID = 1L;
    private boolean layingOut = false;
    private boolean autoAdjustWidth = false;
    private int maxItemWidth = 0;

	/**
	 * Creates an {@code ExtendedJComboBox} with a default data model. The
	 * default data model is an empty list of objects. Use addItem to add items.
	 * By default the first item in the data model becomes selected.
	 */
	public ExtendedJComboBox() {
		super();
	}

	/**
	 * Creates an {@code ExtendedJComboBox} that contains the elements in the
	 * specified array.
	 * 
	 * @param items
	 *            an array of objects to insert into the combo box.
	 */
	public ExtendedJComboBox(T[] items) {
		super(items);
	}

	/**
	 * Creates an {@code ExtendedJComboBox} that takes its items from an
	 * existing {@code ComboBoxModel}.
	 * 
	 * @param model the {@code ComboBoxModel} that provides the displayed list 
	 * 		        of items
	 */
    public ExtendedJComboBox(ComboBoxModel<T> model) {
    	super(model);
    }

	/**
	 * Sets whether the size of the combo box must be adapted to its items width
	 * or not.
	 * 
	 * @param wide {@code true} if the size of the combo box must be adapted to
	 *             its items width and {@code false} otherwise.
	 */
	public void setAutoAdjustWidth(boolean wide) {
    	this.autoAdjustWidth = wide;
    	maxItemWidth = getWidestItemWidth();
    }
	
	private int getWidestItemWidth() {
		FontMetrics metrics = getFontMetrics();
		int widest = 0;
		for (int i = 0; i < this.getItemCount(); i++) {
			Object item = this.getItemAt(i);
			int lineWidth = metrics.stringWidth(item.toString());
			widest = Math.max(widest, lineWidth);
		}

		return widest + WIDTH_MARGIN;
	}
    
    private FontMetrics getFontMetrics() {
    	Font font = this.getFont();
        return this.getFontMetrics(font);
	}

	private boolean isAutoAdjustWidth() {
        return autoAdjustWidth;
    }
    
	@Override
	public Dimension getSize() {
		Dimension dim = super.getSize();
		if (!layingOut && isAutoAdjustWidth()) {
			dim.width = Math.max(maxItemWidth, dim.width);
		}
		return dim;
	}

	@Override
	public void doLayout() {
		try {
			layingOut = true;
			super.doLayout();
		} finally {
			layingOut = false;
		}
	}
}