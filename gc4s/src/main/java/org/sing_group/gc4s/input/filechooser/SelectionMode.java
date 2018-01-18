package org.sing_group.gc4s.input.filechooser;

import javax.swing.JFileChooser;

/**
 * The selection mode of a {@code JFileChooser}.
 * 
 * @author hlfernandez
 *
 */
public enum SelectionMode {
	FILES(JFileChooser.FILES_ONLY), 
	DIRECTORIES(JFileChooser.DIRECTORIES_ONLY), 
	FILES_DIRECTORIES(JFileChooser.FILES_AND_DIRECTORIES);

	private int fileSelectionMode;

	SelectionMode(int fileSelectionMode) {
		this.fileSelectionMode = fileSelectionMode;
	}

	public int getFileSelectionMode() {
		return fileSelectionMode;
	}
}