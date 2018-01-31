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

import java.awt.event.ItemEvent;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * A builder class to construct toggle buttons.
 * 
 * @param <T> the type of {@link JToggleButton} to build.
 * 
 * @author mrjato
 * @author hlfernandez
 */
public class JToggleButtonBuilder<T extends JToggleButton> {

	private final Supplier<T> constructor;
	private Boolean selected;
	private Boolean enabled;
	private Boolean focusable;
	private String label;
	private ImageIcon selectedIcon;
	private ImageIcon unselectedIcon;
	private String tooltip;
	private Function<Boolean, String> tooltipFunction;
	private Consumer<ItemEvent> onItemStateChanged;

	private JToggleButtonBuilder(Supplier<T> constructor) {
		this.constructor = constructor;
	}

	/**
	 * Creates a new {@link JToggleButtonBuilder} to construct a
	 * {@link JToggleButton}.
	 * 
	 * @return a new {@link JToggleButtonBuilder} to construct a
	 * {@link JToggleButton}.
	 */
	public static JToggleButtonBuilder<JToggleButton> newJToggleButton() {
		return newJToggleButton(JToggleButton::new);
	}

	/**
	 * Creates a new {@link JToggleButtonBuilder} to construct a
	 * {@link JToggleButton}.
	 * 
	 * @param <T> the type of the toggle button to construct.
	 * @param constructor a constructor function that instantiates a new
	 * toggle button of the provided type.
	 * @return a new {@link JToggleButtonBuilder} to construct a
	 * {@link JToggleButton}.
	 */
	public static <T extends JToggleButton> JToggleButtonBuilder<T> newJToggleButton(
		Supplier<T> constructor
	) {
		return new JToggleButtonBuilder<>(constructor); 
	}

	/**
	 * Sets the selection status of the toggle button.
	 * 
	 * @param selected the selection status of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> setSelected(Boolean selected) {
		this.selected = selected;
		return this;
	}

	/**
	 * Sets the enabled status of the toggle button.
	 * 
	 * @param enabled the enabled status of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/**
	 * Sets the focusable feature of the toggle button.
	 * 
	 * @param focusable the focusable feature of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> setFocusable(Boolean focusable) {
		this.focusable = focusable;
		return this;
	}

	/**
	 * Sets the selected icon status of the toggle button.
	 * 
	 * @param selectedIcon the selected icon status of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> withSelectedIcon(ImageIcon selectedIcon) {
		this.selectedIcon = selectedIcon;
		return this;
	}

	/**
	 * Sets the unselection icon of the toggle button.
	 * 
	 * @param unselectedIcon the unselection icon of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> withUnselectedIcon(ImageIcon unselectedIcon) {
		this.unselectedIcon = unselectedIcon;
		return this;
	}
	
	/**
	 * Sets the label of the toggle button.
	 * 
	 * @param label the label of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> withLabel(String label) {
		this.label = label;
		return this;
	}
	
	/**
	 * Sets the tooltip of the toggle button.
	 * 
	 * @param tooltip the tooltip of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> withTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}
	
	/**
	 * Sets a function that generates the tooltip of the toggle button using
	 * the selection state of the toggle button.
	 * 
	 * @param tooltipFunction the function that generates the tooltip of the
	 * toggle button using the selection state of the toggle button.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> withTooltip(
		Function<Boolean, String> tooltipFunction
	) {
		this.tooltipFunction = tooltipFunction;
		return this;
	}
	
	/**
	 * Sets an action to be executed when the button is activated.
	 * 
	 * @param onItemStateChanged an action to be executed when the button is
	 * activated.
	 * @return a reference to this builder.
	 */
	public JToggleButtonBuilder<T> thatDoes(
		Consumer<ItemEvent> onItemStateChanged
	) {
		this.onItemStateChanged = onItemStateChanged;
		return this;
	}
	
	/**
	 * Builds the instance of the toggle button.
	 * 
	 * @return a new configured instance of toggle button.
	 */
	public T build() {
		final T instance = this.constructor.get();
		
		if (this.selected != null) {
			instance.setSelected(this.selected);
		}
		
		if (this.enabled != null) {
			instance.setEnabled(this.enabled);
		}
		
		if (this.focusable != null) {
			instance.setFocusable(this.focusable);
			instance.setFocusPainted(this.focusable);
		}
		
		if (this.label != null) {
			instance.setText(this.label);
		}
		
		if (this.selectedIcon != null) {
			instance.setSelectedIcon(this.selectedIcon);
			instance.setDisabledSelectedIcon(this.selectedIcon);
			instance.setRolloverSelectedIcon(this.selectedIcon);
		}
		
		if (this.unselectedIcon != null) {
			instance.setIcon(this.unselectedIcon);
			instance.setDisabledIcon(this.unselectedIcon);
			instance.setRolloverIcon(this.unselectedIcon);
		}
		
		if (this.tooltipFunction != null) {
			instance.setToolTipText(tooltipFunction.apply(this.selected));
			instance.addItemListener(e -> {
				instance.setToolTipText(
					tooltipFunction.apply(instance.isSelected())
				);
			});
		} else if (this.tooltip != null) {
			instance.setToolTipText(this.tooltip);
		}
		
		if (this.onItemStateChanged != null) {
			instance.addItemListener(e -> onItemStateChanged.accept(e));
		}
		
		return instance;
	}
}