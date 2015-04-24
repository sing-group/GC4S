package es.uvigo.ei.hlfernandez.demo;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import es.uvigo.ei.sing.hlfernandez.tabbedpane.CloseableJTabbedPane;
import es.uvigo.ei.sing.hlfernandez.text.JLimitedTextField;

/**
 * An example showing the use of {@link JLimitedTextField}.
 * 
 * @author hlfernandez
 *
 */
public class CloseableJTabbedPaneDemo {
	public static void main(String[] args) {
		JTabbedPane demoPanel = new CloseableJTabbedPane();
		demoPanel.addTab("Tab 1", new JLabel("Tab 1"));
		demoPanel.addTab("Tab 2", new JLabel("Tab 2"));
		demoPanel.addTab("Tab 3", new JLabel("Tab 3"));
		demoPanel.setPreferredSize(new Dimension(300,200));
		DemoUtils.showComponent(demoPanel, "CloseableJTabbedPane demo");
	}
}
