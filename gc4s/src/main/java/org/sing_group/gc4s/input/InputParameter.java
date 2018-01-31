/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato, 
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
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
