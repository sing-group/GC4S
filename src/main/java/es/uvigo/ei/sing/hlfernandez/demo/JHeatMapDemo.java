package es.uvigo.ei.sing.hlfernandez.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMap;

/**
 * An example showing the use of {@link JHeatMap}.
 * 
 * @author hlfernandez
 *
 */
public class JHeatMapDemo {
	
	private enum ComboColor {
		RED 	(Color.RED, 	"Red"),
		GREEN 	(Color.GREEN, 	"Green"),
		BLUE 	(Color.BLUE, 	"Blue");
		
		private Color color;
		private String name;

		ComboColor(Color color, String name) {
			this.color = color;
			this.name = name;
		}
		
		public Color getColor() {
			return color;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}

	public static void main(String[] args) {
		double[][] data = new double[][] {
				new double[] { 1.0d, 2.0d, 3.0d, 4.0d, 5.0d },
				new double[] { 1.1d, 2.1d, 3.1d, 4.1d, 5.1d },
				new double[] { 1.2d, 2.2d, 3.2d, 4.2d, 5.2d },
				new double[] { 1.3d, 2.3d, 3.3d, 4.3d, 5.3d },
				new double[] { 1.4d, 2.4d, 3.4d, 4.4d, 5.4d },
		};

		JHeatMap heatmap = new JHeatMap(
			data, 
			generateRowNames(data), 
			generateColumnNames(data)
		);
		
		DemoUtils.showComponent(createDemoPanel(heatmap), "JHeatMap demo");
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

	private static JComponent createDemoPanel(JHeatMap heatmap) {
		JPanel demoPanel = new JPanel(new BorderLayout());
		JToolBar toolbar = new JToolBar();
		
		toolbar.add(new AbstractAction("Export heatmap as image") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showSaveDialog(demoPanel);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						heatmap.toPngImage(fc.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JComboBox<ComboColor> lowColorCB = new JComboBox<ComboColor>(ComboColor.values());
		lowColorCB.setSelectedItem(ComboColor.GREEN);
		lowColorCB.addItemListener(e -> {
			heatmap.setLowColor(
				((ComboColor)lowColorCB.getSelectedItem()).getColor()
			);
		});
		
		toolbar.add(Box.createHorizontalStrut(10));
		toolbar.add(new JLabel("Low: "));
		toolbar.add(lowColorCB);
		
		JComboBox<ComboColor> highColorCB = new JComboBox<ComboColor>(ComboColor.values());
		highColorCB.setSelectedItem(ComboColor.RED);
		highColorCB.addItemListener(e -> {
			heatmap.setHighColor(
				((ComboColor)highColorCB.getSelectedItem()).getColor()
			);
		});
		
		toolbar.add(Box.createHorizontalStrut(10));
		toolbar.add(new JLabel("High: "));
		toolbar.add(highColorCB);
		
		demoPanel.add(toolbar, BorderLayout.NORTH);
		demoPanel.add(heatmap, BorderLayout.CENTER);
		
		return demoPanel;
	}
}
