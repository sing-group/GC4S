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
package org.sing_group.gc4s.ui.icons;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.Component;
import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Shows all icons in the {@code Icons} class.
 * 
 * @author hlfernandez
 *
 */
public class IconsPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	public IconsPanel() {
		this.init();
	}

	private void init() {
		JPanel iconsPanel = new JPanel(new GridLayout(0,3));
		
		Icons icons = new Icons();
		
		for(Field f : icons.getClass().getDeclaredFields()) {
			iconsPanel.add(createIconPanel(f, icons));
		}
		
		this.setViewportView(iconsPanel);
	}

	private Component createIconPanel(Field f, Icons icons) {
		JPanel iconPanel = new JPanel(new GridLayout(1,2));
		iconPanel.add(iconLabel(f));
		iconPanel.add(iconImage(f, icons));
		return iconPanel;
	}

	private Component iconLabel(Field f) {
		JLabel label = new JLabel(f.getName());
		label.setBorder(createEmptyBorder(5, 5, 5, 5));
		return label;
	}

	private Component iconImage(Field f, Icons icons) {
		try {
			return new JLabel((ImageIcon) f.get(icons));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return new JPanel();
		}
	}
}
