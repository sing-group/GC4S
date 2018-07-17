/*
 * #%L
 * GC4S statistics tests table
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
package org.sing_group.org.gc4s.statistics.table.ui;

import java.awt.Color;
import java.awt.Component;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;

/**
 * A {@code ColorHighlighter} to highlight {@code Boolean} values in tables.
 *
 * @author hlfernandez
 *
 */
public class BooleanHighlighter extends ColorHighlighter {
	private final static Color COLOR_PRESENCE = new Color(255, 100, 100);
	private final static Color COLOR_ABSENCE = new Color(100, 255, 100);
	private final static Color COLOR_PRESENCE_SELECTED = new Color(100, 255, 255);
	private final static Color COLOR_ABSENCE_SELECTED =  new Color(255, 100, 255);

	@Override
	protected void applyBackground(Component renderer,
		ComponentAdapter adapter
	) {

		if (adapter.getValue() == null) {
			super.applyBackground(renderer, adapter);
		} else {
			if (adapter.getColumnClass().equals(Boolean.class)) {
				final boolean value = ((boolean) adapter.getValue());

				if (adapter.isSelected()) {
					if (value) {
						renderer.setBackground(COLOR_PRESENCE_SELECTED);
					} else {
						renderer.setBackground(COLOR_ABSENCE_SELECTED);
					}
				} else {
					if (value) {
						renderer.setBackground(COLOR_PRESENCE);
					} else {
						renderer.setBackground(COLOR_ABSENCE);
					}
				}
			} else {
				super.applyBackground(renderer, adapter);
			}
		}
	}
}
