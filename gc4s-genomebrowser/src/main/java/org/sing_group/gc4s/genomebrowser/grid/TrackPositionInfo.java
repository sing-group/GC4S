/*
 * #%L
 * GC4S genome browser
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
package org.sing_group.gc4s.genomebrowser.grid;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 *  A {@code GenericInfo} implementation for providing information about track
 *  positions. 
 *  
 * @author hlfernandez
 *
 */
public class TrackPositionInfo implements GenericInfo{
	private String info;
	private long base;
	private Color color;
	private int histogramCount = -1;

	/**
	 * Creates a new {@code TrackPositionInfo} instance at the specified base.
	 * 
	 * @param info the tooltip information
	 * @param base the position
	 */
	public TrackPositionInfo(String info, long base) {
		this.info = info;
		this.base = base;
		this.color = Color.LIGHT_GRAY;
	}

	/**
	 * Creates a new {@code TrackPositionInfo} instance at the specified base.
	 * 
	 * @param info the tooltip information
	 * @param base the position
	 * @param color the tooltip color
	 */
	public TrackPositionInfo(String info, long base, Color color) {
		this.info = info;
		this.base = base;
		this.color = color;
	}

	/**
	 * Creates a new {@code TrackPositionInfo}.
	 * 
	 * @param count the tooltip information
	 * @param color the tooltip color
	 */
	public TrackPositionInfo(int count, Color color) {
		this.histogramCount = count;
		this.color = color;
	}

	@Override
	public String toString() {
		if (histogramCount == -1) {
			return (String.valueOf(this.base) + ": " + info);
		} else {
			return ("Total: " + histogramCount);
		}
	}

	@Override
	public Color getToolTipColor() {
		return this.color;
	}

	@Override
	public List<String> getLines() {
		return Arrays.asList(this.toString());
	}
}
