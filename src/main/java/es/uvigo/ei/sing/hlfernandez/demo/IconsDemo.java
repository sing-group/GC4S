package es.uvigo.ei.sing.hlfernandez.demo;

import java.io.InvalidClassException;

import es.uvigo.ei.sing.hlfernandez.input.csv.CsvPanel;
import es.uvigo.ei.sing.hlfernandez.ui.icons.IconsPanel;

/**
 * An example showing the use of {@link CsvPanel}.
 * 
 * @author hlfernandez
 *
 */
public class IconsDemo {

	public static void main(String[] args) throws InvalidClassException {
		DemoUtils.showComponent(new IconsPanel());
	}
}