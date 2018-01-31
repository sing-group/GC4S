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
import static org.sing_group.gc4s.visualization.VisualizationUtils.createPanelAndCenterComponent;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.util.List;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;
import org.sing_group.gc4s.visualization.table.BeanTableModel;

/**
 * An example showing the use of {@link BeanTableModel}.
 * 
 * @author hlfernandez
 *
 */
public class BeanTableModelDemo {

	public static class Bean {

		private String name;
		private int value;

		public Bean(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
	
	public static void main(String[] args) {
		List<Bean> beans = asList(new Bean("Row 1", 1), new Bean("Row 2", 2));
		JXTable table = new JXTable(new BeanTableModel<Bean>(beans));
		showComponent(createPanelAndCenterComponent(new JScrollPane(table)));
	}
}
