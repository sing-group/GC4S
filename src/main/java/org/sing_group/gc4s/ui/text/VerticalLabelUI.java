package org.sing_group.gc4s.ui.text;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 * <p>
 * To rotate a {@code JLabel}‘s text vertically, you only need to use this class
 * and pass it as a parameter to the {@code JLabel}‘s {@code setUI()} method.
 * </p>
 * 
 * <p>
 * For example: {@code 
 * 		JLabel jl = new JLabel("TEST");
 * 		jl.setUI(new VerticalLabelUI(true));
 * }
 * 
 * <p>
 * All credits given to the original creator. Extracted from
 * http://tech.chitgoks.com/2009/11/13/rotate-jlabel-vertically/.
 * </p>
 * 
 * @author hlfernandez
 * @author unknown
 *
 */
public class VerticalLabelUI extends BasicLabelUI {

	static {
		labelUI = new VerticalLabelUI(false);
	}

    protected boolean clockwise;

    /**
     * Constructs a new {@code VerticalLabelUI} with the given orientation.
     * 
     * @param clockwise label orientation: {@code true} for clockwise and 
     * 	{@code false} otherwise.
     */
	public VerticalLabelUI(boolean clockwise) {
		super();
		this.clockwise = clockwise;
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		Dimension dim = super.getPreferredSize(c);
		return new Dimension(dim.height, dim.width);
	}

	private static Rectangle paintIconR = new Rectangle();
	private static Rectangle paintTextR = new Rectangle();
	private static Rectangle paintViewR = new Rectangle();
	private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    @Override
    public void paint(Graphics g, JComponent c) {
        JLabel label = (JLabel)c;
        String text = label.getText();
        Icon icon = (label.isEnabled()) ? 
        	label.getIcon() : label.getDisabledIcon();

		if ((icon == null) && (text == null)) {
			return;
		}

		FontMetrics fm = g.getFontMetrics();
		paintViewInsets = c.getInsets(paintViewInsets);

		paintViewR.x = paintViewInsets.left;
		paintViewR.y = paintViewInsets.top;

		/*
		 * Use inverted height and width
		 */
		paintViewR.height = c.getWidth()
			- (paintViewInsets.left + paintViewInsets.right);
		paintViewR.width = c.getHeight()
			- (paintViewInsets.top + paintViewInsets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

		String clippedText = layoutCL(label, fm, text, icon, paintViewR,
			paintIconR, paintTextR);

		Graphics2D g2 = (Graphics2D) g;
		AffineTransform tr = g2.getTransform();
		if (clockwise) {
			g2.rotate(Math.PI / 2);
			g2.translate(0, -c.getWidth());
		} else {
			g2.rotate(-Math.PI / 2);
			g2.translate(-c.getHeight(), 0);
		}

		if (icon != null) {
			icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
		}

		if (text != null) {
			int textX = paintTextR.x;
			int textY = paintTextR.y + fm.getAscent();

			if (label.isEnabled()) {
				paintEnabledText(label, g, clippedText, textX, textY);
			} else {
				paintDisabledText(label, g, clippedText, textX, textY);
			}
		}
		g2.setTransform(tr);
	}
}