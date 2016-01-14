package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.Color;
import java.io.InvalidClassException;

import es.uvigo.ei.sing.hlfernandez.input.JColorChooserButton;

/**
 * An example showing the use of {@link JColorChooserButton}.
 * 
 * @author hlfernandez
 *
 */
public class JColorChooserButtonDemo {

	public static void main(String[] args) throws InvalidClassException {
		DemoUtils.showComponent(new JColorChooserButton(Color.LIGHT_GRAY));
	}
}
