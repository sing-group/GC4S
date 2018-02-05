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

import java.awt.Color;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * A panel that allows user to type a range of double values by using spinners.
 * The introduced range can be obtained by calling
 * {@code DoubleRangeSpinnerInputPanel#getRange()}.
 * Method{@code DoubleRangeSpinnerInputPanel#isValidRange()} returns
 * {@code true} if the minimum value is lower or equal than the maximum value
 * and false otherwise.
 * </p>
 * 
 * <p>
 * Changes in the selected range can be listened
 * {@code DoubleRangeSpinnerInputPanel#PROPERTY_RANGE} property.
 * </p>
 * 
 * @author hlfernandez
 *
 */
public class DoubleRangeSpinnerInputPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_RANGE = "value";

	public static final Color COLOR_INVALID_INPUT = new Color(255,148,148);
	public static final Color COLOR_VALID_INPUT = 
		UIManager.getColor("TextField.background");

	private ChangeListener listener = this::rangeChanged;

	private DoubleRange oldRange;
	private double min;
	private double max;
	private double rangeStart;
	private double rangeEnd;
	private double step;

	private JSpinner rangeStartSpinner;
	private JSpinner rangeEndSpinner;
	private JLabel startLabel;
	private JLabel endLabel;

	/**
	 * Creates a new {@code DoubleRangeSpinnerInputPanel} with the specified minimum
	 * and maximum values.
	 * 
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param rangeStart the range start value
	 * @param rangeEnd the range end value
	 * @param step the step for the spinners
	 */
	public DoubleRangeSpinnerInputPanel(double min, double max,
		double rangeStart, double rangeEnd, double step
	) {
		this.min = min;
		this.max = max;
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
		this.step = step;

		this.init();
	}

	private void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(getStartLabel());
		this.add(getRangeStartSpinner());
		this.add(Box.createHorizontalStrut(5));
		this.add(getEndLabel());
		this.add(getRangeEndSpinner());
	}

	private JLabel getStartLabel() {
		this.startLabel = new JLabel("Start:");

		return this.startLabel;
	}

	private JLabel getEndLabel() {
		this.endLabel = new JLabel("End:");

		return this.endLabel;
	}

	private JSpinner getRangeStartSpinner() {
		this.rangeStartSpinner = new JSpinner(
			new SpinnerNumberModel(
				this.rangeStart, this.min, this.max, this.step
			));
		this.rangeStartSpinner.addChangeListener(listener);
		this.configureTextField(this.rangeStartSpinner);

		return this.rangeStartSpinner;
	}

	protected void configureTextField(JSpinner spinner) {
		JSpinner.NumberEditor editor = 
			new JSpinner.NumberEditor(spinner, "0.0#####");
		JFormattedTextField textField = editor.getTextField();
		textField.setColumns(5);
		spinner.setEditor(editor);
	}

	private JSpinner getRangeEndSpinner() {
		this.rangeEndSpinner = new JSpinner(
			new SpinnerNumberModel(
				this.rangeEnd, this.min, this.max, this.step
			));
		this.rangeEndSpinner.addChangeListener(listener);
		this.configureTextField(this.rangeEndSpinner);
		
		return this.rangeEndSpinner;
	}

	private void checkRangeValues() {
		boolean validRange = isValidRange();
		setRangeTextFieldColor(validRange);
	}
	
	private void setRangeTextFieldColor(boolean validValues) {
		Color backgroundColor = getTextFieldBackgroundColor(validValues);
		((JSpinner.NumberEditor) this.rangeStartSpinner.getEditor())
			.getTextField().setBackground(backgroundColor);
		((JSpinner.NumberEditor) this.rangeEndSpinner.getEditor())
			.getTextField().setBackground(backgroundColor);
	}

	private Color getTextFieldBackgroundColor(boolean validValues) {
		return validValues ? COLOR_VALID_INPUT : COLOR_INVALID_INPUT;
	}

	/**
	 * Returns {@code true} if the minimum value is lower or equal than the
	 * maximum value and {@code false} otherwise.
	 * 
	 * @return {@code true} if the minimum value is lower or equal than the
	 *         maximum value and {@code false} otherwise.
	 */
	public boolean isValidRange() {
		return getMinValue() <= getMaxValue();
	}

	private double getMinValue() {
		return getSpinnerValue(rangeStartSpinner);
	}

	protected static double getSpinnerValue(JSpinner spinner) {
		NumberEditor editor = ((JSpinner.NumberEditor) spinner.getEditor());
		try {
			return editor.getFormat().parse(
					editor.getTextField().getText()
				).doubleValue();
		} catch (ParseException e) {
			return 0d;
		}
	}

	private double getMaxValue() {
		return getSpinnerValue(rangeEndSpinner);
	}
	
	/**
	 * Returns the {@code DoubleRange} introduced by the user.
	 * 
	 * @return the {@code DoubleRange} introduced by the user.
	 */
	public DoubleRange getRange() {
		return new DoubleRange(getMinValue(), getMaxValue());
	}

	private void rangeChanged(ChangeEvent e) {
		DoubleRange newRange = getRange();
		this.firePropertyChange(PROPERTY_RANGE, oldRange, newRange);
		this.oldRange = newRange;

		this.checkRangeValues();
	}

	public void setStartLabel(String label) {
		this.startLabel.setText(label);
	}

	public void setEndLabel(String label) {
		this.endLabel.setText(label);
	}
}
