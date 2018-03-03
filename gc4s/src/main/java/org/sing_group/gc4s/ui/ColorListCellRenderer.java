package org.sing_group.gc4s.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * An implementation of {@code ListCellRenderer} to show colors.
 * 
 * @author hlfernandez
 *
 */
public class ColorListCellRenderer extends JButton
	implements ListCellRenderer<Color> {  
	private static final long serialVersionUID = 1L;

	boolean rendering = false;

	public ColorListCellRenderer() {
		setOpaque(true);
	}

	@Override
	public void setBackground(Color bg) {
		if (!rendering) {
			return;
		}

		super.setBackground(bg);
	}

	public Component getListCellRendererComponent(JList<? extends Color> list,
		Color value, int index, boolean isSelected, boolean cellHasFocus
	) {
		this.rendering = true;
		this.setText(" ");
		this.setBackground(value);
		this.rendering = false;
		return this;
	}
}