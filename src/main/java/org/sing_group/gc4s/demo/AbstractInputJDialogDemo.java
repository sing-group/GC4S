package org.sing_group.gc4s.demo;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.Mode;

/**
 * An example showing the use of {@link AbstractInputJDialog}.
 * 
 * @author hlfernandez
 *
 */
public class AbstractInputJDialogDemo {
	public static void main(String[] args) {
		class InputJDialog extends AbstractInputJDialog {
			private static final long serialVersionUID = 1L;

			protected InputJDialog(JFrame parent) {
				super(parent);
			}

			protected String getDialogTitle() {
				return "Demo dialog";
			}

			protected String getDescription() {
				return "This is a custom dialog.";
			}

			protected JPanel getInputComponentsPane() {
				JPanel toret = new JPanel();
				toret.setLayout(new GridLayout(0, 1));
				toret.add(new JFileChooserPanel(Mode.OPEN, "Select a file:"));
				toret.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				return toret;
			}
		}

		InputJDialog dialog = new InputJDialog(new JFrame());
		dialog.setVisible(true);
	}
}
