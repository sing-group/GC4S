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

import static org.sing_group.gc4s.utilities.ColorUtils.getComplementaryColor;
import static org.sing_group.gc4s.utilities.Gradient.createGradient;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.sing_group.org.gc4s.statistics.data.Dataset;
import org.sing_group.org.gc4s.statistics.data.FeatureValues;
import org.sing_group.org.gc4s.statistics.table.StatisticsTestTable;
import org.sing_group.org.gc4s.statistics.table.StatisticsTestTableModel;

/**
 * A {@code ColorHighlighter} to highlight {@code Number} values in tables by
 * creating a gradient of colors (like in heat map representations).
 *
 * @author hlfernandez
 *
 */
public class NumberHighlighter extends ColorHighlighter {
	public static final Color COLOR_LOW = new Color(255, 100, 100);
	public static final Color COLOR_HIGH = new Color(100, 255, 100);
	public static final Color DEFAULT_NAN_COLOR = Color.LIGHT_GRAY;
	public static final int DEFAULT_STEPS = 100;

	private Number max;
	private Number min;
	private Color lowColor;
	private Color highColor;
	private Color nanColor = DEFAULT_NAN_COLOR;
	private Function<Number, Color> numberToColor;
	private StatisticsTestTableModel<Number> tableModel;

	/**
	 * Creates a new {@code NumberHighlighter} for the specified table using the
	 * default colors for the lowest and highest values.
	 *
	 * @param table a {@code StatisticsTestTable<Number>}
	 */
	public NumberHighlighter(StatisticsTestTable<Number> table) {
		this(table, COLOR_LOW, COLOR_HIGH);
	}

	/**
	 * Creates a new {@code NumberHighlighter} for the specified table using the
	 * specified colors.
	 *
	 * @param table a {@code StatisticsTestTable<Number>}
	 * @param lowColor the color for the lowest value in the dataset
	 * @param highColor the color for the highest value in the dataset
	 */
	public NumberHighlighter(StatisticsTestTable<Number> table, Color lowColor,
		Color highColor
	) {
		this.tableModel = table.getStatisticsTestTableModel();
		this.lowColor = lowColor;
		this.highColor = highColor;

		this.computeColors();
	}

	private void computeColors() {
		Dataset<Number> dataset = this.tableModel.getDataset();
		max = Integer.MIN_VALUE;
		min = Integer.MAX_VALUE;

		for (String f : dataset.getFeatures()) {
			for (String className : dataset.getUniqueConditionNames()) {
				FeatureValues<Number> fV = dataset.getFeatureValues(f);
				for (Number n : fV.getConditionValues(className)) {
					int comparison = compare(n, min);
					if (comparison < 0) {
						min = n;
					} else {
						comparison = compare(n, max);
						if (comparison > 0) {
							max = n;
						}
					}
				}
			}
		}

		Color[] colorGradient = getColorGradient();

		numberToColor = new Function<Number, Color>() {

			@Override
			public Color apply(Number t) {
				if (t== null) {
					return nanColor;
				} else {
					Number normalized = normalize(checkRange(t), max, min);

					int colorIndex = (int)
						((normalized.doubleValue() * (DEFAULT_STEPS - 1)));

					return colorGradient[colorIndex];
				}
			}

			private Number checkRange(Number t) {
				if (compare(t, max) > 0) {
					return max;
				} else if (compare(t, min) < 0) {
					return min;
				} else {
					return t;
				}
			}

			private Number normalize(Number d, Number max, Number min) {
				BigDecimal dividend = bigDecimal(d).subtract(bigDecimal(min));
				BigDecimal divisor = bigDecimal(max).subtract(bigDecimal(min));

				return dividend.divide(divisor, MathContext.DECIMAL128);
			}

		};
	}

	private static final BigDecimal bigDecimal(Number n) {
		return new BigDecimal(n.toString());
	}

	private static int compare(Number n1, Number n2) {
		return bigDecimal(n1).compareTo(bigDecimal(n2));
	}

	private Color[] getColorGradient() {
		return createGradient(lowColor, highColor, DEFAULT_STEPS);
	}

	@Override
	protected void applyBackground(Component renderer,
		ComponentAdapter adapter
	) {
		int columnModel = adapter.convertColumnIndexToModel(adapter.column);

		if (!tableModel.isSampleColumn(columnModel)) {
			super.applyBackground(renderer, adapter);
		} else {
			if (adapter.getValue() == null) {
				super.applyBackground(renderer, adapter);
			} else {
				if (Number.class.isAssignableFrom(adapter.getColumnClass())) {
					final Number value = ((Number) adapter.getValue());
					Color color = numberToColor.apply(value);
					if (adapter.isSelected()) {
						renderer.setBackground(getComplementaryColor(color));
					} else {
						renderer.setBackground(color);
					}
				} else {
					super.applyBackground(renderer, adapter);
				}
			}
		}
	}
}
