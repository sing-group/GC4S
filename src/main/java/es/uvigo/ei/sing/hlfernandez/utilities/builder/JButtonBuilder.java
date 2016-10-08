package es.uvigo.ei.sing.hlfernandez.utilities.builder;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * A builder class for {@link JButton}.
 * 
 * @author hlfernandez
 *
 */
public class JButtonBuilder {

	private String text;
	private String tooltip;
	private Icon icon;
	private Action action;
	private boolean enabled = true;

	private JButtonBuilder() { }

	/**
	 * Returns a new {@code JButtonBuilder} instance.
	 * 
	 * @return a new {@code JButtonBuilder} instance.
	 */
	public static JButtonBuilder newJButtonBuilder() {
		return new JButtonBuilder();
	}

	/**
	 * Sets the button text.
	 * 
	 * @param text button text.
	 * @return the {@code JButtonBuilder instance}.
	 */
	public JButtonBuilder withText(String text) {
		this.text = text;
		return this;
	}
	
	/**
	 * Sets the button tooltip.
	 * 
	 * @param tooltip button tooltip.
	 * @return the {@code JButtonBuilder instance}.
	 */
	public JButtonBuilder withTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	/**
	 * Sets the button icon.
	 * 
	 * @param icon button icon.
	 * @return the {@code JButtonBuilder instance}.
	 */
	public JButtonBuilder withIcon(Icon icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Sets the button action.
	 * 
	 * @param action the buton action.
	 * @return the {@code JButtonBuilder instance}.
	 */
	public JButtonBuilder thatDoes(Action action) {
		this.action = action;
		return this;
	}
	
	/**
	 * Sets the button disabled.
	 * 
	 * @return the {@code JButtonBuilder instance}.
	 */
	public JButtonBuilder disabled() {
		this.enabled = false;
		return this;
	}
	
	/**
	 * Builds the {@code JButton}.
	 * 
	 * @return a new {@code JButton} instance.
	 */
	public JButton build() {
		JButton build = new JButton(text, icon);
		if (text != null) {
			build.setText(text);
		}
		if(tooltip != null) {
			build.setToolTipText(this.tooltip);
		}
		if (action != null) {
			build.setAction(action);
		}
		if (icon != null) {
			build.setIcon(icon);
		}
		build.setEnabled(enabled);

		return build;
	}
}
