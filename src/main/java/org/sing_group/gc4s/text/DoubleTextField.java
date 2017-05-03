package org.sing_group.gc4s.text;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * An extension of {@code JFormattedTextField} to create text fields for allow
 * the input of double values.
 * 
 * @author hlfernandez
 *
 */
public class DoubleTextField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;
	private static NumberFormat format = DecimalFormat
			.getInstance(Locale.ENGLISH);
	private static NumberFormatter formatter = new NumberFormatter(format);

	static {
		format.setMaximumFractionDigits(6);
		formatter.setValueClass(Double.class);
		formatter.setMinimum(-Double.MAX_VALUE);
		formatter.setMaximum(Double.MAX_VALUE);
		formatter.setCommitsOnValidEdit(true);
	}

	/**
	 * Constructs a new {@code DoubleTextField} and sets {@code value} as input
	 * value.
	 * 
	 * @param value the initial input value.
	 */
	public DoubleTextField(double value) {
		super(formatter);
		setValue(value);
	}
	
	@Override
	public Double getValue() {
		return (Double) super.getValue();
	}
}