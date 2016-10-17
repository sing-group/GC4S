package es.uvigo.ei.sing.hlfernandez.utilities;

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