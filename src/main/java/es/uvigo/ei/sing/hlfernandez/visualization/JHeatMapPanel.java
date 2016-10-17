package es.uvigo.ei.sing.hlfernandez.visualization;

import static es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapOperations.center;
import static es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapOperations.transform;
import static javax.swing.SwingUtilities.getWindowAncestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.FontConfigurationDialog;
import es.uvigo.ei.sing.hlfernandez.menu.HamburgerMenu;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapOperations.Centering;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapOperations.Transform;

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
	private Optional<Font> heatmapFont = Optional.empty();

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
		
		toolbar.add(getMenu());
		
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
		
		toolbar.add(Box.createHorizontalStrut(10));

		this.add(toolbar, BorderLayout.NORTH);
		this.add(heatmap, BorderLayout.CENTER);
	}

	private Component getMenu() {
		HamburgerMenu menu = new HamburgerMenu(HamburgerMenu.Size.SIZE16);

		menu.add(new ExtendedAbstractAction(
			"Transform data", this::transformDataMatrix
		));

		menu.add(new ExtendedAbstractAction(
			"Configure font", this::configureFont
		));

		menu.add(new ExtendedAbstractAction(
			"Export heatmap as image", this::exportAsImage
		));

		return menu;
	}
	
	protected void exportAsImage() {
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
	
	protected void configureFont() {
		FontConfigurationDialog dialog = new FontConfigurationDialog(
			getWindowAncestor(this), getHeatmapFont()
		);
		dialog.setVisible(true);
		
		if(!dialog.isCanceled()) {
			this.setHeatmapFont(dialog.getSelectedFont());
		}
	}

	private void setHeatmapFont(Font font) {
		this.heatmapFont = Optional.ofNullable(font);
		this.heatmap.setHeatmapFont(getHeatmapFont());
	}
	
	/**
	 * Returns the heat map font.
	 * 
	 * @return the heat map font.
	 */
	public Font getHeatmapFont() {
		return this.heatmapFont.orElse(this.getFont());
	}
	
	private void fixComboSize(JComboBox<ComboColor> lowColorCB) {
		Dimension d = new Dimension(120, 20);
		lowColorCB.setSize(d);
		lowColorCB.setMaximumSize(d);
		lowColorCB.setPreferredSize(d);
	}
	
	private void transformDataMatrix() {
		JHeatMapDataOperationsDialog dialog = 
			new JHeatMapDataOperationsDialog(getWindowAncestor(this));
		dialog.setVisible(true);
		
		if(!dialog.isCanceled()) {
			applyTransformations(dialog.getTransform(), dialog.getCentering());
		}
	}
	
	private void applyTransformations(Transform transform, Centering centering) {
		this.heatmap.setData(
			center(
				transform(this.heatmap.getData(), transform), centering, true)
		);
	}
}