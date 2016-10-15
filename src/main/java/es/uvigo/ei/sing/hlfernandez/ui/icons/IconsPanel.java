package es.uvigo.ei.sing.hlfernandez.ui.icons;

import static javax.swing.BorderFactory.createEmptyBorder;
import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IconsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public IconsPanel() {
		this.init();
	}

	private void init() {
		JPanel iconsPanel = new JPanel(new GridLayout(0,4));
		
		Icons icons = new Icons();
		
		for(Field f : icons.getClass().getDeclaredFields()) {
			iconsPanel.add(createIconPanel(f, icons));
		}
		
		this.add(iconsPanel);
	}

	private Component createIconPanel(Field f, Icons icons) {
		JPanel iconPanel = new JPanel(new BorderLayout());
		iconPanel.add(iconLabel(f), WEST);
		iconPanel.add(iconImage(f, icons));
		iconPanel.add(Box.createHorizontalStrut(20), EAST);
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
