package org.sing_group.gc4s.text;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;

/**
 * An extension of {@code JXTextField} that provides additional features.
 *
 * @author hlfernandez
 *
 */
public class ExtendedJXTextField extends JXTextField {
	private static final long serialVersionUID = 1L;

	private Color emptyColor;
	private boolean textChangedDocumenListenerAdded = false;
	private DocumentListener textChangedDocumentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			textChanged();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			textChanged();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			textChanged();
		}

	};

	/**
	 * Creates a new {@code ExtendedJXTextField} with the specified prompt text.
	 *
	 * @param promptText the text field prompt text.
	 */
	public ExtendedJXTextField(String promptText) {
		super(promptText);
	}

	/**
	 * Sets the color that must be set when the text field is empty.
	 *
	 * @param color the color to use when the text field is empty.
	 */
	public void setEmptyTextFieldColor(Color color) {
		this.emptyColor = color;
		this.addTextChangedDocumentListener();
		textChanged();
	}

	private void addTextChangedDocumentListener() {
		if (!textChangedDocumenListenerAdded) {
			this.getDocument().addDocumentListener(textChangedDocumentListener);
			textChangedDocumenListenerAdded = true;
		}
	}

	private void textChanged() {
		if(this.getText().isEmpty()) {
			this.setBackground(emptyColor);
		} else {
			this.setBackground(null);
		}
	}
}
