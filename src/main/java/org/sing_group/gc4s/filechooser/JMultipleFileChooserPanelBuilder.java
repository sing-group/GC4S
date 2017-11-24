package org.sing_group.gc4s.filechooser;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;



/**
 * A builder class for {@link JMultipleFileChooserPanel}.
 * 
 * @author hlfernandez
 * @see JMultipleFileChooserPanel
 */
public class JMultipleFileChooserPanelBuilder {

	private Mode mode;
	private JFileChooser fileChooser = new JFileChooser();
	private ImageIcon browseIcon = JMultipleFileChooserPanel.DEFAULT_ICON_BROWSE;
	private SelectionMode selectionMode = JMultipleFileChooserPanel.DEFAULT_SELECTION_MODE;
	private boolean allowAllFilter = JMultipleFileChooserPanel.DEFAULT_ALLOW_ALL_FILTER;
	private List<FileFilter> fileFilters = JMultipleFileChooserPanel.DEFAULT_FILE_FILTERS;

	/**
	 * Returns a new {@code JMultipleFileChooserPanelBuilder} instance to build
	 * a {@code JMultipleFileChooserPanel} with {@link Mode#OPEN}, which means
	 * that the {@code JFileChooser} is shown using
	 * {@link JFileChooser#showOpenDialog}.
	 * 
	 * @return a new {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public static JMultipleFileChooserPanelBuilder createOpenJMultipleFileChooserPanel() {
		return new JMultipleFileChooserPanelBuilder(Mode.OPEN);
	}

	/**
	 * Returns a new {@code JMultipleFileChooserPanelBuilder} instance to build
	 * a {@code JMultipleFileChooserPanel} with {@link Mode#SAVE}, which means
	 * that the {@code JFileChooser} is shown using
	 * {@link JFileChooser#showSaveDialog}.
	 * 
	 * @return a new {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public static JMultipleFileChooserPanelBuilder createSaveJMultipleFileChooserPanel() {
		return new JMultipleFileChooserPanelBuilder(Mode.SAVE);
	}

	protected JMultipleFileChooserPanelBuilder(Mode mode) {
		this.mode = mode;
	}

	/**
	 * Sets the {@code JFileChooser} that will be opened.
	 * 
	 * @param fileChooser the {@code JFileChooser} that will be opened.
	 * @return the {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public JMultipleFileChooserPanelBuilder withFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
		return this;
	}

	/**
	 * Sets the {@code ImageIcon} for the browse button.
	 * 
	 * @param browseIcon the {@code ImageIcon} for the browse button.
	 * @return the {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public JMultipleFileChooserPanelBuilder withBrowseIcon(ImageIcon browseIcon) {
		this.browseIcon = browseIcon;
		return this;
	}

	/**
	 * Sets the {@code JFileChooser} selection mode.
	 * 
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 * @return the {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public JMultipleFileChooserPanelBuilder withFileChooserSelectionMode(
		SelectionMode selectionMode
	) {
		this.selectionMode = selectionMode;
		return this;
	}

	/**
	 * Sets whether the "All files" file filter should be used or not.
	 * 
	 * @param allowAllFilter whether the "All files" file filter should be used 
	 * 	      or not.
	 * @return the {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public JMultipleFileChooserPanelBuilder withAllowAllFilter(boolean allowAllFilter) {
		this.allowAllFilter = allowAllFilter;
		return this;
	}

	/**
	 * Sets the list of {@code FileFilter} to use in the file chooser.
	 * 
	 * @param fileFilters the list of {@code FileFilter} to use in the file 
	 * 	      chooser.
	 * @return the {@code JMultipleFileChooserPanelBuilder} instance.
	 */
	public JMultipleFileChooserPanelBuilder withFileFilters(
		List<FileFilter> fileFilters
	) {
		this.fileFilters = fileFilters;
		return this;
	}

	/**
	 * Builds the {@code JMultipleFileChooserPanel} using the introduced
	 * configuration.
	 * 
	 * @return a new {@code JMultipleFileChooserPanel} instance.
	 */
	public JMultipleFileChooserPanel build() {
		return new JMultipleFileChooserPanel(
			this.mode, this.fileChooser,
			this.browseIcon, this.selectionMode, 
			this.allowAllFilter, this.fileFilters
		);
	}
}
