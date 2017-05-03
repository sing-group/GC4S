package org.sing_group.gc4s.demo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.JOptionPaneMessage;

/**
 * An example showing the use of {@code JOptionPaneMessage}.
 * 
 * @author hlfernandez
 *
 */
public class JOptionPaneMessageDemo {

	public static void main(String[] args) {
		DemoUtils.showComponent(createJOptionPaneMessageDemooComponent());
	}

	private static JComponent createJOptionPaneMessageDemooComponent() {
		JPanel toret = new JPanel();
		toret.setPreferredSize(new Dimension(500, 600));
		JOptionPaneMessage message = new JOptionPaneMessage("A demo message.");
		JButton demoButton = new JButton(new AbstractAction("Demo action") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				demoButtonAction(toret,message);
			}
		});
		toret.add(demoButton);
		return toret;
	}

	protected static void demoButtonAction(JComponent parent, JOptionPaneMessage message) {
		if (message.shouldBeShown()) {
			JOptionPane.showMessageDialog(parent, message.getMessage());
		}
	}
}
