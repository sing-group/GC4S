package org.sing_group.gc4s.demo;

import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import org.sing_group.gc4s.visualization.ColorKeyLegend;

/**
 * An example showing the use of {@link ColorKeyLegend}.
 * 
 * @author hlfernandez
 *
 */
public class ColorKeyLegendDemo {

	public static void main(String[] args) {
		ColorKeyLegend colorKeyPanel = new ColorKeyLegend(GREEN, RED, 0d, 100d);
		showComponent(colorKeyPanel, "Color key legend demo");
	}
}