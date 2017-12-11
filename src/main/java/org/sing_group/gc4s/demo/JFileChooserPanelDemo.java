package org.sing_group.gc4s.demo;

import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.Mode;

/**
 * An example showing the use of {@link JFileChooserPanel}.
 *
 * @author hlfernandez
 *
 */
public class JFileChooserPanelDemo {
	public static void main(String[] args) {
		JFileChooserPanel fileChooserPanel =
			new JFileChooserPanel(Mode.SAVE, "csv");

		/**
		 * Uncomment the following line to hide the label.
		 */
		/*
		 * fileChooserPanel.getComponentLabelFile().setVisible(false);
		 */

		DemoUtils.showComponent(fileChooserPanel);
	}
}
