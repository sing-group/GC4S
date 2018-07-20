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

import java.io.InvalidClassException;
import java.util.List;

import javax.swing.JList;

import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.input.list.JParallelListsPanel;

/**
 * An example showing the use of {@link JParallelListsPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JParallelListsPanelDemo {

	public static void main(String[] args) throws InvalidClassException {
		JParallelListsPanel<String> parallelLists = new JParallelListsPanel<>(
			createLeftList(), createRightList(), "Left", "Right", true, false);

		showComponent(parallelLists, "JParallelListsPanel demo");
	}

	private static JList<String> createLeftList() {
		return createList(asList("a", "b", "c", "d"));
	}

	private static JList<String> createRightList() {
		return createList(asList("e", "f", "g", "h", "i", "j", "k", "l", "m"));
	}

	private static JList<String> createList(List<String> data) {
		ExtendedDefaultListModel<String> listModel = 
			new ExtendedDefaultListModel<String>();
		listModel.addElements(data);

		return new JList<String>(listModel);
	}
}
