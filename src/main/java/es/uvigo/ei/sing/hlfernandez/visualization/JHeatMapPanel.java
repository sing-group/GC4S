package es.uvigo.ei.sing.hlfernandez.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A {@code JHeatMapPanel} wraps a {@code JHeatMap}, adding a toolbar with
 * options to manipulate it.
 * 
 * @author hlfernandez
 * @see JHeatMap
 */
public class JHeatMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
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
	
	private JHeatMap heatmap;

	/**
	 * Constructs a new {@code JHeatMapPanel} wrapping {@code heatmap}.
	 * 
	 * @param heatmap
	 *            a {@code JHeatMap}.
	 */
	public JHeatMapPanel(JHeatMap heatmap) {
		super(new BorderLayout());
		
		this.heatmap = heatmap;
		this.initComponent();
	}

	private void initComponent() {
		JPanel toolbar = new JPanel();
		toolbar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		BoxLayout layout = new BoxLayout(toolbar, BoxLayout.X_AXIS);
		toolbar.setLayout(layout);
		
		toolbar.add(new JButton(new AbstractAction("Export heatmap as image") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showSaveDialog(JHeatMapPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						heatmap.toPngImage(fc.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}));
		
		JComboBox<ComboColor> lowColorCB = 
			new JComboBox<ComboColor>(ComboColor.values());
		fixComboSize(lowColorCB);
		lowColorCB.setSelectedItem(ComboColor.GREEN);
		lowColorCB.addItemListener(e -> {
			heatmap.setLowColor(
				((ComboColor)lowColorCB.getSelectedItem()).getColor()
			);
		});
		
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(new JLabel("Low: "));
		toolbar.add(lowColorCB);
		
		JComboBox<ComboColor> highColorCB = 
			new JComboBox<ComboColor>(ComboColor.values());
		fixComboSize(highColorCB);
		highColorCB.setSelectedItem(ComboColor.RED);
		highColorCB.addItemListener(e -> {
			heatmap.setHighColor(
				((ComboColor)highColorCB.getSelectedItem()).getColor()
			);
		});
		
		toolbar.add(Box.createHorizontalStrut(10));
		toolbar.add(new JLabel("High: "));
		toolbar.add(highColorCB);

		this.add(toolbar, BorderLayout.NORTH);
		this.add(heatmap, BorderLayout.CENTER);
	}

	private void fixComboSize(JComboBox<ComboColor> lowColorCB) {
		Dimension d = new Dimension(120, 20);
		lowColorCB.setSize(d);
		lowColorCB.setMaximumSize(d);
		lowColorCB.setPreferredSize(d);
		
	}
}