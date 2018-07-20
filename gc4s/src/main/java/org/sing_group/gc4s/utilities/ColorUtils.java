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
package org.sing_group.gc4s.utilities;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides functionalitites to deal with colors.
 * 
 * @author hlfernandez
 *
 */
public class ColorUtils {
	public static final Color COLOR_INVALID_INPUT = new Color(255, 148, 148);

	public final static Color COLOR_LIGHT_RED = new Color(233, 198, 175);
	public final static Color COLOR_LIGHT_GREEN = new Color(175, 233, 198);
	public final static Color COLOR_LIGHT_BLUE = new Color(175, 198, 233);
	public final static Color COLOR_LIGHT_ORANGE = new Color(255, 179, 128);
	public final static Color COLOR_ORANGE = new Color(255, 153, 85);
	public final static Color COLOR_RED = new Color(255, 100, 100);
	public final static Color COLOR_GREEN = new Color(100, 255, 100);
	public final static Color COLOR_BLUE = new Color(100, 100, 255);
	public final static Color COLOR_CYAN = new Color(100, 255, 255);
	public final static Color COLOR_MAGENTA = new Color(255, 100, 255);
	
	public final static Color[] COLORS = new Color[] {
		COLOR_LIGHT_GREEN,
		COLOR_LIGHT_ORANGE,
		COLOR_LIGHT_BLUE, 
		COLOR_LIGHT_RED,
		COLOR_GREEN,
		COLOR_ORANGE,
		COLOR_BLUE,
		COLOR_RED,
		COLOR_CYAN,
		COLOR_MAGENTA
	};
	
	/**
	 * Returns the complementary color of the specified {@code color}.
	 * @param color the input color
	 * @return the complementary color
	 */
	public static Color getComplementaryColor(Color color) {
		return new Color(
			255 - color.getRed(), 
			255 - color.getGreen(),
			255 - color.getBlue()
		);
	}

	/**
	 * Maps each string in the input set of values into a color, using the color
	 * constants defined in this class.
	 * 
	 * @param values the set of string values
	 * @return a map of string to color
	 */
	public static Map<String, Color> getColorMap(Set<String> values) {
		Map<String, Color> colorMap = new HashMap<>();
		List<String> valuesList = new LinkedList<>(values);
		for (int i = 0; i < valuesList.size(); i++) {
			colorMap.put(valuesList.get(i), COLORS[i % COLORS.length]);
		}
		return colorMap;
	}
}
