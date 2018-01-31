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
package org.sing_group.gc4s.genomebrowser;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import org.sing_group.gc4s.genomebrowser.painter.Painter;

/**
 * This class encapsulates the state of a {@code GenomeBrowser}.
 * 
 * @author hlfernandez
 *
 */
public class GenomeBrowserState {
	private long initialPosition = -1;
	private long finalPosition = -1;
	private String chr = "null";
	private HashMap<Painter, Collection<TrackOption>> tracks;
	private LinkedList<File> files;
	private LinkedList<Painter> painters;
	private int numberOfIntervals = -1;

	/**
	 * Creates a new {@code GenomeBrowserState} instance with empty values.
	 */
	public GenomeBrowserState() {
	}

	public long getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(long initialPosition) {
		this.initialPosition = initialPosition;
	}

	public long getFinalPosition() {
		return finalPosition;
	}

	public void setFinalPosition(long finalPosition) {
		this.finalPosition = finalPosition;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
	}

	public int getNumberOfIntervals() {
		return numberOfIntervals;
	}

	public void setNumberOfIntervals(int numberOfIntervals) {
		this.numberOfIntervals = numberOfIntervals;
	}

	public LinkedList<File> getFiles() {
		return this.files;
	}

	public LinkedList<Painter> getPainters() {
		return this.painters;
	}

	public Collection<TrackOption> getTrackOptions(Painter p) {
		try {
			return this.tracks.get(p);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Updates the variables in this object to store the current status of the
	 * specified {@code GenomeBrowser}.
	 * 
	 * @param genomeBrowser a {@code GenomeBrowser} object
	 */
	public void saveStatus(GenomeBrowser genomeBrowser) {
		this.tracks = new HashMap<Painter, Collection<TrackOption>>();
		this.files = new LinkedList<File>();
		this.painters = new LinkedList<Painter>();
		for (Painter p : genomeBrowser.getPainters()) {
			painters.addLast(p);
			files.addLast(genomeBrowser.getPainter(p));
			this.tracks.put(p, p.getOptions());
		}
		this.chr = genomeBrowser.getCurrentSequence();
		this.initialPosition = genomeBrowser.getInitialPosition();
		this.finalPosition = genomeBrowser.getFinalPosition();
	}
}
