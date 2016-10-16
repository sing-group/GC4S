package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	 * @param component the {@code JComponent} to show
	 * @param title the title for the window
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
	 * @param component JComponent to show
	 */
	public static final void showComponent(JComponent component) {
		showComponent(component, "Demo dialog");
	}
	
	/**
	 * Returns a new {@code JPanel} with {@code component} in the center.
	 * 
	 * @param component the component to add to the {@code JPanel}.
	 * @return a new {@code JPanel} with {@code component} in the center.
	 */
	public static final JPanel createPanelAndCenterComponent(
		JComponent component
	) {
		JPanel toret = new JPanel(new BorderLayout());
		toret.add(new JPanel(), BorderLayout.NORTH);
		toret.add(new JPanel(), BorderLayout.WEST);
		toret.add(new JPanel(), BorderLayout.EAST);
		toret.add(new JPanel(), BorderLayout.SOUTH);
		toret.add(component, BorderLayout.CENTER);
		return toret;
	}
	
	/**
	 * Shows {@code dialog} and ends the application after it is closed.
	 * 
	 * @param dialog the {@code JDialog} to show.
	 */
	public static final void showDialog(JDialog dialog) {
		dialog.setVisible(true);
		System.exit(0);
	}
}
