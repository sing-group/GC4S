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
package org.sing_group.gc4s.input;

import static java.util.Objects.requireNonNull;
import static org.sing_group.gc4s.utilities.Checks.requireStrictPositive;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JComboBox;

import org.sing_group.gc4s.input.combobox.ComboBoxItem;
import org.sing_group.gc4s.ui.CenteredJPanel;

/**
 * An {@code ItemSelectionPanel} allows users choosing <i>n</i> items from 
 * different combo boxes.
 * 
 * @author hlfernandez
 *
 * @param <T> the type of the items.
 */
public class ItemSelectionPanel<T> extends CenteredJPanel {
	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_SELECTION = "gc4s.item.selection";

	private List<T> items;
	private int n;
	private InputParameter[] inputParameters;
	private Function<T, String> namingFunction;

	/**
	 * Creates a new {@code ItemSelectionPanel} with {@code n} combo boxes
	 * to select {@code items}. The visible name in the combo box for each item
	 * is its {@code toString} representation. 
	 *  
	 * @param items a list of selectable items.
	 * @param n an strict positive integer specifying the number of items to 
	 * 	select.
	 */
	public ItemSelectionPanel(List<T> items, int n) {
		this(items, n, T::toString);
	}

	/**
	 * Creates a new {@code ItemSelectionPanel} with {@code n} combo boxes
	 * to select {@code items}. The {@code namingFunction} is applied to each
	 * item to obtain its visible name in the combo box. 
	 * 
	 * @param items a list of selectable items.
	 * @param n an strict positive integer specifying the number of items to 
	 * 	select.
	 * @param namingFunction the function to obtain the visible name in the
	 * 	combo boxes.
	 */
	public ItemSelectionPanel(List<T> items, int n,
		Function<T, String> namingFunction
	) {
		this.items = requireNonNull(items);
		this.n = requireStrictPositive(n, "n must be greater than 0");
		this.namingFunction = requireNonNull(namingFunction);
		
		this.initComponent();
	}

	private void initComponent() {
		this.add(createInputParametersPanel());
	}

	private Component createInputParametersPanel() {
		return new InputParametersPanel(getInputParameters());
	}

	private InputParameter[] getInputParameters() {
		this.inputParameters = new InputParameter[n];
		for (int i = 0; i < n; i++) {
			this.inputParameters[i] = createInputParameter(i);
		}
		return this.inputParameters;
	}

	private InputParameter createInputParameter(int i) {
		JComboBox<ComboBoxItem<T>> comboBox = new JComboBox<ComboBoxItem<T>>();
		getComboBoxItems().forEach(comboBox::addItem);
		String itemName = "Item " + (i+1);
		if(i < comboBox.getItemCount()) {
			comboBox.setSelectedIndex(i);
		}
		comboBox.addItemListener(this::itemSelectionChanged);
		
		return new InputParameter(itemName, comboBox, itemName);
	}

	private List<ComboBoxItem<T>> getComboBoxItems() {
		List<ComboBoxItem<T>> cmbItems = new ArrayList<>();
		for (T i : items) {
			cmbItems.add(new ComboBoxItem<T>(i, namingFunction.apply(i)));
		}
		
		return cmbItems;
	}

	private void itemSelectionChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			this.firePropertyChange(PROPERTY_SELECTION, null, getSelectedItems());
		}
	}

	/**
	 * Returns the list of <i>n</i> selected items.
	 * 
	 * @return the list of selected items.
	 */
	public List<T> getSelectedItems() {
		List<T> selectedItems = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			JComboBox<?> comboBox = (JComboBox<?>) this.inputParameters[i].getInput();
			selectedItems.add(items.get(comboBox.getSelectedIndex()));
		}
		return selectedItems;
	}
}
