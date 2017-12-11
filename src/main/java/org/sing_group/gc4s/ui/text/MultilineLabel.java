package org.sing_group.gc4s.ui.text;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * An extension of {@code JTextArea} configured as multiline label.
 * 
 * @author hlfernandez
 *
 */
public class MultilineLabel extends JTextArea {
	private static final long serialVersionUID = 1L;
	private String text;

	/**
	 * Creates a new {@code MultilineLabel} to show the specified {@code text}.
	 * 
	 * @param text the text of the label
	 */
	public MultilineLabel(String text) {
		this.text = text;

		this.init();
	}

	private void init() {
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setEditable(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setFont(UIManager.getFont("Label.font"));
		this.setText(text);
	}
}
