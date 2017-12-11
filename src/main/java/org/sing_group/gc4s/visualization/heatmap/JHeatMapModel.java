package org.sing_group.gc4s.visualization.heatmap;

/**
 * This class encapsulates the data needed by {@code JHeatMap}.
 * 
 * @author hlfernandez
 * @see JHeatMap
 *
 */
public class JHeatMapModel {
	
	private double[][] data;
	private String[] rowNames;
	private String[] columnNames;

	/**
	 * Constructs a new {@code JHeatMapModel}.
	 * 
	 * @param data
	 *            the input data matrix.
	 * @param rowNames
	 *            the names for the rows.
	 * @param columnNames
	 *            the names for the columns
	 */
	public JHeatMapModel(double[][] data, String[] rowNames, String[] columnNames) {
		this.data = data;
		this.rowNames = rowNames;
		this.columnNames = columnNames;
	}
	
	/**
	 * Returns the input data matrix.
	 * 
	 * @return the input data matrix.
	 */
	public double[][] getData() {
		return data;
	}
	
	/**
	 * Returns an array with the row names.
	 * 
	 * @return an array with the row names.
	 */
	public String[] getRowNames() {
		return rowNames;
	}

	/**
	 * Returns an array with the column names.
	 * 
	 * @return an array with the columnnames.
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
}
