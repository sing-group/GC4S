package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import javax.swing.JLabel;

import org.sing_group.gc4s.ui.CenteredJPanel;

/**
 * An example showing the use of {@link CenteredJPanel}.
 * 
 * @author hlfernandez
 *
 */
public class CenteredJPanelDemo {

	public static void main(String[] args) {
		showComponent(
			new CenteredJPanel(new JLabel("Centered")), "CenteredJPanel demo");
	}
}