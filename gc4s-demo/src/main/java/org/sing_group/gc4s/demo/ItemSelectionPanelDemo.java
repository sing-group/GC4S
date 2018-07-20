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

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.Component;

import org.sing_group.gc4s.input.ItemSelectionPanel;

/**
 * An example showing the use of {@link ItemSelectionPanel}.
 * 
 * @author hlfernandez
 *
 */
public class ItemSelectionPanelDemo {
	public static void main(String[] args)  {
		showComponent(createItemSelectionPanel(), "ItemSelectionPanel demo");
	}

	private static Component createItemSelectionPanel() {
		ItemSelectionPanel<String> itemSelectionPanel = 
			new ItemSelectionPanel<String>(asList("A", "B", "C", "D"), 2);

		itemSelectionPanel.addPropertyChangeListener(
			ItemSelectionPanel.PROPERTY_SELECTION,
			e -> {
				System.err.println(itemSelectionPanel.getSelectedItems());
			}
		);

		return itemSelectionPanel;
	}
}