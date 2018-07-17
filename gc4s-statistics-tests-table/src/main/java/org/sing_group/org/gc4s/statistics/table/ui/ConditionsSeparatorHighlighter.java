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
package org.sing_group.org.gc4s.statistics.table.ui;

import static java.awt.Color.BLACK;
import static javax.swing.BorderFactory.createMatteBorder;

import java.awt.Component;

import javax.swing.JComponent;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.sing_group.org.gc4s.statistics.table.StatisticsTestTable;
import org.sing_group.org.gc4s.statistics.table.StatisticsTestTableModel;

/**
 * A {@code ColorHighlighter} that draws a left border at the first sample of
 * each condition to enhance the distinction between conditions.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in the dataset
 */
public class ConditionsSeparatorHighlighter<T> extends ColorHighlighter {
	public static final int DEFAULT_BORDER_PIXELS = 1;

	private StatisticsTestTableModel<T> tableModel;
	private int borderSize;

	/**
	 * Creates a new {@code ClassesSeparatorHighlighter} for the specified
	 * table using a border size of 1px.
	 *
	 * @param table a {@code StatisticsTestTable}
	 */
	public ConditionsSeparatorHighlighter(StatisticsTestTable<T> table) {
		this(table, DEFAULT_BORDER_PIXELS);
	}

	/**
	 * Creates a new {@code ClassesSeparatorHighlighter} for the specified
	 * table using the specified border size.
	 *
	 * @param table a {@code StatisticsTestTable}
	 * @param borderSize the size in px of the border to separate conditions
	 */
	public ConditionsSeparatorHighlighter(StatisticsTestTable<T> table,
		int borderSize
	) {
		this.tableModel = table.getStatisticsTestTableModel();
		this.borderSize = borderSize;
	}

	@Override
	protected void applyBackground(Component renderer,
		ComponentAdapter adapter
	) {
		super.applyBackground(renderer, adapter);

		int columnIndex = adapter.convertColumnIndexToModel(adapter.column);
		if (this.tableModel.isFirstSampleColumn(columnIndex) &&
			renderer instanceof JComponent
		) {
			((JComponent) renderer)
				.setBorder(createMatteBorder(0, borderSize, 0, 0, BLACK));
		}
	}
}
