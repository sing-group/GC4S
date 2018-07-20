/*
 * #%L
 * GC4S demo
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
package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.util.Locale;

import org.sing_group.gc4s.input.DoubleRangeInputPanel;
import org.sing_group.gc4s.input.DoubleRangeSpinnerInputPanel;

/**
 * An example showing the use of {@link DoubleRangeInputPanel}.
 * 
 * @author hlfernandez
 *
 */
public class DoubleRangeSpinnerInputPanelDemo {
	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		DoubleRangeSpinnerInputPanel panel = 
			new DoubleRangeSpinnerInputPanel(0d, 1d, 0d, 1d, 0.1d);
		panel.addPropertyChangeListener(
			DoubleRangeSpinnerInputPanel.PROPERTY_RANGE, e -> {
				System.err.println(e.getNewValue().toString());
			});
		panel.setStartLabel("Minimum value:");
		panel.setEndLabel("Maximum value:");

		showComponent(panel, "DoubleRangeSpinnerInputPanel demo");
	}
}
