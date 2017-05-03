package org.sing_group.gc4s.input;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.sing_group.gc4s.text.DoubleTextField;
import org.sing_group.gc4s.utilities.TextFieldSelectionFocusListener;

/**
 * <p>
 * A panel that allows user to type a range of double values. The introduced
 * range can be obtained by calling {@code DoubleRangeInputPanel#getRange()}.
 * Method{@code DoubleRangeInputPanel#isValidRange()} returns {@code true} if
 * the minimum value is lower than the maximum value and false otherwise.
 * </p>
 * 
 * <p>
 * Changes in the selected range can be listened
 * {@code DoubleRangeInputPanel#PROPERTY_RANGE} property.
 * </p>
 * 
 * @author hlfernandez
 *
 */
public class DoubleRangeInputPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_RANGE = "value";

	public static final Color COLOR_INVALID_INPUT = new Color(255,148,148);
	public static final Color COLOR_VALID_INPUT = 
		UIManager.getColor("TextField.background");

	private static final String MINIMUM_VALUE = "Minimum value";
	private static final String MAXIMUM_VALUE = "Maximum value";
	
	private DoubleRange range;

	private PropertyChangeListener listener = this::rangeChanged;
	private FocusListener focusListener = new TextFieldSelectionFocusListener();
	
	private DoubleTextField minRangeValue;
	private DoubleTextField maxRangeValue;
	
	/**
	 * Creates a new {@code DoubleRangeInputPanel} with {@code Double.MIN_VALUE}
	 * and {@code Double.MAX_VALUE} as minimum and maximum values.
	 */
	public DoubleRangeInputPanel() {
		this(Double.MIN_VALUE, Double.MAX_VALUE);
	}

	/**
	 * Creates a new {@code DoubleRangeInputPanel} with the specified minimum
	 * and maximum values.
	 * 
	 * @param min the minimum value.
	 * @param max the maximum value.
	 */
	public DoubleRangeInputPanel(double min, double max) {
		this(new DoubleRange(min, max));
	}

	/**
	 * Creates a new {@code DoubleRangeInputPanel} with the specified range.
	 * 
	 * @param range the range of values.
	 */
	public DoubleRangeInputPanel(DoubleRange range) {
		this.range = range;

		this.init();
	}

	private void init() {
		this.add(new InputParametersPanel(getParameters()));
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[2];
		parameters[0] = new InputParameter(
			MINIMUM_VALUE, getMinimumValueInput(), MINIMUM_VALUE);
		parameters[1] = new InputParameter(
			MAXIMUM_VALUE, getMaximumValueInput(), MAXIMUM_VALUE);
		return parameters;
	}

	private JComponent getMinimumValueInput() {
		minRangeValue = new DoubleTextField(range.getMin());
		minRangeValue.addPropertyChangeListener("value", listener);
		minRangeValue.addFocusListener(focusListener);
		minRangeValue.setColumns(10);
		return minRangeValue;
	}

	private JComponent getMaximumValueInput() {
		maxRangeValue = new DoubleTextField(range.getMax());
		maxRangeValue.addPropertyChangeListener("value", listener);
		maxRangeValue.addFocusListener(focusListener);
		maxRangeValue.setColumns(10);
		return maxRangeValue;
	}

	private void checkRangeValues() {
		boolean validRange = isValidRange();
		setRangeTextFieldColor(validRange);
	}
	
	private void setRangeTextFieldColor(boolean validValues) {
		Color backgroundColor = getTextFieldBackgroundColor(validValues);
		minRangeValue.setBackground(backgroundColor);
		maxRangeValue.setBackground(backgroundColor);
	}
	
	private Color getTextFieldBackgroundColor(boolean validValues) {
		return validValues ? COLOR_VALID_INPUT : COLOR_INVALID_INPUT;
	}

	/**
	 * Returns {@code true} if the minimum value is lower than the maximum value
	 * and {@code false} otherwise.
	 * 
	 * @return {@code true} if the minimum value is lower than the maximum value
	 *         and {@code false} otherwise.
	 */
	public boolean isValidRange() {
		return getMinValue() < getMaxValue();
	}

	private double getMinValue() {
		return minRangeValue.getValue();
	}

	private double getMaxValue() {
		return maxRangeValue.getValue();
	}
	
	/**
	 * Returns the {@code DoubleRange} introduced by the user.
	 * 
	 * @return the {@code DoubleRange} introduced by the user.
	 */
	public DoubleRange getRange() {
		return range;
	}
	
	/**
	 * Establishes the range.
	 * 
	 * @param range the new {@code DoubleRange}.
	 */
	public void setRange(DoubleRange range) {
		this.range = range;
		this.minRangeValue.setValue(range.getMin());
		this.maxRangeValue.setValue(range.getMax());
	}
	
	private void rangeChanged(PropertyChangeEvent e) {
		DoubleRange newRange = new DoubleRange(getMinValue(), getMaxValue());
		this.firePropertyChange(PROPERTY_RANGE, getRange(), newRange);
		this.range = newRange;

		this.checkRangeValues();
	}
}
