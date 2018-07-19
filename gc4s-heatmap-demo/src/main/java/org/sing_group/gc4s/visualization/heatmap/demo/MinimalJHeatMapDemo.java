/*
 * #%L
 * GC4S heatmap demo
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
package org.sing_group.gc4s.visualization.heatmap.demo;

import static java.lang.Double.NaN;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import org.sing_group.gc4s.visualization.heatmap.JHeatMap;

//An example to show the simplest usage of the JHeatMap.
public class MinimalJHeatMapDemo {
	public static void main(String[] args) {
		// Creation of the data needed to create the JHeatMap: the matrix data
		// and two arrays with the names of the columns and rows.
		double[][] data = {
			{1, 2, 3, 4, 5},
			{6, 7, 8, 9, NaN},
			{10, 11, 12, 13, 14},
		};
		String[] rowNames = { "R1", "R2", "R3" };
		String[] columnNames = { "C1", "C2", "C3", "C4", "C5" };

		// Creation of the JHeatMap component using the data created before. It
		// can also be instantiated with a JHeatMapModel, wich takes the same
		// data than this constructor.
		JHeatMap heatmap = new JHeatMap(data, rowNames, columnNames);

		// Finally, the heatmap is shown.
		showComponent(heatmap, "JHeatMap demo");
	}
}
