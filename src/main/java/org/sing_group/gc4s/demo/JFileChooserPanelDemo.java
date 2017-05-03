package org.sing_group.gc4s.demo;

import org.sing_group.gc4s.filechooser.JFileChooserPanel;

/**
 * An example showing the use of {@link JFileChooserPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JFileChooserPanelDemo {
	public static void main(String[] args) {
		JFileChooserPanel fileChooserPanel = new JFileChooserPanel(
				JFileChooserPanel.Mode.SAVE, "csv");
		
		/**
		 * Uncomment the following line to hide the label.
		 */
		/*
		 * fileChooserPanel.getComponentLabelFile().setVisible(false);
		 */
		
		DemoUtils.showComponent(fileChooserPanel);
	}
}
