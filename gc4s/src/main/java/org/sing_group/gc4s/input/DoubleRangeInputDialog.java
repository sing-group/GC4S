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

import static org.sing_group.gc4s.input.DoubleRangeInputPanel.PROPERTY_RANGE;

import java.awt.Window;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

/**
 * A dialog that allows user to introduce a range of double values.
 * 
 * @author hlfernandez
 *
 */
public class DoubleRangeInputDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private DoubleRange range;
	private DoubleRangeInputPanel rangePanel;

	/**
	 * Creates a new {@code DoubleRangeInputDialog} with the initial
	 * {@code range}.
	 * 
	 * @param parent the parent {@code Window}.
	 * @param range the initial {@code DoubleRange}.
	 */
	public DoubleRangeInputDialog(Window parent, DoubleRange range) {
		super(parent);
		
		this.range = range;
		this.init();
	}

	private void init() {
		rangePanel.setRange(range);
		
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Introduce range";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select a range of values.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		rangePanel = new DoubleRangeInputPanel();
		rangePanel.addPropertyChangeListener(PROPERTY_RANGE,this::rangeChanged);
		
		return rangePanel;
	}

	private void rangeChanged(PropertyChangeEvent e) {
		this.okButton.setEnabled(rangePanel.isValidRange());
	}
	
	/**
	 * Returns the {@code DoubleRange} introduced.
	 * 
	 * @return the {@code DoubleRange} introduced.
	 */
	public DoubleRange getSelectedRange() {
		return rangePanel.getRange();
	}
}
