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
package org.sing_group.gc4s.input;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 * A button that allows users selecting a color by using a
 * {@code JColorChooser}.
 * 
 * @author hlfernandez
 *
 */
public class JColorChooserButton extends JButton {
	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_COLOR = Color.BLUE;
	private Color currentColor;

	/**
	 * Constructs a new {@code JColorChooserButton} with the default color.
	 */
	public JColorChooserButton() {
		this(DEFAULT_COLOR);
	}
	
	/**
	 * Constructs a new {@code JColorChooserButton} with the specified {@code
	 * color}.
	 * 
	 * @param color the selected color.
	 */
	public JColorChooserButton(Color color) {
		this.currentColor = color;
		init();
	}

	private void init() {
		this.setBackground(currentColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDialog();
			}
		});
	}

	protected void showDialog() {
		JColorChooser colorChooser = getColorChooser();
		int response = JOptionPane.showConfirmDialog(
			getParent(), colorChooser, 
			"Choose a color", JOptionPane.YES_NO_OPTION
		);
		if (response == JOptionPane.YES_OPTION) {
			updateCurrentColor(colorChooser.getSelectionModel()
					.getSelectedColor());
		}
	}
	
	private JColorChooser getColorChooser() {
		if (currentColor != null) {
			return new JColorChooser(currentColor);
		} else {
			return new JColorChooser();
		}
	}

	/**
	 * Sets the component selected color.
	 * 
	 * @param color
	 *            the new color.
	 */
	public void setColor(Color color){
		updateCurrentColor(color);
	}

	private void updateCurrentColor(Color newColor) {
		this.currentColor = newColor;
		this.setBackground(currentColor);
	}

	/**
	 * Returns the current selected color.
	 * 
	 * @return the current selected color.
	 */
	public Color getColor() {
		return this.currentColor;
	}
}
