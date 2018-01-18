package org.sing_group.gc4s.input;

import javax.swing.JComponent;

/**
 * This class encapsulates three components of an input parameter: a description
 * label, a {@code JComponent} to use to retrieve user input, and a label to use
 * as help for this parameter.   
 * 
 * @author hlfernandez
 *
 */
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
