package es.uvigo.ei.sing.hlfernandez.visualization;

import static javax.swing.JLabel.LEFT;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.ui.icons.ColorIcon;

/**
 * A {@code ColorLegend} is a graphical component that shows a legend of colors
 * and labels.
 * 
 * @author hlfernandez
 *
 */
public class ColorLegend extends JPanel {
	private static final long serialVersionUID = 1L;

	public enum Orientation {
		HORIZONTAL, VERTICAL;
	}

	private Map<String, Color> colors;
	private Orientation orientation;

	/**
	 * Creates a new {@code ColorLegend} with the specified colors and
	 * horizontal orientation.
	 * 
	 * @param colors the map of labels and colors.
	 */
	public ColorLegend(Map<String, Color> colors) {
		this(colors, Orientation.HORIZONTAL);
	}

	/**
	 * Creates a new {@code ColorLegend} with the specified colors and
	 * orientation.
	 * 
	 * @param colors the map of labels and colors.
	 * @param orientation the {@code Orientation} of the legend.
	 */
	public ColorLegend(Map<String, Color> colors, Orientation orientation) {
		this.colors = colors;
		this.orientation = orientation;
		this.init();
	}

	private void init() {
		this.setLayout(new GridLayout(getNumRows(), getNumColumns()));
		colors.forEach((label, color) -> {
			this.add(new JLabel(label, new ColorIcon(16, 16, color), LEFT));
		});
	}

	private int getNumColumns() {
		return isHorizontal() ? this.colors.size() : 0;
	}

	private boolean isHorizontal() {
		return this.orientation.equals(Orientation.HORIZONTAL);
	}

	private int getNumRows() {
		return isHorizontal() ? 0 : this.colors.size();
	}
}
