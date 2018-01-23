package org.sing_group.gc4s.genomebrowser.grid;

import java.awt.Color;
import java.util.Vector;

/**
 * A {@code GenericInfo} implementation for providing file information.
 * 
 * @author hlfernandez
 *
 */
public class FileInfo implements GenericInfo {
	public static final Color TOOLTIP_COLOR = Color.decode("#D8E1F2");

	private String filename;

	/**
	 * Creates a new {@code FileInfo} instance.
	 * 
	 * @param filename the name of the file
	 */
	public FileInfo(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return (this.filename);
	}

	@Override
	public Color getToolTipColor() {
		return TOOLTIP_COLOR;
	}

	@Override
	public Vector<String> getLines() {
		return null;
	}
}
