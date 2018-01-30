package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.util.Random;
import java.util.stream.IntStream;

import org.sing_group.gc4s.input.DoubleRange;
import org.sing_group.gc4s.visualization.heatmap.JHeatMap;
import org.sing_group.gc4s.visualization.heatmap.JHeatMapModel;
import org.sing_group.gc4s.visualization.heatmap.JHeatMapPanel;

/**
 * An example showing the use of {@link JHeatMap}.
 * 
 * @author hlfernandez
 *
 */
public class JHeatMapDemo {
	
	public static void main(String[] args) {
		final int size = 5;
		final int missingValues = 1;

		double[][] data = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				data[i][j] = i + (double) j / size;
			}
		}

		// Repeated missing values are not controlled
		Random random = new Random(size * missingValues);
		for (int i = 0; i < missingValues; i++) {
			data[random.nextInt(size)][random.nextInt(size)] = Double.NaN;
		}

		JHeatMap heatmap = new JHeatMap(
			new JHeatMapModel(
				data, 
				generateRowNames(data), 
				generateColumnNames(data)
			)
		);

		heatmap.setValuesRange(new DoubleRange(0d, size));

		showComponent(new JHeatMapPanel(heatmap), "JHeatMap demo");
	}
	
	private static String[] generateRowNames(double[][] data) {
		return IntStream.range(0, data.length)
			.mapToObj(Integer::toString)
			.map(s -> new String("R" + s))
		.toArray(String[]::new);
	}
	
	private static String[] generateColumnNames(double[][] data) {
		return IntStream.range(0, data[0].length)
			.mapToObj(Integer::toString)
			.map(s -> new String("Column " + s))
		.toArray(String[]::new);
	}
}
