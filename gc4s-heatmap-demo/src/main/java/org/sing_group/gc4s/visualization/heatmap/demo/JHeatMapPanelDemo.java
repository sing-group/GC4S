// An example showing the use of link JHeatMap through a JHeatMapPanel, which 
// adds additional functionalities to the basic heatmap visualization.
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
package org.sing_group.gc4s.visualization.heatmap.demo;

import static java.lang.Double.NaN;
import static java.util.stream.IntStream.range;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.awt.Color;
import java.util.Random;

import org.sing_group.gc4s.input.DoubleRange;
import org.sing_group.gc4s.visualization.heatmap.JHeatMap;
import org.sing_group.gc4s.visualization.heatmap.JHeatMapModel;
import org.sing_group.gc4s.visualization.heatmap.JHeatMapPanel;

public class JHeatMapPanelDemo {
	public static void main(String[] args) {
		// First, the data needed to create the JHeatMap are created: the matrix
		// data and two arrays with the names of the columns and rows.
		final int size = 5;
		final int missingValues = 1;

		double[][] data = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				data[i][j] = i + (double) j / size;
			}
		}

		// Then, some missing values are added to the data matrix to show how
		// the component represents them. Repeated missing values are not
		// controlled.
		Random random = new Random(size * missingValues);
		for (int i = 0; i < missingValues; i++) {
			data[random.nextInt(size)][random.nextInt(size)] = NaN;
		}

		// Now, a JHeatMapModel (that will be used to create the JHeatMap
		// later) with the data created before is created.
		JHeatMapModel heatmapModel = new JHeatMapModel(
			data,
			generateLabels("Row ", data.length),
			generateLabels("Column ", data[0].length)
		);

		// And the JHeatMap is constructed using the JHeatMapModel created
		// previously.
		JHeatMap heatmap = new JHeatMap(heatmapModel);

		// Once the heatmap is created, the range of values that the heatmap
		// should use to create the color gradient is set.
		heatmap.setValuesRange(new DoubleRange(0d, size));

		// Also the colors associated with the lowest and highest values in the
		// data matrix are established.
		heatmap.setLowColor(Color.BLUE);
		heatmap.setHighColor(Color.ORANGE);

		// And finally, the heatmap panel is shown.
		showComponent(new JHeatMapPanel(heatmap), "JHeatMapPanel demo");
	}

	private static final String[] generateLabels(String prefix, int length) {
		return range(0, length)
			.mapToObj(Integer::toString)
			.map(s -> new String(prefix + s))
		.toArray(String[]::new);
	}
}
