package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

import es.uvigo.ei.sing.hlfernandez.visualization.ColorKeyLegend;

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