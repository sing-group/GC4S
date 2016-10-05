package es.uvigo.ei.sing.hlfernandez.visualization;

import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.max;
import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.min;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import es.uvigo.ei.sing.hlfernandez.ui.VerticalLabelUI;
import es.uvigo.ei.sing.hlfernandez.utilities.Gradient;
import es.uvigo.ei.sing.hlfernandez.utilities.ImageIOUtils;

/**
 * <p>
 * A {@code JHeatMap} provides a heatmap representation for a {@code double}
 * matrix.
 * </p>
 * 
 * <p>
 * Gradient colors can be changed by calling {@link #setLowColor(Color)} and
 * {@link #setHighColor(Color)} methods. The color gradient is created by
 * {@link Gradient} class. Missing values (represented by {@code Double.NaN})
 * are represented in a different color.
 * </p>
 * 
 * <p>
 * The heatmap can be exported by invoking {@link #toPngImage(File)} method.
 * </p>
 * 
 * @author hlfernandez
 * 
 * @see Gradient
 *
 */
public class JHeatMap extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final double DEFAULT_ZOOM_SCALE 	= 1.25d;
	private static final int 	DEFAULT_SIZE 		= 65;
	private static final Color 	DEFAULT_NAN_COLOR 	= Color.LIGHT_GRAY;
	private static final Color 	DEFAULT_LOW_COLOR 	= Color.GREEN;
	private static final Color 	DEFAULT_HIGH_COLOR 	= Color.RED;
	private static final int 	DEFAULT_STEPS 		= 100;
	
	private int 	cellSize 	= DEFAULT_SIZE;
	private Color 	lowColor 	= DEFAULT_LOW_COLOR;
	private Color 	highColor 	= DEFAULT_HIGH_COLOR;
	private Color 	nanColor 	= DEFAULT_NAN_COLOR;
	
	private double[][] 	data;
	private String[] 	columnNames;
	private String[] 	rowNames;
	
	private JXTable heatmap;
	private HeatMapTableModel heatmapTM;
	
	private Function<Double, Color> doubleToColor;

	/**
	 * Constructs a new {@code JHeatMap} taking {@code model} as data source.
	 * 
	 * @param model
	 *            the {@code JHeatMapModel}.
	 */
	public JHeatMap(JHeatMapModel model) {
		this(model.getData(), model.getRowNames(), model.getColumnNames());
	}
	
	/**
	 * Constructs a new {@code JHeatMap} taking {@code data} as input data matrix
	 * 	and {@code rowNames}/{@code columNames} as names for rows and columns.
	 * 
	 * @param data the input data matrix.
	 * @param rowNames the names for the rows.
	 * @param columnNames the names for the columns
	 */
	public JHeatMap(double[][] data, String[] rowNames, String[] columnNames) {
		this.data = data;
		this.rowNames = rowNames;
		this.columnNames = columnNames;
		
		this.initComponent();
	}

	private void initComponent() {
		this.initializeColors();
		
		this.setLayout(new BorderLayout());
		this.add(getHeatMap(), BorderLayout.CENTER);
	}
	
	private void initializeColors() {
		double max = max(this.data);
		double min = min(this.data);
		Color[] colorGradient = getColorGradient();
		doubleToColor = new Function<Double, Color>() {
			
			@Override
			public Color apply(Double t) {
				if (Double.isNaN(t)) {
					return nanColor;
				} else {
					double normalized = normalize(t, max, min);
					int colorIndex = (int) ((normalized * (DEFAULT_STEPS - 1)));
					return colorGradient[colorIndex];
				}
			}

			private double normalize(double d, double max, double min) {
				return (d - min) / (max - min);
			}
		};
		this.updateUI();
	}
	
	private Color[] getColorGradient() {
		return Gradient.createGradient(lowColor, highColor, DEFAULT_STEPS);
	}	

	private Component getHeatMap() {
		this.heatmapTM = new HeatMapTableModel();
		this.heatmap = new JXTable(this.heatmapTM);
		this.heatmap.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		this.heatmap.setTableHeader(null);
		
		this.heatmap.addMouseWheelListener(e -> {
			if (e.getWheelRotation() < 0) {
				zoomIn();
			} else {
				zoomOut();
			}
		});
		
		this.heatmap.setFont(this.heatmap.getFont().deriveFont(
			(float) this.cellSize));
		this.heatmap.setColumnControlVisible(false);
		this.heatmap.setShowHorizontalLines(false);
		this.heatmap.setShowVerticalLines(false);
		this.heatmap.setFillsViewportHeight(false);
		this.heatmap.setRowMargin(0);
		this.heatmap.setIntercellSpacing(new Dimension(0, 0));
		this.heatmap.setCellSelectionEnabled(false);
		this.heatmap.setAutoCreateRowSorter(false);
		this.heatmap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		this.fixCellSize();
		
		return new JScrollPane(this.heatmap);
	}

	private void fixCellSize() {
		this.heatmap.setRowHeight(0, getMaxColumnNameLength());
		IntStream.range(1, this.heatmapTM.getRowCount()).forEach(row -> {
			this.heatmap.setRowHeight(row, this.cellSize);
		});
		
		for(int i = 0; i < this.heatmap.getColumnModel().getColumnCount(); i++) {
			TableColumn c = this.heatmap.getColumnModel().getColumn(i);
			c.setMinWidth(this.cellSize);
			c.setMaxWidth(this.cellSize);
			c.setPreferredWidth(this.cellSize);
		}
		
		int maxRowWidth = getMaxRowNameLength();
		TableColumn rowNamesColumn = this.heatmap.getColumnModel().getColumn(0);
		rowNamesColumn.setMinWidth(maxRowWidth);
		rowNamesColumn.setMaxWidth(maxRowWidth);
		rowNamesColumn.setPreferredWidth(maxRowWidth);
	}
	
	private int getMaxRowNameLength() {
		return Stream.of(this.rowNames).mapToInt(this::requiredLength).max().getAsInt();
	}
	
	private int getMaxColumnNameLength() {
		return Stream.of(this.columnNames).mapToInt(this::requiredLength).max().getAsInt();
	}
	
	private int requiredLength(String s) {
		return new JLabel().getFontMetrics(new JLabel().getFont()).stringWidth(s) + 10;
	}
	
	/**
	 * Zooms in the heatmap by a factor of {@code scale}.
	 * 
	 * @param scale the scaling factor.
	 */
	public void zoomIn(double scale) {
		scaleCellSize(scale);
	}
	
	private void zoomIn() {
		zoomIn(DEFAULT_ZOOM_SCALE);
	}
	
	/**
	 * Zooms out the heatmap by a factor of {@code scale}.
	 * 
	 * @param scale the scaling factor.
	 */
	public void zoomOut(double scale) {
		scaleCellSize(scale);
	}
	
	private void zoomOut() {
		zoomOut((double) 1 / DEFAULT_ZOOM_SCALE);
	}
	
	private void scaleCellSize(double scale) {
		this.cellSize = (int) (this.cellSize * scale);
		this.fixCellSize();
	}

	/**
	 * Exports the heatmap as a png image.
	 * 
	 * @param f the file to save the image.
	 * @throws IOException if an error occurs while saving the image.
	 */
	public void toPngImage(File f) throws IOException {
		ImageIOUtils.toImage(this.heatmap, "png", f);
	}
	
	/**
	 * Establishes the low color of the gradient.
	 * 
	 * @param color the low color of the gradient.
	 */
	public void setLowColor(Color color) {
		this.lowColor = color;
		this.initializeColors();
	}
	
	/**
	 * Establishes the low color of the gradient.
	 * 
	 * @param color the low color of the gradient.
	 */
	public void setHighColor(Color color) {
		this.highColor = color;
		this.initializeColors();
	}
	
	/**
	 * Establishes the color for missing values ({@code Double.NaN}).
	 * 
	 * @param color the color for missing values.
	 */
	public void setNanColor(Color color) {
		this.nanColor = color;
		this.updateUI();
	}
	
	/**
	 * Returns the data matrix.
	 * 
	 * @return the data matrix.
	 */
	public double[][] getData() {
		return data;
	}
	
	/**
	 * Establishes the data matrix of the heatmap.
	 * 
	 * @param data a {@code double[][]}.
	 */
	public void setData(double[][] data) {
		this.data = data;
		this.initializeColors();
		SwingUtilities.invokeLater(() -> {
			this.heatmapTM.fireTableDataChanged();
			this.fixCellSize();
		});
			
	}
	
	class HeatMapTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount() {
			return rowNames.length + 1;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length + 1;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if(rowIndex == 0 && columnIndex == 0) {
				return "";
			} else {
				if (rowIndex == 0) {
					return columnNames[columnIndex-1];
				}
				if (columnIndex == 0) {
					return rowNames[rowIndex-1];
				}
				return new CellValue(data[rowIndex-1][columnIndex-1]);
			}
		}
	}
	
	private final class CellValue {
		
		private double value;

		public CellValue(double value) {
			this.value = value;
		}
		
		public double getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return Double.toString(value);
		}
	}
	
	private final class CustomTableCellRenderer extends JLabel implements
		TableCellRenderer 
	{
		private static final long serialVersionUID = 1L;

		public CustomTableCellRenderer() {
			super();
			this.setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			
			if (value instanceof CellValue) {
				double cellValue = ((CellValue) value).getValue();
				
				this.setBackground(doubleToColor.apply(cellValue));
				this.setText("");
				this.setToolTipText(value.toString());
			} else {
				this.setText(value.toString());
				this.setBackground(Color.WHITE);
				this.setHorizontalAlignment(JLabel.CENTER);
				this.setToolTipText("");
			}

			if (table.convertRowIndexToModel(row) == 0) {
				this.setUI(new VerticalLabelUI(false));
				this.setHorizontalAlignment(JLabel.LEFT);
				this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
			} else {
				this.setUI(new BasicLabelUI());
				this.setHorizontalAlignment(JLabel.RIGHT);
				this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
			}
			return this;
		}
	}
}
