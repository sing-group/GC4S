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
package org.sing_group.gc4s.utilities;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * An utility class to compute the size of a {@code String} given a
 * {@code Graphics2D} and a {@code Font}.
 * 
 * @author hlfernandez
 *
 */
public class StringMetrics {

	private Font font;
	private FontRenderContext context;

	/**
	 * Creates a new {@code StringMetrics}.
	 * 
	 * @param g2 a {@code Graphics2D}.
	 * @param font  a {@code Font}.
	 */
	public StringMetrics(Graphics2D g2, Font font) {
		this.font = font;
		this.context = g2.getFontRenderContext();
	}

	/**
	 * Returns the width of {@code message}.
	 * 
	 * @param message a {@code String}.
	 * @return the width of {@code message}.
	 */
	public double getWidth(String message) {
		return getBounds(message).getWidth();
	}

	/**
	 * Returns the height of {@code message}.
	 * 
	 * @param message a {@code String}.
	 * @return the height of {@code message}.
	 */
	public double getHeight(String message) {
		return getBounds(message).getHeight();
	}

	private Rectangle2D getBounds(String message) {
		return font.getStringBounds(message, context);
	}
}