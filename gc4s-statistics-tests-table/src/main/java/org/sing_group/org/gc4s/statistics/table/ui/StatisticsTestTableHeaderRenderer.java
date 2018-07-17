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
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createMatteBorder;
import static org.sing_group.gc4s.utilities.ColorUtils.getColorMap;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.sing_group.org.gc4s.statistics.table.StatisticsTestTableModel;

/**
 * A {@code DefaultTableCellRenderer} table cell renderer used to display sample
 * names in table headers using different colors. Also, a border at the first
 * sample of each condition can be displayed in order to enhance the distinction
 * between conditions.
 *
 * @author hlfernandez
 *
 */
public class StatisticsTestTableHeaderRenderer
	extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_BORDER_PIXELS = 0;

	private int borderSize;
	private TableCellRenderer defaultRenderer;
	private Map<String, Color> classColors;

	/**
	 * Creates a new {@code StatisticsTestTableHeaderRenderer} that uses the
	 * specified default renderer to obtain each table cell. The default border
	 * size is 0, meaning that no border is drawn.
	 *
	 * @param defaultRenderer the default {@code TableCellRenderer}
	 */
	public StatisticsTestTableHeaderRenderer(
		TableCellRenderer defaultRenderer
	) {
		this(defaultRenderer, DEFAULT_BORDER_PIXELS);
	}

	/**
	 * Creates a new {@code StatisticsTestTableHeaderRenderer} that uses the
	 * specified default renderer to obtain each table cell.
	 *
	 * @param defaultRenderer the default {@code TableCellRenderer}
	 * @param borderSize the size in px of the border to separate conditions
	 */
	public StatisticsTestTableHeaderRenderer(TableCellRenderer defaultRenderer,
		int borderSize) {
		this.defaultRenderer = defaultRenderer;
		this.borderSize = borderSize;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column
	) {
		Component toret = defaultRenderer.getTableCellRendererComponent(table,
			value, isSelected, hasFocus, row, column);

		StatisticsTestTableModel<?> model =
			(StatisticsTestTableModel<?>) table.getModel();

		int columnIndex = table.convertColumnIndexToModel(column);
		if (toret instanceof JComponent) {
			if (model.isSampleColumn(columnIndex)) {
				toret.setBackground(getClassColor(
					model.getSampleCondition(columnIndex), model)
				);
			}

			if (model.isFirstSampleColumn(columnIndex) && borderSize > 0) {
				((JComponent) toret)
					.setBorder(createCompoundBorder(
						createMatteBorder(0, borderSize, 0, 0, BLACK),
						((JComponent) toret).getBorder())
					);
			}
		}

		return toret;
	}

	private Color getClassColor(String className,
		StatisticsTestTableModel<?> model
	) {
		if (classColors == null) {
			this.initializeClassColors(model);
		}
		return classColors.get(className);
	}

	private void initializeClassColors(StatisticsTestTableModel<?> model) {
		classColors = getColorMap(model.getDataset().getUniqueConditionNames());
	}
}
