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

import static org.sing_group.gc4s.visualization.VisualizationUtils.createPanelAndCenterComponent;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.sing_group.gc4s.input.combobox.ExtendedJComboBox;

/**
 * An example showing the use of {@link ExtendedJComboBox}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJComboBoxDemo {

	public static void main(String[] args) {
        String[] items = {
        	"Item 1: this item needs more width to be visible",
            "Item 2: this item also needs more width to be visible", 
        };
        ExtendedJComboBox<String> combobox = 
        	new ExtendedJComboBox<String>(items);
        combobox.setPreferredSize(new Dimension(180, 20));
        combobox.setAutoAdjustWidth(true);
        
		JPanel demoPanel = createPanelAndCenterComponent(combobox);
		showComponent(demoPanel, "ExtendedJComboBox demo");
	}
}
