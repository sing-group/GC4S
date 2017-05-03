package org.sing_group.gc4s.demo;

import java.awt.Color;

import org.sing_group.gc4s.input.JColorChooserButton;

/**
 * An example showing the use of {@link JColorChooserButton}.
 * 
 * @author hlfernandez
 *
 */
public class JColorChooserButtonDemo {

	public static void main(String[] args) {
		DemoUtils.showComponent(new JColorChooserButton(Color.LIGHT_GRAY));
	}
}
