/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.visualization.heatmap;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.lang.Double.isNaN;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;
import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.gc4s.utilities.Gradient.createGradient;
import static org.sing_group.gc4s.utilities.ImageIOUtils.toImage;
import static org.sing_group.gc4s.utilities.MatrixUtils.max;
import static org.sing_group.gc4s.utilities.MatrixUtils.min;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import org.sing_group.gc4s.input.DoubleRange;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.ui.text.VerticalLabelUI;
import org.sing_group.gc4s.utilities.Gradient;
import org.sing_group.gc4s.visualization.ColorKeyLegend;

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
	private static final boolean DEFAULT_MOUSE_ZOOM_ENABLED = true;

 	private int 	cellSize 	= DEFAULT_SIZE;
	private Color 	lowColor 	= DEFAULT_LOW_COLOR;
	private Color 	highColor 	= DEFAULT_HIGH_COLOR;
	private Color 	nanColor 	= DEFAULT_NAN_COLOR;

	private DecimalFormat 	decimalFormat 	= new DecimalFormat();
	private Optional<Font> 	font			= Optional.empty();

	private double[][] 	data;
	private String[] 	columnNames;
	private String[] 	rowNames;
	private Optional<List<String>> visibleRows = Optional.empty();;
	private Optional<List<String>> visibleColumns = Optional.empty();;

	private ColorKeyLegend colorKey;
	private JXTable heatmap;
	private HeatMapTableModel heatmapTM;

	private Function<Double, Color> doubleToColor;

	private double lowValue = Double.NaN;
	private double highValue = Double.NaN;
	private boolean mouseZoomEnabled = DEFAULT_MOUSE_ZOOM_ENABLED;

	/**
	 * Constructs a new {@code JHeatMap} taking {@code model} as data source.
	 *
	 * @param model the {@code JHeatMapModel}
	 */
	public JHeatMap(JHeatMapModel model) {
		this(model.getData(), model.getRowNames(), model.getColumnNames());
	}

	/**
	 * Constructs a new {@code JHeatMap} taking {@code data} as input data
	 * matrix and {@code rowNames}/{@code columNames} as names for rows and
	 * columns.
	 *
	 * @param data the input data matrix
	 * @param rowNames the names for the rows
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
		this.add(getColorKey(), NORTH);
		this.add(getHeatMap(), CENTER);
	}

	private void initializeColors() {
		double min = getLowValue();
		double max = getHighValue();
		Color[] colorGradient = getColorGradient();

		doubleToColor = new Function<Double, Color>() {

			@Override
			public Color apply(Double t) {
				if (isNaN(t)) {
					return nanColor;
				} else {
					double normalized = normalize(checkRange(t), max, min);
					int colorIndex = (int) ((normalized * (DEFAULT_STEPS - 1)));
					return colorGradient[colorIndex];
				}
			}

			private double checkRange(Double t) {
				if (t > max) {
					return max;
				} else if (t < min) {
					return min;
				} else {
					return t;
				}
			}

			private double normalize(double d, double max, double min) {
				return (d - min) / (max - min);
			}
		};

		this.updateUI();
	}

	private Color[] getColorGradient() {
		return createGradient(lowColor, highColor, DEFAULT_STEPS);
	}

	private Component getColorKey() {
		colorKey = new ColorKeyLegend(
			this.lowColor, this.highColor,
			min(this.data), max(this.data)
		);
		return new CenteredJPanel(colorKey);
	}

	private Component getHeatMap() {
		this.heatmapTM = new HeatMapTableModel();
		this.heatmap = new JXTable(this.heatmapTM);
		this.heatmap.setDefaultRenderer(
			Object.class, new CustomTableCellRenderer());
		this.heatmap.setTableHeader(null);

		this.heatmap.addMouseWheelListener(e -> {
			if(!mouseZoomEnabled) {
				return;
			}

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
		range(1, getVisibleRowNames().size() + 1).forEach(row -> {
			this.heatmap.setRowHeight(row, this.cellSize);
		});

		for (int i = 0; i < this.heatmap.getColumnModel().getColumnCount(); i++) {
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
		return 	Stream.of(this.rowNames)
				.mapToInt(this::requiredLength).max().getAsInt();
	}

	private int getMaxColumnNameLength() {
		return 	Stream.of(this.columnNames)
				.mapToInt(this::requiredLength).max().getAsInt();
	}

	private int requiredLength(String s) {
		Font heatmapFont = this.font.orElse(new JLabel().getFont());
		return new JLabel().getFontMetrics(heatmapFont).stringWidth(s) + 10;
	}

	/**
	 * Zooms in the heatmap by a factor of {@code scale}.
	 *
	 * @param scale the scaling factor
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
	 * @param scale the scaling factor
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
	 * @param f the file to save the image
	 * @throws IOException if an error occurs while saving the image
	 */
	public void toPngImage(File f) throws IOException {
		toImage("png", f, this.colorKey, this.heatmap);
	}

	/**
	 * Establishes the low color of the gradient.
	 *
	 * @param color the low color of the gradient
	 */
	public void setLowColor(Color color) {
		this.lowColor = color;
		this.colorKey.setLowColor(color);
		this.initializeColors();
	}

	/**
	 * Returns the low color of the gradient.
	 *
	 * @return the low color of the gradient
	 */
	public Color getLowColor() {
		return lowColor;
	}

	/**
	 * Establishes the high color of the gradient.
	 *
	 * @param color the high color of the gradient
	 */
	public void setHighColor(Color color) {
		this.highColor = color;
		this.colorKey.setHighColor(color);
		this.initializeColors();
	}

	/**
	 * Returns the high color of the gradient.
	 *
	 * @return the high color of the gradient
	 */
	public Color getHighColor() {
		return highColor;
	}

	/**
	 * Establishes the low and high colors of the gradient.
	 *
	 * @param lowColor the low color of the gradient
	 * @param highColor the high color of the gradient
	 */
	public void setColors(Color lowColor, Color highColor) {
		this.lowColor = lowColor;
		this.colorKey.setLowColor(lowColor);
		this.setHighColor(highColor);
	}

	/**
	 * Establishes the color for missing values ({@code Double.NaN}).
	 *
	 * @param color the color for missing values
	 */
	public void setNanColor(Color color) {
		this.nanColor = color;
		this.updateUI();
	}

	/**
	 * Sets the decimal format.
	 *
	 * @param decimalFormat the decimal format
	 */
	public void setDecimalFormat(DecimalFormat decimalFormat) {
		this.decimalFormat = decimalFormat;
		this.colorKey.setDecimalFormat(decimalFormat);
	}

	/**
	 * Returns the data matrix.
	 *
	 * @return the data matrix
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
		this.colorKey.setLowValue(min(data));
		this.colorKey.setHighValue(max(data));
		this.initializeColors();
		invokeLater(() -> {
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
				this.setToolTipText(format(cellValue));
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
				this.setFont(getHeatmapFont());
			} else {
				this.setUI(new BasicLabelUI());
				this.setHorizontalAlignment(JLabel.RIGHT);
				this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				this.setFont(getHeatmapFont());
			}

			return this;
		}

		private String format(double cellValue) {
			return 	Double.isNaN(cellValue) ?
					"N/A" : decimalFormat.format(cellValue);
		}
	}

	/**
	 * Sets the heat map font.
	 *
	 * @param font the heat map font
	 */
	public void setHeatmapFont(Font font) {
		this.font = ofNullable(font);
		this.colorKey.setFont(font);
		this.fixCellSize();
		this.updateUI();
	}

	/**
	 * Returns the heat map font.
	 *
	 * @return the heat map font
	 */
	public Font getHeatmapFont() {
		return this.font.orElse(super.getFont());
	}

	/**
	 * Returns the low value of the heatmap.
	 *
	 * @return the low value of the heatmap
	 */
	public double getLowValue() {
		if (isNaN(lowValue)) {
			return min(this.data);
		} else {
			return lowValue;
		}
	}

	/**
	 * Returns the high value of the heatmap.
	 *
	 * @return the high value of the heatmap
	 */
	public double getHighValue() {
		if (Double.isNaN(highValue)) {
			return max(this.data);
		} else {
			return highValue;
		}
	}

	/**
	 * Sets the range of values used to draw the heatmap.
	 *
	 * @param range a {@code DoubleRange}
	 */
	public void setValuesRange(DoubleRange range) {
		this.lowValue = range.getMin();
		this.highValue = range.getMax();

		this.colorKey.setLowValue(this.lowValue);
		this.colorKey.setHighValue(this.highValue);

		this.initializeColors();
	}

	/**
	 * Returns a list with the row names.
	 *
	 * @return a list with the row names
	 */
	public List<String> getRowNames() {
		return new LinkedList<String>(asList(rowNames));
	}

	/**
	 * Returns a list with the column names.
	 *
	 * @return a list with the column names.
	 */
	public List<String> getColumnNames() {
		return new LinkedList<String>(asList(columnNames));
	}

	/**
	 * Returns a list with the row names that are currently visible.
	 *
	 * @return a list with the row names that are currently visible
	 */
	public List<String> getVisibleRowNames() {
		if (visibleRows.isPresent()) {
			return visibleRows.get();
		} else {
			return getRowNames();
		}
	}

	/**
	 * Returns a list with the column names that are currently visible.
	 *
	 * @return a list with the column names that are currently visible
	 */
	public List<String> getVisibleColumnNames() {
		if (visibleColumns.isPresent()) {
			return visibleColumns.get();
		} else {
			return getColumnNames();
		}
	}

	/**
	 * Sets the visible row names.
	 *
	 * @param rowNames a {@code List} containing the visible row names
	 */
	public void setVisibleRowNames(List<String> rowNames) {
		if (rowNames != null && !rowNames.isEmpty()) {
			this.visibleRows = of(rowNames);
		} else {
			this.visibleRows = empty();
		}
		updateVisibleRows();
	}

	private void updateVisibleRows() {
		updateRowFilter();
	}

	/**
	 * Sets the visible column names.
	 *
	 * @param columnNames a {@code List} containing the visible column names
	 */
	public void setVisibleColumnNames(List<String> columnNames) {
		if (columnNames != null && !columnNames.isEmpty()) {
			this.visibleColumns = of(columnNames);
		} else {
			this.visibleColumns = empty();
		}
		updateVisibleColumns();
	}

	/**
	 * Sets whether the scroll mouse zoom should be enabled or not.
	 *
	 * @param enabled whether the scroll mouse zoom should be enabled or not
	 */
	public void setMouseZoomEnabled(boolean enabled) {
		this.mouseZoomEnabled = enabled;
	}

	private void updateVisibleColumns() {
		List<String> visibleColumnNames = getVisibleColumnNames();
		List<String> columnNames = getColumnNames();
		for (int i = 0; i < columnNames.size(); i++) {
			String colName = columnNames.get(i);
			boolean visible = visibleColumnNames.contains(colName);
			getTableColumnExt(i + 1).setVisible(visible);
		}
	}

	private TableColumnExt getTableColumnExt(int modelIndex) {
		return (TableColumnExt) this.heatmap.getColumns(true).get(modelIndex);
	}

	private void updateRowFilter() {
		this.heatmap.setRowFilter(getRowFilter());
	}

	private TestRowFilter<Object, Object> getRowFilter() {
		return new TestRowFilter<>();
	}

	private class TestRowFilter<M, I> extends RowFilter<M, I> {
		@Override
		public boolean include(RowFilter.Entry<? extends M, ? extends I> entry) {
			if (entry.getIdentifier().equals(0)) {
				return true;
			} else {
				return getVisibleRowNames().contains(entry.getStringValue(0));
			}
		}
	}
}
