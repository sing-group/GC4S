/*
 * #%L
 * GC4S multiple sequence alignment viewer
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
package org.sing_group.gc4s.msaviewer;

import java.awt.Color;

/**
 * This class encloses the information used by the
 * {@code SequenceAlignmentRenderer} to render a single base of a sequence
 * alignment or track alignment.
 *
 * @author hlfernandez
 * @author mrjato
 *
 */
public class SequenceBaseRenderingInfo {
	private Color background;
	private Color foreground;
	private boolean bold;
	private boolean italic;

	/**
	 * Creates a new {@code SequenceBaseRenderingInfo} with the specified
	 * initial values.
	 *
	 * @param background the background color
	 * @param foreground the foreground color
	 * @param bold whether the base is bold or not
	 * @param italic whether the base is italic or not
	 */
	public SequenceBaseRenderingInfo(Color background, Color foreground,
		boolean bold, boolean italic
	) {
		this.background = background;
		this.foreground = foreground;
		this.bold = bold;
		this.italic = italic;
	}

	public Color getBackground() {
		return background;
	}

	public Color getForeground() {
		return foreground;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isItalic() {
		return italic;
	}
}
