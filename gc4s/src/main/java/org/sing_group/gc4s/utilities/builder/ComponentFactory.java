package org.sing_group.gc4s.utilities.builder;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * Utility class that contains methods to create GUI components.
 * 
 * @author hlfernandez
 * @deprecated
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
	 * @deprecated
	 */
	public static JButton createButton(Action action, final boolean enabled,
		final String tooltip, boolean useText) {
		final JButton button = new JButton(action);
		button.setEnabled(enabled);
		button.setToolTipText(tooltip);
		if (!useText) {
			button.setText("");
		}
		return button;
	}
	
	/**
	 * @param icon
	 *            default icon of the toggle button.
	 * @param iconSelected
	 *            selected icon of the toggle button.
	 * @param selected
	 *            whether the toggle button is selected.
	 * @param action
	 *            the button action
	 * @return a new configured toggle button.
	 * @deprecated
	 */
	public static JToggleButton createToggleButton(
		final ImageIcon icon,
		final ImageIcon iconSelected,
		final boolean selected,
		final AbstractAction action
	) {
		final JToggleButton toggleButton = new JToggleButton(action);
		toggleButton.setFocusPainted(false);
		toggleButton.setSelectedIcon(iconSelected);
		toggleButton.setSelected(selected);
		
		return toggleButton;
	}
}
