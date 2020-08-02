/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.input.filechooser;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * A class that encapsulates the configuration of a {@code JFileChooser},
 * providing the method
 * {@link JFileChooserConfiguration#configure(JFileChooser)} to configure a
 * given {@code JFileChooser}.
 *
 * @author hlfernandez
 *
 */
public class JFileChooserConfiguration {

	private final List<FileFilter> filters;
	private int selectionMode;
	private boolean allowAll;
	private boolean clearSelectedFileOnShow;

	/**
	 * Creates a new {@code JFileChooserConfiguration} with the specified
	 * {@code JFileChooser} selection mode.
	 * 
	 * @param selectionMode the {@code JFileChooser} selection mode
	 *  ({@code JFileChooser#FILES_AND_DIRECTORIES},
	 *  {@code JFileChooser#FILES_ONLY} or 
	 *  {@code JFileChooser#DIRECTORIES_ONLY})
	 */
	public JFileChooserConfiguration(int selectionMode) {
		this(selectionMode, Collections.emptyList(), true, false);
	}

	/**
	 * Creates a new {@code JFileChooserConfiguration} with the specified
	 * {@code JFileChooser} selection mode. The list of {@code FileFilter} is
	 * used to configure the {@code JFileChooser} filters.
	 * 
	 * @param selectionMode the {@code JFileChooser} selection mode
	 *  ({@code JFileChooser#FILES_AND_DIRECTORIES},
	 *  {@code JFileChooser#FILES_ONLY} or
	 *	{@code JFileChooser#DIRECTORIES_ONLY})
	 * @param filters a list of {@code FileFilter}.
	 */
	public JFileChooserConfiguration(
		int selectionMode, List<FileFilter> filters
	) {
		this(selectionMode, filters, true, false);
	}

	/**
	 * Creates a new {@code JFileChooserConfiguration} with the specified
	 * {@code JFileChooser} selection mode. The list of {@code FileFilter} is
	 * used to configure the {@code JFileChooser} filters and {@code allowAll}
	 * indicates whether the accept all files filter is used or not. Also, the 
	 * {@code clearSelectedFileOnShow} indicates whether to clear the selected
	 * file on show or not.
	 * 
	 * @param selectionMode the {@code JFileChooser} selection mode
	 *  ({@code JFileChooser#FILES_AND_DIRECTORIES},
	 *  {@code JFileChooser#FILES_ONLY} or
	 *	{@code JFileChooser#DIRECTORIES_ONLY})
	 * @param filters a list of {@code FileFilter}.
	 * @param allowAll whether the accept all files filter is used or not.
	 * @param clearSelectedFileOnShow whether to clear the selected file on show or not.
	 */
	public JFileChooserConfiguration(
		int selectionMode, List<FileFilter> filters, boolean allowAll, boolean clearSelectedFileOnShow
	) {
		this.selectionMode = selectionMode;
		this.filters = filters;
		this.allowAll = allowAll;
		this.clearSelectedFileOnShow = clearSelectedFileOnShow;
	}

	/**
	 * Applies the configuration to {@code fileChooser}.
	 * 
	 * @param fileChooser a {@code JFileChoose} to configure.
	 */
	public void configure(JFileChooser fileChooser) {
		final File selectedFile = fileChooser.getSelectedFile();

		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(this.selectionMode);

		fileChooser.resetChoosableFileFilters();
		for (FileFilter filter : this.filters) {
			fileChooser.addChoosableFileFilter(filter);
		}
		fileChooser.setAcceptAllFileFilterUsed(this.allowAll);

		if (this.selectionMode == JFileChooser.DIRECTORIES_ONLY) {
			fileChooser.setCurrentDirectory(selectedFile);
		} else {
			if (this.clearSelectedFileOnShow) {
				fileChooser.setSelectedFile(new File(""));
			} else {
				fileChooser.setSelectedFile(selectedFile);
			}
		}
	}
}
