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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code GenericInfo} implementation for providing genome browser
 * information.
 * 
 * @author hlfernandez
 *
 */
public class GenomeBrowserInfo implements GenericInfo {
	public static final Color TOOLTIP_COLOR = Color.decode("#F3BC6B");
	
	private String lengh;
	private String from;
	private String to;
	private String chromosome;
	public NumberFormat formatter = new DecimalFormat("#,###,###"); 

	/**
	 * Creates a new {@code GenomeBrowserInfo} with the specified values.
	 * 
	 * @param length the chromosome length
	 * @param from the initial base
	 * @param to the end base
	 * @param chromosome the chromosome
	 */
	public GenomeBrowserInfo(String length, String from, String to,
		String chromosome
	) {
		this.lengh = length;
		this.from = from;
		this.to = to;
		this.chromosome = chromosome;
	}

	@Override
	public String toString() {
		return ("Viewing " + lengh + "bases at chromosome " + this.chromosome
			+ " from " + from + " to " + to);
	}

	@Override
	public Color getToolTipColor() {
		return TOOLTIP_COLOR;
	}

	@Override
	public List<String> getLines() {
		return Arrays.asList(this.toString());
	}
}
