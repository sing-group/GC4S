package es.uvigo.ei.sing.hlfernandez;

import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 * Utility class that contains methods to create GUI components.
 * 
 * @author hlfernandez
 */
public final class ComponentFactory {

	/**
	 * Creates a button.
	 * 
	 * @param action
	 *            the button action.
	 * @param enabled
	 *            wether the button is enabled.
	 * @param tooltip
	 *            the tooltip of the button.
	 * @param useText
	 *            if the action text is visible.
	 * @return a new configured button.
	 */
	public static JButton createButton(AbstractAction action,
			final boolean enabled, final String tooltip, boolean useText) {
		final JButton button = new JButton(action);
		button.setEnabled(enabled);
		button.setToolTipText(tooltip);
		if (!useText) {
			button.setText("");
		}
		return button;
	}
}
