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
package org.sing_group.gc4s.input.text;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.ui.Orientation;

/**
 * <p>
 * A panel that allows user typing a range defined by values, taking into 
 * account that the minimum value should always be lower or equal to the maximum 
 * value.
 * </p>
 * 
 * <p>
 * It uses {@code JXtextField}s for receiving user inputs.
 * </p>
 * 
 * <p>
 * Changes in range selection can be listened by adding a {@code ChangeListener}
 * to this component.
 * </p>
 * 
 * @author hlfernandez
 *
 */
public class IntegerRangeInputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String MINIMUM_VALUE = "Minimum value";
	private static final String MAXIMUM_VALUE = "Maximum value";

	private int min;
	private int max;
	private int oldMinValue;
	private int oldMaxValue;

	private JLabel minValueLabel;
	private String minValueLabelText;
	private JIntegerTextField minValueTf;

	private JLabel maxValueLabel;
	private String maxValueLabelText;
	private JIntegerTextField maxValueTf;
	private int orientation;

	/**
	 * Constructs a new {@code IntegerRangeInputPanel} instance using 
	 * {@code min} and {@code max} as minimum and maximum initial values.
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 */
	public IntegerRangeInputPanel(int min, int max) {
		this(min, max, MINIMUM_VALUE, MAXIMUM_VALUE);
	}

	/**
	 * Constructs a new {@code IntegerRangeInputPanel} instance using 
	 * {@code min} and {@code max} as minimum and maximum initial values.
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 * @param minLabel minimum range label
	 * @param maxLabel maximum range label
	 */
	public IntegerRangeInputPanel(int min, int max, String minLabel,
		String maxLabel
	) {
		this(min, max, minLabel, maxLabel, Orientation.VERTICAL);
	}
	
	/**
	 * Constructs a new {@code IntegerRangeInputPanel} instance using 
	 * {@code min} and {@code max} as minimum and maximum initial values.
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 * @param minLabel minimum range label
	 * @param maxLabel maximum range label
	 * @param orientation the orientation of the two text fields
	 */
	public IntegerRangeInputPanel(int min, int max, String minLabel,
		String maxLabel, Orientation orientation
	) {
		this.min = min;
		this.max = max;
		this.minValueLabelText = minLabel;
		this.maxValueLabelText = maxLabel;
		this.orientation = orientation.getBoxLayoutOrientation();
		
		this.initComponent();
	}

	private void initComponent() {
		this.setLayout(new BoxLayout(this, orientation));
		this.add(this.getMinPanel());
		this.add(Box.createHorizontalStrut(5));
		this.add(this.getMaxPanel());
		this.initializeTextFields();
	}

	private Component getMinPanel() {
		JPanel minPanel = new JPanel(new BorderLayout());
		minPanel.setLayout(new BoxLayout(minPanel, BoxLayout.X_AXIS));
		minValueLabel = new JLabel(minValueLabelText);
		minValueTf = new JIntegerTextField(min);
		minValueTf.setColumns(6);

		minPanel.add(minValueLabel);
		minPanel.add(Box.createHorizontalStrut(5));
		minPanel.add(minValueTf);

		return minPanel;
	}

	private Component getMaxPanel() {
		JPanel maxPanel = new JPanel();
		maxPanel.setLayout(new BoxLayout(maxPanel, BoxLayout.X_AXIS));
		maxValueLabel = new JLabel(maxValueLabelText);
		maxValueTf = new JIntegerTextField(max);
		maxValueTf.setColumns(6);

		maxPanel.add(maxValueLabel);
		maxPanel.add(Box.createHorizontalStrut(5));
		maxPanel.add(maxValueTf);

		return maxPanel;
	}

	private void initializeTextFields() {
		minValueTf.getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				rangeChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				rangeChanged();
			}
		});

		maxValueTf.getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				rangeChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				rangeChanged();
			}
		});

		this.oldMinValue = min;
		this.oldMaxValue = max;
	}

	/**
	 * Returns the minimum value introduced.
	 * 
	 * @return the minimum value introduced
	 */
	public int getMinValue() {
		return minValueTf.getValue();
	}
	
	/**
	 * Sets the minimum value.
	 * 
	 * @param min the minimum value
	 */
	public void setMinValue(int min) {
		this.minValueTf.setText(String.valueOf(min));
	}
	
	/**
	 * Returns the maximum value introduced.
	 * 
	 * @return the maximum value introduced
	 */
	public int getMaxValue() {
		return maxValueTf.getValue();
	}
	
	/**
	 * Sets the maximum value.
	 * 
	 * @param max the maximum value
	 */
	public void setMaxValue(int max) {
		this.maxValueTf.setText(String.valueOf(max));
	}
	
	private void rangeChanged() {
		if(rangeEffectivelyChanged()) {
			this.oldMinValue = getMinValue();
			this.oldMaxValue = getMaxValue();
			checkRange();
			Stream.of(getListeners(ChangeListener.class)).forEach(l -> {
				l.stateChanged(new ChangeEvent(this));
			});
		}
	}

	private boolean rangeEffectivelyChanged() {
		return oldMinValue != getMinValue() || oldMaxValue != getMaxValue();
	}

	private void checkRange() {
		Color color = isValidRange()? null : Color.RED;
		this.minValueTf.setBackground(color);
		this.maxValueTf.setBackground(color);
	}
	
	/**
	 * Returns {@code true} if the minimum value is lower or equal to the
	 * maximum value and {@code false} otherwise.
	 * 
	 * @return {@code true} if the minimum value is lower or equal to the
	 * 		   maximum value and {@code false} otherwise
	 */
	public boolean isValidRange() {
		return getMinValue() <= getMaxValue();
	}

	/**
	 * Adds the specified change listener to receive component events from
	 * this component. If listener {@code l} is {@code null}, no exception is
	 * thrown and no action is performed.
	 *
	 * @param l the {@code ChangeListener}
	 */
	public synchronized void addChangeListener(ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes the specified change listener.
	 *
	 * @param l the {@code ChangeListener}
	 */
	public synchronized void removeChangeListener(ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}
}
