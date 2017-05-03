package org.sing_group.gc4s.utilities;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * A {@code FocusAdapter} implementation that selects all text in a
 * {@code JTextField} when it gains focus.
 * 
 * @author hlfernandez
 *
 */
public class TextFieldSelectionFocusListener extends FocusAdapter {
	
	@Override
	public void focusGained(FocusEvent e) {
		if (e.getComponent() instanceof JTextField) {
			SwingUtilities.invokeLater(() -> {
				((JTextField) e.getComponent()).selectAll();
			});
		}
	}
}
