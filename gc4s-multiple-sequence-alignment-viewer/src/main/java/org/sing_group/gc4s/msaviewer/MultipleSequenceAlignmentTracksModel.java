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

import java.util.List;

/**
 * An interface for the definition of multiple sequence alignment tracks. Such
 * {@code Track}s contain information that is placed above or below sequences
 * in the {@code MultipleSequenceAlignmentViewerPanel}.
 *
 * @author hlfernandez
 * @author mrjato
 *
 * @see Track
 *
 */
public interface MultipleSequenceAlignmentTracksModel {

	/**
	 * Returns the name associated to this model.
	 *
	 * @return the name associated to this model
	 */
	public String getName();

	/**
	 * Returns the list of tracks that must be placed above the sequences.
	 *
	 * @return the list of tracks that must be placed above the sequences
	 */
	public List<Track> getUpperTracks();

	/**
	 * Returns the list of tracks that must be placed below the sequences.
	 *
	 * @return the list of tracks that must be placed below the sequences
	 */
	public List<Track> getBottomTracks();
}
