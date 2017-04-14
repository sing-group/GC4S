package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;

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
	 * @param component the {@code Component} to show
	 * @param title the title for the window
	 */
	public static final void showComponent(Component component, String title) {
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
		return new CenteredJPanel(component);
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

	/**
	 * Sets the Nimbus look and feel.
	 */
	public static final void setNimbusLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}

	/**
	 * Sets the value of the property {@code Nimbus.keepAlternateRowColor} in
	 * {@code UIManager} to {@code true}.
	 * 
	 */
	public static void setNimbusKeepAlternateRowColor() {
		setNimbusKeepAlternateRowColor(Boolean.TRUE);
	}

	/**
	 * Sets the value of the property {@code Nimbus.keepAlternateRowColor} in
	 * {@code UIManager}.
	 * 
	 * @param keep the value of the property {@code Nimbus.keepAlternateRowColor}
	 */
	public static void setNimbusKeepAlternateRowColor(boolean keep) {
		UIManager.put("Nimbus.keepAlternateRowColor", keep);
	}
}
