package org.sing_group.gc4s.ui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;

/**
 * 
 * A {@code ColorIcon} draws a square of the established size and color. 
 * 
 * @author hlfernandez
 *
 */
public class ColorIcon implements Icon {
	
	private Color color;
	private int width;
	private int height;

	/**
	 * Creates a new {@code ColorIcon} with the specified {@code color} and a 
	 * size of 32x32.
	 * 
	 * @param color the color of the icon.
	 */
	public ColorIcon(Color color) {
		this(32, 32, color);
	}

	/**
	 * Creates a new {@code ColorIcon} with the specified {@code color} and a 
	 * size of {@code width}x{@code height}.
	 * 
	 * @param width the width of the icon.
	 * @param height the height of the icon.
	 * @param color the color of the icon.
	 */
	public ColorIcon(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.drawRect(x, y, getIconWidth() - 1, getIconHeight() - 2);

		Insets insets = getInsets();
		x += insets.left;
		y += insets.top;

		int w = getIconWidth() 	- insets.left 	- insets.right;
		int h = getIconHeight() - insets.top 	- insets.bottom - 1;

		g.fillRect(x, y, w, h);
	}

	private Insets getInsets() {
		return new Insets(1, 1, 1, 1);
	}

	@Override
	public int getIconWidth() {
		return this.width;
	}

	@Override
	public int getIconHeight() {
		return this.height;
	}
}
