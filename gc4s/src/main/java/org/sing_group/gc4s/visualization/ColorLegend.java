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
package org.sing_group.gc4s.visualization;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingConstants.LEFT;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sing_group.gc4s.ui.icons.ColorIcon;

/**
 * A {@code ColorLegend} is a graphical component that shows a legend of colors
 * and labels.
 * 
 * @author hlfernandez
 *
 */
public class ColorLegend extends JPanel {
	private static final long serialVersionUID = 1L;

	public enum Orientation {
		HORIZONTAL, VERTICAL;
	}

	private Map<String, Color> colors;
	private Orientation orientation;

	/**
	 * Creates a new {@code ColorLegend} with the specified colors and
	 * horizontal orientation.
	 * 
	 * @param colors the map of labels and colors.
	 */
	public ColorLegend(Map<String, Color> colors) {
		this(colors, Orientation.HORIZONTAL);
	}

	/**
	 * Creates a new {@code ColorLegend} with the specified colors and
	 * orientation.
	 * 
	 * @param colors the map of labels and colors.
	 * @param orientation the {@code Orientation} of the legend.
	 */
	public ColorLegend(Map<String, Color> colors, Orientation orientation) {
		this.colors = colors;
		this.orientation = orientation;
		this.init();
	}

	private void init() {
		this.setLayout(new GridLayout(getNumRows(), getNumColumns()));
		colors.forEach((label, color) -> {
			this.add(createLabel(label, color));
		});
	}

	private JLabel createLabel(String text, Color color) {
		JLabel label = new JLabel(text, new ColorIcon(16, 16, color), LEFT);
		if (isHorizontal()) {
			label.setBorder(createEmptyBorder(0, 0, 0, 5));
		}
		return label;
	}

	private int getNumColumns() {
		return isHorizontal() ? this.colors.size() : 0;
	}

	private boolean isHorizontal() {
		return this.orientation.equals(Orientation.HORIZONTAL);
	}

	private int getNumRows() {
		return isHorizontal() ? 0 : this.colors.size();
	}
}
