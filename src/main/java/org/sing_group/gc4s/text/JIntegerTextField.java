package org.sing_group.gc4s.text;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import org.sing_group.gc4s.utilities.TextFieldSelectionFocusListener;

/**
 * This class extends {@code JFormattedTextField} and only accepts integers as
 * input.
 *
 * @author hlfernandez
 *
 */
public class JIntegerTextField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code JIntegerTextField} and initializes it with the
	 * specified value.
	 *
	 * @param value the initial value
	 */
	public JIntegerTextField(Integer value) {
	    this.setFormatter(configureFormatter());
	    this.setValue(value);
	    this.addFocusListener(new TextFieldSelectionFocusListener());
	}

	private AbstractFormatter configureFormatter() {
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);

	    return formatter;
	}

	@Override
	public Integer getValue() {
		return (Integer) super.getValue();
	}
}
