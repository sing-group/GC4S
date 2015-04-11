package es.uvigo.ei.hlfernandez.demo;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Utility methods for demo classes.
 * 
 * @author hlfernandez
 *
 */
public class DemoUtils {
	
	
	/**
	 * Shows a JFrame containing the specified <code>component</code>.
	 * 
	 * @param component
	 *            JComponent to show
	 */
	public static final void showComponent(JComponent component, String title) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Shows a JFrame containing the specified <code>component</code>.
	 * 
	 * @param component
	 *            JComponent to show
	 */
	public static final void showComponent(JComponent component) {
		showComponent(component, "Demo dialog");
	}
}
