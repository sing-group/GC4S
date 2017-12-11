package org.sing_group.gc4s.input.text;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;

/**
 * A {@code BindJXTextField} extends a {@code JXTextField} allowing to specify a
 * function to consume text field's value when it changes.
 * 
 * @author hlfernandez
 *
 */
public class BindJXTextField extends JXTextField {
	private static final long serialVersionUID = 1L;

	private String value;
	private Consumer<String> bind;

	/**
	 * Creates a new {@code BindJXTextField}.
	 * 
	 * @param prompt the prompt value.
	 * @param value the initial text value.
	 * @param bind the function to consume text field's value when it changes.
	 */
	public BindJXTextField(String prompt, String value, Consumer<String> bind) {
		super(prompt);

		this.value = value;
		this.bind = bind;

		this.init();
	}

	private void init() {
		if (!value.equals("")) {
			this.setText(value);
		}

		this.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				bind.accept(getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				bind.accept(getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}
}