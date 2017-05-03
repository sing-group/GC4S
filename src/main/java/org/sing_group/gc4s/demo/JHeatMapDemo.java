package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.sing_group.gc4s.input.DoubleRange;
import org.sing_group.gc4s.visualization.JHeatMap;
import org.sing_group.gc4s.visualization.JHeatMapModel;
import org.sing_group.gc4s.visualization.JHeatMapPanel;

/**
 * An example showing the use of {@link JHeatMap}.
 * 
 * @author hlfernandez
 *
 */
public class JHeatMapDemo {
	
	public static void main(String[] args) {
		double[][] data = new double[][] {
				new double[] { 1.0d, 2.0d, 3.0d, 4.0d, Double.NaN },
				new double[] { 1.1d, 2.1d, 3.1d, 4.1d, 5.1d },
				new double[] { 1.2d, 2.2d, 3.2d, 4.2d, 5.2d },
				new double[] { 1.3d, 2.3d, 3.3d, 4.3d, 5.3d },
				new double[] { 1.4d, 2.4d, 3.4d, 4.4d, 5.4d },
		};

		JHeatMap heatmap = new JHeatMap(
			new JHeatMapModel(
				data, 
				generateRowNames(data), 
				generateColumnNames(data)
			)
		);

		heatmap.setValuesRange(new DoubleRange(0d, 7d));

		showComponent(new JHeatMapPanel(heatmap), "JHeatMap demo");
	}
	
	private static String[] generateRowNames(double[][] data) {
		List<String> rownames = IntStream.range(0, data.length)
			.mapToObj(String::valueOf).map(s -> new String("R" + s))
			.collect(Collectors.toList());
		
		return rownames.toArray(new String[rownames.size()]);
	}
	
	private static String[] generateColumnNames(double[][] data) {
		List<String> colnames = IntStream.range(0, data[0].length)
			.mapToObj(String::valueOf).map(s -> new String("Column " + s))
			.collect(Collectors.toList());
		
		return colnames.toArray(new String[colnames.size()]);
	}
}
