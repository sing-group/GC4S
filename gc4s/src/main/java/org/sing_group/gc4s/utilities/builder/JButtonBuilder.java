/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato, 
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.sing_group.gc4s.utilities.builder;

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
	 * @param action the button action.
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
			fixAction();
		}
		if (icon != null) {
			build.setIcon(icon);
		}
		build.setEnabled(enabled);

		return build;
	}

	private void fixAction() {
		if (action.getValue(Action.NAME) == null 
			|| (text != null && text.isEmpty())
		) {
			action.putValue(Action.NAME, text);
		}
		if (action.getValue(Action.SHORT_DESCRIPTION) == null) {
			action.putValue(Action.SHORT_DESCRIPTION, tooltip);
		}
		if (action.getValue(Action.LONG_DESCRIPTION) == null) {
			action.putValue(Action.LONG_DESCRIPTION, tooltip);
		}
	}
}
