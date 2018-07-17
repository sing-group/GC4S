/*
 * #%L
 * GC4S statistics tests table
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
package org.sing_group.org.gc4s.statistics.table;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.sing_group.org.gc4s.statistics.data.Dataset;

/**
 * The table model for the {@code StatisticsTestTable}.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in the dataset
 */
public class StatisticsTestTableModel<T> extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final int FIXED_ADDITIONAL_COLUMNS = 2;

	private Dataset<T> dataset;
	private Map<String, Double> pValues = new HashMap<>();
	private Map<String, Double> qValues = new HashMap<>();
	private boolean isShowCorrection;

	/**
	 * Creates a new {@code StatisticsTestTableModel} for the specified
	 * {@code dataset}.
	 *
	 * @param dataset the {@code Dataset<T>}
	 * @param isShowCorrection whether the correction column should be shown or
	 *        not
	 */
	public StatisticsTestTableModel(Dataset<T> dataset,
		boolean isShowCorrection
	) {
		this.dataset = dataset;
		this.isShowCorrection = isShowCorrection;
	}

	@Override
	public int getRowCount() {
		return dataset == null ? 0 : dataset.getFeatures().length;
	}

	@Override
	public int getColumnCount() {
		return dataset == null ? 0
			: getAdditionalColumns() + dataset.getSamples().length;
	}

	private int getAdditionalColumns() {
		return FIXED_ADDITIONAL_COLUMNS + (isShowCorrection ? 1 : 0);
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Feature";
		case 1:
			return "p-value";
		default:
			if (columnIndex == 2 && isShowCorrection) {
				return "q-value";
			} else {
				return dataset.getSamples()[getSampleIndex(columnIndex)];
			}
		}
	}

	private int getSampleIndex(int columnIndex) {
		return columnIndex - getAdditionalColumns();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Double.class;
		default:
			if (columnIndex == 2 && isShowCorrection) {
				return Double.class;
			} else {
				if (getRowCount() > 0 && getColumnCount() > 0) {
					return dataset.getValue(
						dataset.getSamples()[0],
						dataset.getFeatures()[0]
					).getClass();
				} else {
					return null;
				}
			}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return dataset.getFeatures()[rowIndex];
		case 1:
			return pValues.getOrDefault(dataset.getFeatures()[rowIndex], null);
		default:
			if(columnIndex == 2 && isShowCorrection) {
				return qValues.getOrDefault(
					dataset.getFeatures()[rowIndex], null);
			} else {
				return dataset.getValue(
					dataset.getSamples()[getSampleIndex(columnIndex)],
					dataset.getFeatures()[rowIndex]
				);
			}
		}
	}

	/**
	 * Returns the {@code Dataset<T>} associated with this model.
	 *
	 * @return the {@code Dataset<T>} associated with this model
	 */
	public Dataset<T> getDataset() {
		return dataset;
	}

	/**
	 * Sets the values for the p-values column. Map keys should be the feature
	 * names.
	 *
	 * @param pValues the values for the p-values column
	 */
	public void setPvalues(Map<String, Double> pValues) {
		this.pValues  = pValues;
	}

	/**
	 * Sets the values for the q-values column. Map keys should be the feature
	 * names.
	 *
	 * @param qValues the values for the q-values column
	 */
	public void setQvalues(Map<String, Double> qValues) {
		this.qValues = qValues;
	}

	/**
	 * Returns {@code true} if the specified {@code columnIndex} corresponds to a
	 * sample and {@code false} otherwise.
	 *
	 * @param columnIndex a column model index
	 * @return {@code true} if the specified {@code columnIndex} corresponds to a
	 *         sample and {@code false} otherwise
	 */
	public boolean isSampleColumn(int columnIndex) {
		return columnIndexToSampleIndex(columnIndex) >= 0;
	}

	private int columnIndexToSampleIndex(int columnIndex) {
		return columnIndex - getAdditionalColumns();
	}

	/**
	 * Returns the name of the condition associated to the sample located at the
	 * specified column index. Note that if the column index does not correspond
	 * to a sample, an {@code IllegalArgumentException} will be thrown.
	 *
	 * @param columnIndex the column model index
	 * @return the name of the condition associated to the sample located at the
	 *         specified column
	 */
	public String getSampleCondition(int columnIndex) {
		if(isSampleColumn(columnIndex)) {
			int sampleIndex = columnIndexToSampleIndex(columnIndex);
			return this.dataset.getConditionNames()[sampleIndex];
		} else {
			throw new IllegalArgumentException(columnIndex + " does not refer to a sample");
		}
	}

	/**
	 * Returns {@code true} if the specified {@code columnIndex} corresponds to
	 * the first sample of a condition and {@code false} otherwise.
	 *
	 * @param columnIndex a column model index
	 * @return {@code true} if the specified {@code columnIndex} corresponds to
	 *         the first sample of a condition and {@code false} otherwise
	 */
	public boolean isFirstSampleColumn(int columnIndex) {
		if (!isSampleColumn(columnIndex)) {
			return false;
		}

		int sampleIndex = columnIndexToSampleIndex(columnIndex);

		if (sampleIndex == 0) {
			return true;
		}

		String previousCondition = dataset.getConditionNames()[0];
		for (int i = 1; i < dataset.getConditionNames().length; i++) {
			String nextCondition = dataset.getConditionNames()[i];
			if (!nextCondition.equals(previousCondition)) {
				previousCondition = nextCondition;
				if (sampleIndex == i) {
					return true;
				}
			}
		}

		return false;
	};
}
