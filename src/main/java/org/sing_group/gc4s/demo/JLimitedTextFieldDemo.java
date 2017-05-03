package org.sing_group.gc4s.demo;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sing_group.gc4s.text.JLimitedTextField;

/**
 * An example showing the use of {@link JLimitedTextField}.
 * 
 * @author hlfernandez
 *
 */
public class JLimitedTextFieldDemo {
	public static void main(String[] args) {
		JPanel demoPanel = new JPanel(new GridLayout(2, 2));
		demoPanel.add(new JLabel("With limit 1:"));
		demoPanel.add(new JLimitedTextField("1", 1));
		demoPanel.add(new JLabel("With limit 5:"));
		demoPanel.add(new JLimitedTextField("12345", 5));
		demoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		DemoUtils.showComponent(demoPanel);
	}
}
