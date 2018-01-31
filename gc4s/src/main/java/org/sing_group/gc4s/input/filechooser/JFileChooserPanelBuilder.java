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

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


/**
 * A builder class for {@link JFileChooserPanel}.
 * 
 * @author hlfernandez
 * @see JFileChooserPanel
 */
public class JFileChooserPanelBuilder {

	private Mode mode;
	private JFileChooser fileChooser = new JFileChooser();
	private ImageIcon browseIcon = JFileChooserPanel.DEFAULT_ICON_BROWSE;
	private String labelFileText = JFileChooserPanel.DEFAULT_FILES_LABEL;
	private String requiredSaveFileExtension;
	private SelectionMode selectionMode = JFileChooserPanel.DEFAULT_SELECTION_MODE;
	private boolean allowAllFilter = JFileChooserPanel.DEFAULT_ALLOW_ALL_FILTER;
	private List<FileFilter> fileFilters = JFileChooserPanel.DEFAULT_FILE_FILTERS;

	/**
	 * Returns a new {@code JFileChooserPanelBuilder} instance to build a
	 * {@code JFileChooserPanel} with {@link Mode#OPEN}, which means that the
	 * {@code JFileChooser} is shown using {@link JFileChooser#showOpenDialog}.
	 * 
	 * @return a new {@code JFileChooserPanelBuilder} instance.
	 */
	public static JFileChooserPanelBuilder createOpenJFileChooserPanel() {
		return new JFileChooserPanelBuilder(Mode.OPEN);
	}

	/**
	 * Returns a new {@code JFileChooserPanelBuilder} instance to build a
	 * {@code JFileChooserPanel} with {@link Mode#SAVE}, which means that the
	 * {@code JFileChooser} is shown using {@link JFileChooser#showSaveDialog}.
	 * 
	 * @return a new {@code JFileChooserPanelBuilder} instance.
	 */
	public static JFileChooserPanelBuilder createSaveJFileChooserPanel() {
		return new JFileChooserPanelBuilder(Mode.SAVE);
	}

	protected JFileChooserPanelBuilder(Mode mode) {
		this.mode = mode;
	}

	/**
	 * Sets the {@code JFileChooser} that will be opened.
	 * 
	 * @param fileChooser the {@code JFileChooser} that will be opened.
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
		return this;
	}

	/**
	 * Sets the {@code ImageIcon} for the browse button.
	 * 
	 * @param browseIcon the {@code ImageIcon} for the browse button.
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withBrowseIcon(ImageIcon browseIcon) {
		this.browseIcon = browseIcon;
		return this;
	}

	/**
	 * Sets the text for the label file.
	 * 
	 * @param labelFileText the text for the label file.
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withLabel(String labelFileText) {
		this.labelFileText = labelFileText;
		return this;
	}

	/**
	 * Only for Mode.SAVE, sets the extension that file must have (e.g.: "txt").
	 * 
	 * @param requiredSaveFileExtension only for Mode.SAVE, the extension that 
	 * 	file must have (e.g.: "txt")
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withRequiredFileSaveExtension(
		String requiredSaveFileExtension
	) {
		this.requiredSaveFileExtension = requiredSaveFileExtension;
		return this;
	}

	/**
	 * Sets the {@code JFileChooser} selection mode.
	 * 
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withFileChooserSelectionMode(
		SelectionMode selectionMode
	) {
		this.selectionMode = selectionMode;
		return this;
	}

	/**
	 * Sets whether the "All files" file filter should be used or not.
	 * 
	 * @param allowAllFilter whether the "All files" file filter should be used 
	 * 	or not.
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withAllowAllFilter(boolean allowAllFilter) {
		this.allowAllFilter = allowAllFilter;
		return this;
	}

	/**
	 * Sets the list of {@code FileFilter} to use in the file chooser.
	 * 
	 * @param fileFilters the list of {@code FileFilter} to use in the file 
	 * 	chooser.
	 * @return the {@code JFileChooserPanelBuilder} instance.
	 */
	public JFileChooserPanelBuilder withFileFilters(
		List<FileFilter> fileFilters
	) {
		this.fileFilters = fileFilters;
		return this;
	}

	/**
	 * Builds the {@code JFileChooserPanel} using the introduced configuration.
	 * 
	 * @return a new {@code JFileChooserPanel} instance.
	 */
	public JFileChooserPanel build() {
		return new JFileChooserPanel(this.mode, this.fileChooser,
			this.browseIcon, this.labelFileText, this.requiredSaveFileExtension,
			this.selectionMode, this.allowAllFilter, this.fileFilters);
	}
}
