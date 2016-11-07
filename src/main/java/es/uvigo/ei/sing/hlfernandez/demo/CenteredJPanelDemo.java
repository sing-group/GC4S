package es.uvigo.ei.sing.hlfernandez.demo;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import java.io.InvalidClassException;

import javax.swing.JLabel;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;

/**
 * An example showing the use of {@link CenteredJPanel}.
 * 
 * @author hlfernandez
 *
 */
public class CenteredJPanelDemo {

	public static void main(String[] args) throws InvalidClassException {
		showComponent(
			new CenteredJPanel(new JLabel("Centered")), "CenteredJPanel demo");
	}
}