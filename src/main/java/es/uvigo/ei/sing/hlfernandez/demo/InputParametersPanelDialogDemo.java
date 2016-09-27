package es.uvigo.ei.sing.hlfernandez.demo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;

/**
 * An example showing the use of {@link InputParametersPanel} inside an 
 * {@link AbstractInputJDialog}.
 * 
 * @author hlfernandez
 *
 */
public class InputParametersPanelDialogDemo {
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
				return "This is a custom dialog with a InputParametersPanel.";
			}

			protected JPanel getInputComponentsPane() {
				return new InputParametersPanel(getInputParameters());
			}

			private InputParameter[] getInputParameters() {
				InputParameter[] parameters = new InputParameter[1];
				parameters[0] = 
					new InputParameter(
						"An input parameter", 
						new JTextField(15), 
						"The tooltip description."
					);
				return parameters;
			}
			
			@Override
			public void setVisible(boolean b) {
				this.pack();
				super.setVisible(b);
			}
		}

		InputJDialog dialog = new InputJDialog(new JFrame());
		dialog.setVisible(true);
	}
}
