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

import java.util.Observable;

/**
 * This class stores the configuration of the
 * {@code MultipleSequenceAlignmentViewerPanel}.
 *
 * @author hlfernandez
 * @author mrjato
 *
 * @see MultipleSequenceAlignmentViewerPanel
 *
 */
public class MultipleSequenceAlignmentViewerConfiguration extends Observable {
	public static final int DEFAULT_BLOCKS_PER_LINE = 9;
	public static final int DEFAULT_BLOCK_LENGTH = 10;
	public static final int DEFAULT_LABEL_TAB = 3;
	public static final int DEFAULT_LABEL_LENGTH = 10;
	public static final int DEFAULT_FONT_SIZE = 14;
	public static final boolean DEFAULT_SHOW_INDEXES = false;
	public static final boolean DEFAULT_SHOW_UPPER_TRACKS = false;
	public static final boolean DEFAULT_SHOW_BOTTOM_TRACKS = false;

	private int labelLength = DEFAULT_LABEL_LENGTH;
	private int labelTab = DEFAULT_LABEL_TAB;
	private int blockLength = DEFAULT_BLOCK_LENGTH;
	private int blocksPerLine = DEFAULT_BLOCKS_PER_LINE;
	private int fontSize = DEFAULT_FONT_SIZE;
	private boolean showIndexes = DEFAULT_SHOW_INDEXES;
	private boolean showUpperTracks = DEFAULT_SHOW_UPPER_TRACKS;
	private boolean showBottomTracks = DEFAULT_SHOW_BOTTOM_TRACKS;

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerConfiguration}
	 * initialized with the default values.
	 */
	public MultipleSequenceAlignmentViewerConfiguration() { }

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerConfiguration}
	 * initialized with the specified values.
	 *
	 * @param labelLength the length of the sequence label
	 * @param labelTab the number of tabs after the sequence label
	 * @param blockLength the length of each block
	 * @param blocksPerLine the number of blocks per line
	 * @param fontSize the size of the font
	 * @param showIndexes whether position indexes must be shown or not
	 * @param showUpperTracks whether upper tracks must be shown or not
	 * @param showBottomTracks whether bottom tracks must be shown or not
	 */
	public MultipleSequenceAlignmentViewerConfiguration(int labelLength,
		int labelTab, int blockLength, int blocksPerLine, int fontSize,
		boolean showIndexes, boolean showUpperTracks,
		boolean showBottomTracks
	) {
		this.labelLength = labelLength;
		this.labelTab = labelTab;
		this.blockLength = blockLength;
		this.blocksPerLine = blocksPerLine;
		this.fontSize = fontSize;
		this.showIndexes = showIndexes;
		this.showUpperTracks = showUpperTracks;
		this.showBottomTracks = showBottomTracks;
	}

	/**
	 * Sets the default values.
	 */
	public void reset() {
		this.labelLength = DEFAULT_LABEL_LENGTH;
		this.labelTab = DEFAULT_LABEL_TAB;
		this.blockLength = DEFAULT_BLOCK_LENGTH;
		this.blocksPerLine = DEFAULT_BLOCKS_PER_LINE;
		this.fontSize = DEFAULT_FONT_SIZE;
		this.showIndexes = DEFAULT_SHOW_INDEXES;
		this.showUpperTracks = DEFAULT_SHOW_UPPER_TRACKS;
		this.showBottomTracks = DEFAULT_SHOW_BOTTOM_TRACKS;

		this.setChanged();
		this.notifyObservers();
	}

	public int getLabelLength() {
		return labelLength;
	}

	public void setLabelLength(int labelLength) {
		if (labelLength != this.labelLength) {
			this.labelLength = (labelLength <= 0) ? DEFAULT_LABEL_LENGTH : labelLength;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getLabelTab() {
		return labelTab;
	}

	public void setLabelTab(int labelTab) {
		if (this.labelTab != labelTab) {
			this.labelTab = (labelTab <= 0) ? DEFAULT_LABEL_TAB : labelTab;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getBlockLength() {
		return blockLength;
	}

	public void setBlockLength(int blockLength) {
		if (this.blockLength != blockLength) {
			this.blockLength = (blockLength <= 0) ? DEFAULT_BLOCK_LENGTH : blockLength;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getBlocksPerLine() {
		return blocksPerLine;
	}

	public void setBlocksPerLine(int blocksPerLine) {
		if (this.blocksPerLine != blocksPerLine) {
			this.blocksPerLine = (blocksPerLine <= 0) ? DEFAULT_BLOCKS_PER_LINE : blocksPerLine;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		if (this.fontSize != fontSize) {
			this.fontSize = (fontSize <= 0) ? DEFAULT_FONT_SIZE : fontSize;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public boolean isShowIndexes() {
		return showIndexes;
	}

	public void setShowIndexes(boolean showIndexes) {
		if (this.showIndexes != showIndexes) {
			this.showIndexes = showIndexes;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public boolean isShowUpperTracks() {
		return showUpperTracks;
	}

	public void setShowUpperTracks(boolean showUpperTracks) {
		if (this.showUpperTracks != showUpperTracks) {
			this.showUpperTracks = showUpperTracks;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public boolean isShowBottomTracks() {
		return showBottomTracks;
	}

	public void setShowBottomTracks(boolean showBottomTracks) {
		if (this.showBottomTracks != showBottomTracks) {
			this.showBottomTracks = showBottomTracks;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public String getRules() {
		final StringBuilder sb = new StringBuilder();

		sb.append(".indexes { color: #707070; }\n");
		sb.append(".scores { color: #707070; }\n");

		sb.append("pre {\n")
			.append("\tfont-family: monospace;\n")
			.append("\tfont-size: ").append(this.getFontSize()).append("pt;\n")
			.append("}\n\n");

		return sb.toString();
	}
}
