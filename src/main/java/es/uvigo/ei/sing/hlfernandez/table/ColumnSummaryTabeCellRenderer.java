package es.uvigo.ei.sing.hlfernandez.table;

import java.awt.Component;
import java.io.File;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * <p>
 * A table cell renderer that displays a summary of all values of the column
 * that the cell to be rendered belongs. This renderer is intended to be used in
 * {@code JTableHeader}s and can render summaries for columns containing objects
 * of the following classes: String, Double, Float, Integer, Long and File.
 * </p>
 * 
 * <p>
 * For Double, Float, Integer and Long objects a numeric summary is provided,
 * showing maximum, minimum, count, sum and average values. For String and File
 * objects, a summary of the different values present in the column is shown.
 * </p>
 * 
 * <p>
 * To provide support for another classes, method
 * {@link ColumnSummaryTabeCellRenderer#getColumnSummary(TableModel, int)} can
 * be override.
 * </p>
 * 
 * 
 * @author hlfernandez
 *
 */
public class ColumnSummaryTabeCellRenderer
	implements TableCellRenderer, TableModelListener {
	private TableCellRenderer defaultRenderer;
	private List<Integer> summaryColumns;
	private Map<Integer, String> columnSummary = new HashMap<>();
	private static final Collector<CharSequence, ?, String> JOIN_LIST = 
		Collectors.joining("</li><li>", "<ul><li>", "</li></ul>");

	/**
	 * Creates a new {@code ColumnSummaryTabeCellRenderer} that uses
	 * {@code defaultRenderer} to render the component and add the tooltip
	 * summary for all columns. Also adds the created instance as
	 * {@code TableModelListener} of {@code model}.
	 * 
	 * @param defaultRenderer the default {@code TableCellRenderer}
	 * @param model a {@code TableModel}.
	 */
	public ColumnSummaryTabeCellRenderer(TableCellRenderer defaultRenderer,
		TableModel model
	) {
		this(defaultRenderer);
		model.addTableModelListener(this);
	}

	/**
	 * Creates a new {@code ColumnSummaryTabeCellRenderer} that uses
	 * {@code defaultRenderer} to render the component and add the tooltip
	 * summary for all columns.
	 * 
	 * @param defaultRenderer the default {@code TableCellRenderer}
	 */
	public ColumnSummaryTabeCellRenderer(TableCellRenderer defaultRenderer) {
		this(defaultRenderer, Collections.emptyList());
	}

	/**
	 * Creates a new {@code ColumnSummaryTabeCellRenderer} that uses
	 * {@code defaultRenderer} to render the component and add the tooltip
	 * summary for columns in {@code summaryColumns}. Also adds the created
	 * instance as {@code TableModelListener} of {@code model}.
	 * 
	 * @param defaultRenderer the default {@code TableCellRenderer}
	 * @param summaryColumns the columns for which summary should be generated.
	 * @param model a {@code TableModel}.
	 */
	public ColumnSummaryTabeCellRenderer(TableCellRenderer defaultRenderer,
		List<Integer> summaryColumns, TableModel model
	) {
		this(defaultRenderer, summaryColumns);
		model.addTableModelListener(this);
	}

	/**
	 * Creates a new {@code ColumnSummaryTabeCellRenderer} that uses
	 * {@code defaultRenderer} to render the component and add the tooltip
	 * summary for columns in {@code summaryColumns}.
	 * 
	 * @param defaultRenderer the default {@code TableCellRenderer}
	 * @param summaryColumns the columns for which summary should be generated.
	 */
	public ColumnSummaryTabeCellRenderer(TableCellRenderer defaultRenderer,
		List<Integer> summaryColumns
	) {
		this.defaultRenderer = defaultRenderer;
		this.summaryColumns = summaryColumns;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column
	) {
		Component component = defaultRenderer.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);

		int columnModel = table.convertColumnIndexToModel(column);

		if (component instanceof JComponent) {
			((JComponent) component)
				.setToolTipText(getTooltip(table, columnModel));
		}

		return component;
	}

	protected String getTooltip(JTable table, int columnModel) {
		String toolTip = "";
		if (summaryColumns.isEmpty() || summaryColumns.contains(columnModel)) {
			if (!columnSummary.containsKey(columnModel)) {
				columnSummary.put(
					columnModel,
					getColumnSummary(table.getModel(), columnModel)
				);
			}
			toolTip = columnSummary.get(columnModel);
		}
		return toolTip;
	}

	protected String getColumnSummary(TableModel model, int columnModel) {
		Class<?> columnClass = model.getColumnClass(columnModel);

		if (columnClass.isAssignableFrom(String.class)) {
			return getStringColumnSummary(model, columnModel);
		} else if (columnClass.isAssignableFrom(Double.class)) {
			return getDoubleColumnSummary(model, columnModel);
		} else if (columnClass.isAssignableFrom(Float.class)) {
			return getFloatColumnSummary(model, columnModel);
		} else if (columnClass.isAssignableFrom(Integer.class)) {
			return getIntegerColumnSummary(model, columnModel);
		} else if (columnClass.isAssignableFrom(Long.class)) {
			return getLongColumnSummary(model, columnModel);
		} else if (columnClass.isAssignableFrom(File.class)) {
			return getFileColumnSummary(model, columnModel);
		}

		return "";
	}

	protected String getStringColumnSummary(TableModel model, int columnModel) {
		Set<String> values = new HashSet<>();
		for (int row = 0; row < model.getRowCount(); row++) {
			values.add((String) model.getValueAt(row, columnModel));
		}

		return getStringValuesSummary(values, "String");
	}
	
	protected String getStringValuesSummary(Set<String> values,
		String stringType
	) {
		StringBuilder summary = new StringBuilder();
		summary
			.append("<html>")
			.append("Type: ")
			.append(stringType)
			.append("<br/")
			.append("Values:")
			.append(values.stream().collect(JOIN_LIST))
			.append("</html>");

		return summary.toString();
	}
	
	protected String getFileColumnSummary(TableModel model, int columnModel) {
		Set<String> values = new HashSet<>();
		for (int row = 0; row < model.getRowCount(); row++) {
			values.add(fileToString((File) model.getValueAt(row, columnModel)));
		}

		return getStringValuesSummary(values, "File");
	}

	protected String fileToString(File file) {
		return file.getName();
	}

	private String getDoubleColumnSummary(TableModel model, int columnModel) {
		List<Double> values = new LinkedList<>();
		for(int row = 0; row < model.getRowCount(); row++) {
			values.add((Double) model.getValueAt(row, columnModel));
		}

		return getDoubleListSummary(values, "double");
	}
	
	protected String getFloatColumnSummary(TableModel model, int columnModel) {
		List<Double> values = new LinkedList<>();
		for (int row = 0; row < model.getRowCount(); row++) {
			values.add(
				((Float) model.getValueAt(row, columnModel)).doubleValue());
		}

		return getDoubleListSummary(values, "float");
	}

	protected String getDoubleListSummary(List<Double> values,
		String numericType
	) {
		DoubleSummaryStatistics statistics = values.stream()
			.collect(Collectors.summarizingDouble(Double::doubleValue));
	
		return getSummary(numericType, new SummaryStatistics(statistics));
	}

	protected String getIntegerColumnSummary(TableModel model, int columnModel) {
		List<Integer> values = new LinkedList<>();
		for (int row = 0; row < model.getRowCount(); row++) {
			values.add((Integer) model.getValueAt(row, columnModel));
		}
		IntSummaryStatistics statistics = values.stream()
			.collect(Collectors.summarizingInt(Integer::intValue));

		return getSummary("integer", new SummaryStatistics(statistics));
	}

	protected String getLongColumnSummary(TableModel model, int columnModel) {
		List<Long> values = new LinkedList<>();
		for (int row = 0; row < model.getRowCount(); row++) {
			values.add((Long) model.getValueAt(row, columnModel));
		}
		LongSummaryStatistics statistics = values.stream()
			.collect(Collectors.summarizingLong(Long::longValue));

		return getSummary("integer", new SummaryStatistics(statistics));
	}

	private String getSummary(String numericType,
		SummaryStatistics statistics
	) {
		StringBuilder summary = new StringBuilder();
		summary
			.append("<html>")
			.append("Type: Numeric (")
			.append(numericType)
			.append(")<br/")
			.append("Summary:")
			.append(statistics.toHtmlList())
			.append("</html>");

		return summary.toString();
	}
	
	private class SummaryStatistics {
		private double average;
		private double max;
		private double min;
		private long count;
		private double sum;

		public SummaryStatistics(DoubleSummaryStatistics statistics) {
			this(statistics.getAverage(), statistics.getMax(),
				statistics.getMin(), statistics.getCount(),
				statistics.getSum());
		}

		public SummaryStatistics(IntSummaryStatistics statistics) {
			this(statistics.getAverage(), statistics.getMax(),
				statistics.getMin(), statistics.getCount(),
				statistics.getSum());
		}
		
		public SummaryStatistics(LongSummaryStatistics statistics) {
			this(statistics.getAverage(), statistics.getMax(),
				statistics.getMin(), statistics.getCount(),
				statistics.getSum());
		}

		public SummaryStatistics(double average, double max, double min,
			long count, double sum) {
			this.average = average;
			this.max = max;
			this.min = min;
			this.count = count;
			this.sum = sum;
		}

		public String toHtmlList() {
			StringJoiner joiner = 
				new StringJoiner("</li><li>", "<ul><li>", "</li></ul>");
			joiner
				.add("Average:" + getAverage())
				.add("Maximum:" + getMax())
				.add("Minimum:" + getMin())
				.add("Count:" + getCount())
				.add("Sum:" + getSum());

			return joiner.toString();
		}

		public double getAverage() {
			return average;
		}

		public double getMax() {
			return max;
		}

		public double getMin() {
			return min;
		}

		public long getCount() {
			return count;
		}

		public double getSum() {
			return sum;
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		this.columnSummary.clear();
	}
}