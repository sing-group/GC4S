package org.sing_group.gc4s.menu;

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
