package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;

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
				toret.add(new JFileChooserPanel(
					JFileChooserPanel.Mode.OPEN, "Select a file:")
				);
				toret.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				return toret;
			}
		}

		InputJDialog dialog = new InputJDialog(new JFrame());
		dialog.setVisible(true);
	}
}
