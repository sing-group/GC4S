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
