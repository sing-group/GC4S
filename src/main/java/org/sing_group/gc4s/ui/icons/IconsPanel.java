package org.sing_group.gc4s.ui.icons;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.Component;
import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
