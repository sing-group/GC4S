package es.uvigo.ei.sing.hlfernandez.dialog;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * A {@code JOptionPaneMessage} object represents a message for a
 * {@code JOptionPane} and adds a check box to allow the user to not show the
 * message again.
 * </p>
 * 
 * @author hlfernandez
 *
 */
public class JOptionPaneMessage {

	private static final String CHECK_BOX_TEXT = "Don't show this message again";
	private JPanel component;
	private JCheckBox showMessageCB;
	private String message;

	/**
	 * Creates a new {@code JOptionPaneMessage} with the specified {@code message}.
	 * @param message the text to show.
	 */
	public JOptionPaneMessage(String message) {
		this.message = message;
		this.showMessageCB = new JCheckBox(CHECK_BOX_TEXT, false);
	}
	
	/**
	 * Returns an {@code Object} to pass to the {@code JOptionPane} methods.
	 * @return an {@code Object} to pass to the {@code JOptionPane} methods.
	 */
	public Object getMessage() {
		if (this.component == null) {
			this.component = new JPanel(new BorderLayout());
			
			JLabel label = new JLabel(this.message);
			Font font = label.getFont().deriveFont(Font.PLAIN);
			label.setFont(font);
			this.showMessageCB.setFont(font);
			this.showMessageCB.setHorizontalAlignment(JCheckBox.RIGHT);
			
			this.component.add(label, BorderLayout.CENTER);
			this.component.add(showMessageCB, BorderLayout.SOUTH);
		}
		return this.component;
	}

	/**
	 * Returns {@code true} if the message should be shown.
	 * @return Returns {@code true} if the message should be shown.
	 */
	public boolean shouldBeShown() {
		return !showMessageCB.isSelected();
	}
}
