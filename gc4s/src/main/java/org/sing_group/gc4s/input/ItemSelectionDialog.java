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

import static java.awt.BorderLayout.CENTER;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

/**
 * An {@code ItemSelectionDialog} allows users choosing <i>n</i> items from 
 * different combo boxes using an {@code ItemSelectionPanel}. 
 * 
 * @author hlfernandez
 *
 * @param <T> the type of the items.
 * @see ItemSelectionPanel
 */
public class ItemSelectionDialog<T> extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	private List<T> items;
	private int n;
	private JPanel inputComponentsPane;
	private ItemSelectionPanel<T> itemSelectionPanel;
	private Function<T, String> namingFunction;
	private boolean allowRepetitions;

	public ItemSelectionDialog(Window parent, List<T> items, int n) {
		this(parent, items, n, T::toString, false);
	}
	
	public ItemSelectionDialog(Window parent, List<T> items, int n,
		Function<T, String> namingFunction, boolean allowRepetitions
	) {
		super(parent);
		this.items = items;
		this.n = n;
		this.namingFunction = namingFunction;
		this.allowRepetitions = allowRepetitions;

		this.init();
	}

	private void init() {
		this.inputComponentsPane.add(createItemSelectionPanel(), CENTER);
	}

	private Component createItemSelectionPanel() {
		this.itemSelectionPanel = new ItemSelectionPanel<T>(items, n, namingFunction);
		this.itemSelectionPanel.addPropertyChangeListener(ItemSelectionPanel.PROPERTY_SELECTION,
			e -> checkOkButton());
		return this.itemSelectionPanel;
	}

	private void checkOkButton() {
		this.okButton.setEnabled(isValidSelection());
	}

	private boolean isValidSelection() {
		return allowRepetitions || !areRepeatedElementsSelected();
	}

	private boolean areRepeatedElementsSelected() {
		return this.getSelectedItems().stream().distinct().count() < n;
	}

	@Override
	protected String getDialogTitle() {
		return "Item selection";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select items.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponentsPane = new JPanel(new BorderLayout());
		return inputComponentsPane;
	}

	public List<T> getSelectedItems() {
		return this.itemSelectionPanel.getSelectedItems();
	}
	
	@Override
	public void setVisible(boolean b) {
		this.checkOkButton();
		this.pack();
		super.setVisible(b);
	}
}
