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
package org.sing_group.gc4s.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;

import org.sing_group.gc4s.event.ListDataAdapter;
import org.sing_group.gc4s.input.list.ColorsListPanel;

/**
 * A dialog that allows a user to select a list of colors using a
 * {@code ColorsListPanel}.
 *
 * @author hlfernandez
 * @see ColorsListPanel
 */
public class ColorsSelectionDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private JPanel inputComponents;
	private ColorsListPanel colorsListPanel;
	
	private int min;
	private int max;

	/**
	 * Constructs a new {@code ColorsSelectionDialog} that requires the user to 
	 * select a number of colors between {@code min} and {@code max} (both are 
	 * inclusive). 
	 * 
	 * @param parent the parent {@code Window}
	 * @param min the minimum number of colors (inclusive)
	 * @param max the maximum number of colors (inclusive)
	 */
	public ColorsSelectionDialog(Window parent, int min, int max) {
		this(parent, min, max, Collections.emptyList());
	}
	
	/**
	 * Constructs a new {@code ColorsSelectionDialog} with the specified initial 
	 * list of colors that requires the user to select a number of colors 
	 * between {@code min} and {@code max} (both are inclusive). 
	 * 
	 * @param parent the parent {@code Window}
	 * @param min the minimum number of colors (inclusive)
	 * @param max the maximum number of colors (inclusive)
	 * @param colors the initial list of {@code Color}s
	 */
	public ColorsSelectionDialog(Window parent, int min, int max,
		List<Color> colors
	) {
		super(parent);
		this.min = min;
		this.max = max;
		this.init(colors);
	}

	private void init(List<Color> colors) {
		this.colorsListPanel = new ColorsListPanel();
		this.colorsListPanel.addColors(colors);
		this.colorsListPanel.addListDataListener(new ListDataAdapter() {

			@Override
			public void intervalRemoved(ListDataEvent e) {
				checkOkButton();
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				checkOkButton();
			}
		});
		this.inputComponents.add(this.colorsListPanel);
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Color selection";
	}

	@Override
	protected String getDescription() {
		return "Select colors";
	}

	@Override
	protected Component getInputComponentsPane() {
		this.inputComponents = new JPanel(new BorderLayout());
		return this.inputComponents;
	}
	
	private void checkOkButton() {
		int count = getSelectedColors().size();
		this.okButton.setEnabled(count >= min && count <= max);
	}

	/**
	 * Returns a list containing the {@code Color}s selected by the user.
	 * 
	 * @return a list containing the {@code Color}s selected by the user
	 */
	public List<Color> getSelectedColors() {
		return this.colorsListPanel.getSelectedColors();
	}
}
