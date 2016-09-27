package es.uvigo.ei.sing.hlfernandez.input;

import javax.swing.JComponent;

public class InputParameter {

	private String label;
	private JComponent input;
	private String helpLabel;

	public InputParameter(String label, JComponent input, String helpLabel) {
		this.label = label;
		this.input = input;
		this.helpLabel = helpLabel;
	}
	
	public String getLabel() {
		return label;
	}
	
	public JComponent getInput() {
		return input;
	}
	
	public String getHelpLabel() {
		return helpLabel;
	}
}
