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
package org.sing_group.gc4s.genomebrowser.painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Collection;

import org.sing_group.gc4s.genomebrowser.GenomeBrowser;
import org.sing_group.gc4s.genomebrowser.TrackOption;

/**
 * The interface that defines a track painter.
 * 
 * @author hlfernandez
 *
 */
public interface Painter {
	/**
	 * Paints the track at {@code Graphics2D} with the specified track
	 * {@code offset}.
	 * 
	 * @param g2 a {@code Graphics2D} object
	 * @param genomeBrowser a {@code GenomeBrowser} object
	 * @param offset the track offset
	 */
	void paint(Graphics2D g2, GenomeBrowser genomeBrowser, int offset);

	/**
	 * Initializes the track painter.
	 * 
	 * @param genomeBrowser a {@code GenomeBrowser} object
	 * @throws IOException if an error occurrs reading the track file
	 */
	void init(GenomeBrowser genomeBrowser) throws IOException;

	/**
	 * Returns the collection of configurable {@code TrackOption}s.
	 * 
	 * @return the collection of configurable {@code TrackOption}s
	 */
	Collection<TrackOption> getOptions();

	/**
	 * Returns the track height.
	 * 
	 * @param genomeBrowser a {@code GenomeBrowser} object
	 * @return the track height
	 */
	int computeHeight(GenomeBrowser genomeBrowser);

	/**
	 * Resets the track painter.
	 */
	void reset();

	/**
	 * Sets the track color.
	 * 
	 * @param trackColor the track color
	 */
	void setTrackColor(Color trackColor);

	/**
	 * Sets the track painter options.
	 * 
	 * @param options the track painter options
	 */
	void setOptions(Collection<TrackOption> options);

	/**
	 * Returns the background color.
	 * 
	 * @return the background color
	 */
	Color getBackgroundColor();

	/**
	 * Returns the track name.
	 * 
	 * @return the track name
	 */
	String getTrackName();

	/**
	 * Returns the track color.
	 * 
	 * @return the track color
	 */
	Color getTrackColor();
}
