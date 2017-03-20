package es.uvigo.ei.sing.hlfernandez.filechooser;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.Mode;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;


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
		return new JFileChooserPanelBuilder(JFileChooserPanel.Mode.OPEN);
	}

	/**
	 * Returns a new {@code JFileChooserPanelBuilder} instance to build a
	 * {@code JFileChooserPanel} with {@link Mode#SAVE}, which means that the
	 * {@code JFileChooser} is shown using {@link JFileChooser#showSaveDialog}.
	 * 
	 * @return a new {@code JFileChooserPanelBuilder} instance.
	 */
	public static JFileChooserPanelBuilder createSaveJFileChooserPanel() {
		return new JFileChooserPanelBuilder(JFileChooserPanel.Mode.SAVE);
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
