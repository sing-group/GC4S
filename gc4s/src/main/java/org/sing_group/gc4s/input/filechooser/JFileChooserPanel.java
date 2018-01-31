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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.filechooser.event.FileChooserListener;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.FileDrop;
import org.sing_group.gc4s.utilities.builder.JButtonBuilder;

/**
 * <p>
 * A {@code JFileChooserPanel} displays a text field together a browse button
 * that allows users to select a file. The name of the file selected is shown in
 * the text field.
 * </p>
 * 
 * <p>
 * This component remembers the last file filter selected in the
 * {@code JFileChooser}. If you create different {@code JFileChooserPanel}
 * components that share a the same set of filters and you want them to remember
 * other's selection, then you must invoke
 * {@link JFileChooserPanel#setUseSharedLastFileFilter(boolean)}. This way, user's
 * choice in one of them will be remembered in the others.
 * </p>
 * 
 * <p>
 * Moreover, {@code JFileChooserPanelBuilder} is provided to facilitate the
 * creation of this component.
 * </p>
 * 
 * @author hlfernandez
 * @see JFileChooserPanelBuilder
 * @see JFileChooser
 */
public class JFileChooserPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_FILES_LABEL = "File: ";
	public static final ImageIcon DEFAULT_ICON_BROWSE = Icons.ICON_BROWSE_16;
	public static final SelectionMode DEFAULT_SELECTION_MODE = 
		SelectionMode.FILES_DIRECTORIES;
	public static final boolean DEFAULT_ALLOW_ALL_FILTER = true;
	public static final List<FileFilter> DEFAULT_FILE_FILTERS = 
		Collections.emptyList();
	
	private JFileChooser filechooser;
	private Mode mode;
	private AbstractAction browseAction;
	private JButton btnBrowse;
	private JLabel lblFile;
	private String lblFileText;
	private JTextField fileName;
	private File selectedFile = null;
	private String requiredFileExtension = null;
	private JFileChooserConfiguration fileChooserConfiguration;

	private boolean useSharedLastFileFilter = false;
	private static FileFilter LAST_FILE_FILTER;
	private FileFilter lastFileFilter;

	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}.
	 * For the rest, the default configuration (default browse icon, file label
	 * text and a new {@code JFileChooser} and no required extension) is taken.
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 */
	public JFileChooserPanel(Mode mode) {
		this(mode, new JFileChooser(), DEFAULT_ICON_BROWSE, DEFAULT_FILES_LABEL,
			null, DEFAULT_SELECTION_MODE);
	}

	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}
	 * and {@code requiredExtension}. For the rest, the default configuration
	 * (default browse icon, file label text and a new {@code JFileChooser}) is
	 * taken.
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param requiredFileExtension only for Mode.SAVE, the extension that file 
	 * 	must have (e.g.: "txt").
	 */
	public JFileChooserPanel(Mode mode, String requiredFileExtension) {
		this(mode, new JFileChooser(), DEFAULT_ICON_BROWSE, DEFAULT_FILES_LABEL,
			requiredFileExtension, SelectionMode.FILES_DIRECTORIES);
	}
	
	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}
	 * and {@code filechooser}. For the rest, the default configuration (default
	 * browse icon, file label text and no required extension) is taken.
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 * @param requiredFileExtension only for Mode.SAVE, the extension that file 
	 * 	must have (e.g.: "txt").
	 */
	public JFileChooserPanel(Mode  mode, JFileChooser filechooser, 
		String requiredFileExtension
	){
		this(mode, filechooser, DEFAULT_ICON_BROWSE, DEFAULT_FILES_LABEL,
			requiredFileExtension, DEFAULT_SELECTION_MODE);
	}
	
	/**
	 * <p>
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode},
	 * {@code selectionMode} and {@code filechooser}. For the rest, the default
	 * configuration (default browse icon, file label text and no required
	 * extension) is taken.
	 * </p>
	 * 
	 * <p>
	 * Parameter {@code selectionMode} is used to configure the
	 * {@code JFileChooser} before showing it to the user.
	 * </p>
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 */
	public JFileChooserPanel(Mode mode, SelectionMode selectionMode,
		JFileChooser filechooser
	) {
		this(mode, filechooser, DEFAULT_ICON_BROWSE, DEFAULT_FILES_LABEL, null,
			selectionMode);
	}

	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}
	 * and {@code filechooser}. For the rest, the default configuration (default
	 * browse icon, file label text and no required extension) is taken.
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 */
	public JFileChooserPanel(Mode mode, JFileChooser filechooser) {
		this(mode, filechooser, DEFAULT_ICON_BROWSE, DEFAULT_FILES_LABEL, null,
			DEFAULT_SELECTION_MODE);
	}

	/**
	 * <p>
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode},
	 * {@code filechooser}, {@code labelFiletext} and {@code selectionMode}.
	 * </p>
	 * 
	 * <p>
	 * Parameter {@code selectionMode} is used to configure the
	 * {@code JFileChooser} before showing it to the user.
	 * </p>
	 *
	 * @param mode the {@code JFileChooser} mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 * @param labelFileText the text for the label file.
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 */
	public JFileChooserPanel(Mode mode, JFileChooser filechooser,
		String labelFileText, SelectionMode selectionMode
	) {
		this(mode, filechooser, DEFAULT_ICON_BROWSE, labelFileText, null,
			selectionMode);
	}

	/**
	 * <p>
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode},
	 * {@code selectionMode}, {@code filechooser}, {@code browseIcon},
	 * {@code labelFiletext} and {@code requiredFileExtension}.
	 * </p>
	 * 
	 * <p>
	 * Parameter {@code selectionMode} is used to configure the
	 * {@code JFileChooser} before showing it to the user.
	 * </p>
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 * @param browseIcon the {@link ImageIcon} for the browse button.
	 * @param labelFileText the text for the label file.
	 * @param requiredFileExtension only for Mode.SAVE, the extension that file 
	 *        must have (e.g.: "txt").
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 */
	public JFileChooserPanel(Mode mode, JFileChooser filechooser, 
		ImageIcon browseIcon, String labelFileText, 
		String requiredFileExtension, SelectionMode selectionMode
	) {
		this(mode, filechooser, browseIcon, labelFileText,
			requiredFileExtension, selectionMode, DEFAULT_ALLOW_ALL_FILTER,
			DEFAULT_FILE_FILTERS);
	}

	/**
	 * <p>
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode},
	 * {@code filechooser}, {@code selectionMode}, {@code browseIcon}, 
	 * {@code labelFiletext} and {@code requiredFileExtension}.
	 * </p>
	 * 
	 * <p>
	 * Parameters {@code selectionMode}, {@code allowAll} and {@code fileFilter}
	 * are used to configure the {@code JFileChooser} before showing it to the
	 * user.
	 * </p>
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 * @param browseIcon the {@link ImageIcon} for the browse button.
	 * @param labelFileText the text for the label file.
	 * @param requiredFileExtension only for Mode.SAVE, the extension that file 
	 * 	must have (e.g.: "txt")
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 * @param allowAll whether the "All files" file filter should be used or 
	 * 	not.
	 * @param fileFilters the list of {@code FileFilter} to use in the file 
	 * 	chooser.
	 */	
	public JFileChooserPanel(Mode mode, JFileChooser filechooser, 
		ImageIcon browseIcon, String labelFileText, 
		String requiredFileExtension, SelectionMode selectionMode,
		boolean allowAll, List<FileFilter> fileFilters
	) {
		super();
		this.filechooser = filechooser;
		this.lblFileText = labelFileText;
		if(mode.equals(Mode.SAVE)){
			this.requiredFileExtension = requiredFileExtension;
		}
		this.mode = mode;
		this.fileChooserConfiguration = new JFileChooserConfiguration(
			selectionMode.getFileSelectionMode(), fileFilters, allowAll);
		initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		lblFile = new JLabel(this.lblFileText);
		fileName = new JTextField("", 20);
		fileName.setEditable(false);
		fileName.getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				fileNameUpdated();
			}
		});
		fileName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON3
					&& browseAction.isEnabled()
				) {
					onBrowse();
				}
			}
		});
		new FileDrop(this.fileName, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				if (files.length > 0) {
					setSelectedFile(files[0]);
				}
			}
		});
		browseAction = new AbstractAction("Browse", DEFAULT_ICON_BROWSE) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				onBrowse();
			}
		};

		btnBrowse = JButtonBuilder.newJButtonBuilder()
			.thatDoes(browseAction)
			.withText("")
			.withTooltip("Browse file")
			.build();

		this.add(lblFile, BorderLayout.WEST);
		this.add(fileName, BorderLayout.CENTER);
		this.add(btnBrowse, BorderLayout.EAST);
	}

	private void fileNameUpdated() {
		fireFileChoosedEvent();
	}

	private void fireFileChoosedEvent() {
		Arrays.asList(getFileChooserListeners()).forEach(l -> {
			l.onFileChoosed(new ChangeEvent(this));
		});
	}

	private void onBrowse() {
		JFileChooser fileChooser = getConfiguredFileChooser();
		
		int returnVal = mode.equals(Mode.SAVE) ? 
			fileChooser.showSaveDialog(JFileChooserPanel.this) : 
			fileChooser.showOpenDialog(JFileChooserPanel.this);

		saveLastFileFilter(fileChooser.getFileFilter());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			setSelectedFile(fileChooser.getSelectedFile());
		}
		this.clearFileChooser();
	}

	private void saveLastFileFilter(FileFilter fileFilter) {
		this.lastFileFilter = fileFilter;
		if (useSharedLastFileFilter) {
			LAST_FILE_FILTER = this.lastFileFilter;
		}
	}

	private JFileChooser getConfiguredFileChooser() {
		JFileChooser fileChooser = getFilechooser();
		this.fileChooserConfiguration.configure(fileChooser);
		configureLastFileFilter(fileChooser);

		return fileChooser;
	}

	private void configureLastFileFilter(JFileChooser fileChooser) {
		if (useSharedLastFileFilter) {
			if (!setFileFilter(fileChooser, LAST_FILE_FILTER)) {
				if (isAcceptAllFileFilter(LAST_FILE_FILTER)) {
					/**
					 * If LAST_FILE_FILTER is the accept all file filter of a
					 * different JFileChooser, then it can't be found by the
					 * setFileFilter method.
					 */
					setAcceptAllFileFilter(fileChooser);
				}
			}
		} else {
			setFileFilter(fileChooser, lastFileFilter);
		}
	}

	private static boolean setFileFilter(JFileChooser fileChooser,
		FileFilter filter
	) {
		if (filter != null) {
			if (containsFilter(fileChooser, filter)) {
				fileChooser.setFileFilter(filter);
				return true;
			}
		}
		return false;
	}

	private static boolean containsFilter(JFileChooser fileChooser,
		FileFilter filter 
	) {
		return getFileFilters(fileChooser).contains(filter);
	}

	private static List<FileFilter> getFileFilters(JFileChooser fileChooser) {
		return Arrays.asList(fileChooser.getChoosableFileFilters());
	}

	private static boolean isAcceptAllFileFilter(FileFilter filter) {
		return filter != null
			&& filter.getClass().getSimpleName().equals("AcceptAllFileFilter");
	}

	private void setAcceptAllFileFilter(JFileChooser fileChooser) {
		Optional<FileFilter> acceptAllFileFilter =
			findAcceptAllFileFilter(fileChooser);
		if (acceptAllFileFilter.isPresent()) {
			fileChooser.setFileFilter(acceptAllFileFilter.get());
		}
	}

	private static Optional<FileFilter> findAcceptAllFileFilter(
		JFileChooser fileChooser
	) {
		return getFileFilters(fileChooser).stream()
				.filter(JFileChooserPanel::isAcceptAllFileFilter).findAny();
	}

	private void clearFileChooser() {
		getFilechooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	/**
	 * Sets the selected file.
	 * 
	 * @param file the selected {@code File}.
	 */
	public void setSelectedFile(File file) {
		if (this.selectedFile == null || !this.selectedFile.equals(file)) {
			selectedFile = file;
			if (requiredFileExtension != null && !file.getName().toLowerCase()
				.endsWith("." + requiredFileExtension)) {
				selectedFile = new File(
					file.getAbsolutePath() + "." + requiredFileExtension);
			}
			fileName.setText(selectedFile.getAbsolutePath());
			fileName.setToolTipText(selectedFile.getAbsolutePath());
			filechooser.setSelectedFile(file);
		}
	}

	/**
	 * Returns the browse action.
	 * 
	 * @return the browse action.
	 */
	public JFileChooser getFilechooser() {
		return filechooser;
	}
	
	/**
	 * Returns the browse action.
	 * 
	 * @return the browse action.
	 */
	public AbstractAction getBrowseAction() {
		return browseAction;
	}
	
	/**
	 * Returns the file label.
	 * 
	 * @return the file label.
	 */
	public JLabel getComponentLabelFile() {
		return lblFile;
	}
	
	/**
	 * Returns the file name text field component.
	 * 
	 * @return the file name text field component.
	 */
	public JTextField getComponentFileName() {
		return fileName;
	}

	/**
	 * Returns the browse button component.
	 * 
	 * @return the browse button component.
	 */
	public JButton getComponentButtonBrowse() {
		return btnBrowse;
	}

	/**
	 * Returns the selected file.
	 * 
	 * @return the selected file.
	 */
	public File getSelectedFile() {
		return selectedFile;
	}

	/**
	 * Establishes whether the last file filter selected by the user should be 
	 * shared with other instances or not.
	 * 
	 * @param use whether the last file filter selected by the user should be
	 *        shared or not.
	 */
	public void setUseSharedLastFileFilter(boolean use) {
		this.useSharedLastFileFilter = use;
	}

	/**
	 * Clears the last file filter shared with other instances.
	 */
	public static void clearSharedLastFileFilter() {
		LAST_FILE_FILTER = null;
	}

	/**
	 * Adds the specified file chooser listener to receive component events from
	 * this component. If listener {@code l} is {@code null}, no exception is
	 * thrown and no action is performed.
	 *
	 * @param l the {@code FileChooserListener}.
	 */
	public synchronized void addFileChooserListener(FileChooserListener l) {
		this.listenerList.add(FileChooserListener .class, l);
	}

	/**
	 * Returns an array of all the file chooser listeners registered on this
	 * component.
	 *
	 * @return all {@code FileChooserListener}s of this component or an empty
	 * array if no component listeners are currently registered
	 */
	public synchronized FileChooserListener[] getFileChooserListeners() {
		return this.listenerList.getListeners(FileChooserListener.class);
	}
}
