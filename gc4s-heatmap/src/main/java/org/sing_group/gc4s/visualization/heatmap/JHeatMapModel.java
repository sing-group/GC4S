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
	 * @param data the input data matrix
	 * @param rowNames the names for the rows
	 * @param columnNames the names for the column
	 */
	public JHeatMapModel(double[][] data, String[] rowNames,
		String[] columnNames
	) {
		this.data = data;
		this.rowNames = rowNames;
		this.columnNames = columnNames;
	}

	/**
	 * Returns the input data matrix.
	 *
	 * @return the input data matrix
	 */
	public double[][] getData() {
		return data;
	}

	/**
	 * Returns an array with the row names.
	 *
	 * @return an array with the row names
	 */
	public String[] getRowNames() {
		return rowNames;
	}

	/**
	 * Returns an array with the column names.
	 *
	 * @return an array with the column names
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
}
