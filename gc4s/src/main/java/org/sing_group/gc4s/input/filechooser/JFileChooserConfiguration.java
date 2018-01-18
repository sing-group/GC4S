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
		this(selectionMode, Collections.emptyList(), true);
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
	public JFileChooserConfiguration(int selectionMode,
		List<FileFilter> filters) {
		this(selectionMode, filters, true);
	}

	/**
	 * Creates a new {@code JFileChooserConfiguration} with the specified
	 * {@code JFileChooser} selection mode. The list of {@code FileFilter} is
	 * used to configure the {@code JFileChooser} filters and {@code alloAll}
	 * indicates whether the accept all files filter is used or not.
	 * 
	 * @param selectionMode the {@code JFileChooser} selection mode
	 *  ({@code JFileChooser#FILES_AND_DIRECTORIES},
	 *  {@code JFileChooser#FILES_ONLY} or
	 *	{@code JFileChooser#DIRECTORIES_ONLY})
	 * @param filters a list of {@code FileFilter}.
	 * @param allowAll whether the accept all files filter is used or not.
	 */
	public JFileChooserConfiguration(int selectionMode,
		List<FileFilter> filters, boolean allowAll) {
		this.selectionMode = selectionMode;
		this.filters = filters;
		this.allowAll = allowAll;
	}

	/**
	 * Applies the configuration to {@code fileChooser}.
	 * 
	 * @param fileChooser a {@code JFileChoose} to configure.
	 */
	public void configure(JFileChooser fileChooser) {
		final File selectedFile = fileChooser.getSelectedFile();
		fileChooser.setSelectedFile(null);

		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(this.selectionMode);

		fileChooser.resetChoosableFileFilters();
		for (FileFilter filter : this.filters) {
			fileChooser.addChoosableFileFilter(filter);
		}
		fileChooser.setAcceptAllFileFilterUsed(this.allowAll);

		fileChooser.setSelectedFile(selectedFile);
	}
}
