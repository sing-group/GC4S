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
package org.sing_group.gc4s.ui.menu;

import static javax.swing.BorderFactory.createEmptyBorder;

import javax.swing.JSeparator;

/**
 * An extension of {@code JSeparator} that allows specifying a margin for the
 * component. The margin is applied depending on the separator orientation: if
 * orientation is {@code SwingConstants.HORIZONTAL} then margins of the
 * specified size are created for the bottom and top sides of the separator; if
 * orientation is {@code SwingConstants.VERTICAL} then margins are created for
 * the left and right sides of the separator.
 *
 * @author hlfernandez
 *
 */
public class ExtendedJSeparator extends JSeparator {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code ExtendedJSeparator} using the specified margin and
	 * the default separator orientation.
	 *
	 * @param margin the margin of the separator
	 */
	public ExtendedJSeparator(int margin) {
		super();
		setSeparatorBorder(margin);
	}

	/**
	 * Creates a new {@code ExtendedJSeparator} using the specified margin and
	 * the orientation.
	 *
	 * @param margin the margin of the separator
	 * @param orientation an integer specifying
	 *        {@code SwingConstants.HORIZONTAL} or
	 *        {@code SwingConstants.VERTICAL}
	 * @exception IllegalArgumentException if {@code orientation} is neither
	 *            {@code SwingConstants.HORIZONTAL} nor
	 *            {@code SwingConstants.VERTICAL}
	 */
	public ExtendedJSeparator(int margin, int orientation) {
		super(orientation);
		setSeparatorBorder(margin);
	}

	private void setSeparatorBorder(int border) {
		if (this.getOrientation() == JSeparator.HORIZONTAL) {
			this.setBorder(createEmptyBorder(border, 0, border, 0));
		} else {
			this.setBorder(createEmptyBorder(0, border, 0, border));
		}
	}
}
