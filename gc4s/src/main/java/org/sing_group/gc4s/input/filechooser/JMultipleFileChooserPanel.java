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
import java.io.File;
import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.filechooser.FileFilter;

import org.sing_group.gc4s.event.ListDataAdapter;
import org.sing_group.gc4s.input.filechooser.event.MultipleFileChooserListener;
import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.input.list.JListPanel;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.FileDrop;
import org.sing_group.gc4s.utilities.FileDropListener;

/**
 * <p>
 * A {@code JMultipleFileChooserPanel} displays a {@code JList} along with a
 * browse button that allows users to select several files. The names of the
 * selected files are shown in the list.
 * </p>
 * 
 * <p>
 * This component remembers the last file filter selected in the
 * {@code JFileChooser}. If you create different {@code JMultipleFileChooserPanel}
 * components that share a the same set of filters and you want them to remember
 * other's selection, then you must invoke
 * {@link JMultipleFileChooserPanel#setUseSharedLastFileFilter(boolean)}. This
 * way, user's choice in one of them will be remembered in the others.
 * </p>
 * 
 * <p>
 * Moreover, {@code JMultipleFileChooserPanelBuilder} is provided to facilitate the
 * creation of this component.
 * </p>
 * 
 * @author hlfernandez
 * @see JMultipleFileChooserPanelBuilder
 * @see JMultipleFileChooserPanel
 */
public class JMultipleFileChooserPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final ImageIcon DEFAULT_ICON_BROWSE = Icons.ICON_BROWSE_16;
	public static final SelectionMode DEFAULT_SELECTION_MODE = 
		SelectionMode.FILES_DIRECTORIES;
	public static final boolean DEFAULT_ALLOW_ALL_FILTER = true;
	public static final boolean DEFAULT_CLEAR_SELECTED_FILE_ON_SHOW = false;
	public static final List<FileFilter> DEFAULT_FILE_FILTERS = 
		Collections.emptyList();
	
	private JFileChooser filechooser;
	private Mode mode;
	private AbstractAction browseAction;
	private JFileChooserConfiguration fileChooserConfiguration;

	private boolean useSharedLastFileFilter = false;
	private static FileFilter LAST_FILE_FILTER;
	private FileFilter lastFileFilter;

	private JListPanel<File> selectedFilesListPanel;
	private ExtendedDefaultListModel<File> selectedFilesModel;

	/**
	 * Constructs a {@link JMultipleFileChooserPanel} with the specified
	 * {@code} mode}. For the rest, the default configuration (default browse
	 * icon and a new {@code JFileChooser}) is taken.
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 */
	public JMultipleFileChooserPanel(Mode mode) {
		this(mode, new JFileChooser(), DEFAULT_ICON_BROWSE,
			DEFAULT_SELECTION_MODE);
	}
	
	/**
	 * Constructs a {@link JMultipleFileChooserPanel} with the specified
	 * {@code} mode} and {@code filechooser}. For the rest, the default
	 * configuration (default browse icon).
	 * 
	 * @param mode the {@code JFileChooser} mode.
	 * @param filechooser the {@link JFileChooser} that will be opened.
	 */
	public JMultipleFileChooserPanel(Mode  mode, JFileChooser filechooser
	){
		this(mode, filechooser, DEFAULT_ICON_BROWSE, DEFAULT_SELECTION_MODE);
	}

	/**
	 * <p>
	 * Constructs a {@link JMultipleFileChooserPanel} with the specified
	 * {@code} mode}, {@code selectionMode} and {@code filechooser}. For the
	 * rest, the default configuration (default browse icon) is taken.
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
	public JMultipleFileChooserPanel(Mode mode, SelectionMode selectionMode,
		JFileChooser filechooser
	) {
		this(mode, filechooser, DEFAULT_ICON_BROWSE, selectionMode);
	}

	/**
	 * <p>
	 * Constructs a {@link JMultipleFileChooserPanel} with the specified
	 * {@code} mode}, {@code selectionMode}, {@code filechooser} and
	 * {@code browseIcon}.
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
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 */
	public JMultipleFileChooserPanel(
		Mode mode, JFileChooser filechooser,
		ImageIcon browseIcon, SelectionMode selectionMode
	) {
		this(
			mode, filechooser, browseIcon, selectionMode,
			DEFAULT_ALLOW_ALL_FILTER, DEFAULT_CLEAR_SELECTED_FILE_ON_SHOW, DEFAULT_FILE_FILTERS
		);
	}

	/**
	 * <p>
	 * Constructs a {@link JMultipleFileChooserPanel} with the specified 
	 * {@code} mode}, {@code filechooser}, {@code browseIcon}, and 
	 * {@code labelFiletext}.
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
	 * @param selectionMode the {@code JFileChooser} selection mode.
	 * @param allowAll whether the "All files" file filter should be used or 
	 * 	not.
	 * @param clearSelectedFileOnShow whether to clear the selected file on 
	 *  show or not.
	 * @param fileFilters the list of {@code FileFilter} to use in the file 
	 * 	chooser.
	 */	
	public JMultipleFileChooserPanel(
		Mode mode, JFileChooser filechooser,
		ImageIcon browseIcon, SelectionMode selectionMode,
		boolean allowAll, boolean clearSelectedFileOnShow, List<FileFilter> fileFilters
	) {
		super();
		this.filechooser = filechooser;
		this.mode = mode;
		this.fileChooserConfiguration =
			new JFileChooserConfiguration(
				selectionMode.getFileSelectionMode(), fileFilters, allowAll, clearSelectedFileOnShow
			);

		initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());

		selectedFilesModel = new ExtendedDefaultListModel<File>();
		selectedFilesModel
			.addExtendedDefaultListModelListener(src -> fireFileChoosedEvent());
		selectedFilesModel.addListDataListener(new ListDataAdapter() {

			@Override
			public void intervalRemoved(ListDataEvent e) {
				fireFileRemovedEvent();

			}
		});
		JList<File> list = new JList<File>(selectedFilesModel);
		try {
			selectedFilesListPanel = new JListPanel<File>(list, true, false);
			selectedFilesListPanel.getBtnMoveDown().setVisible(false);
			selectedFilesListPanel.getBtnMoveUp().setVisible(false);
			selectedFilesListPanel.addAction(getBrowseAction());
			this.add(selectedFilesListPanel, BorderLayout.CENTER);
		} catch (InvalidClassException e1) {
			throw new RuntimeException(e1);
		}

		new FileDrop(list, new FileDropListener() {
			public void filesDropped(File[] files) {
				setSelectedFiles(files);
			}
		});
	}

	private void fireFileChoosedEvent() {
		Arrays.asList(getFileChooserListeners()).forEach(l -> {
			l.onFileChoosed(new ChangeEvent(this));
		});
	}

	private void fireFileRemovedEvent() {
		Arrays.asList(getFileChooserListeners()).forEach(l -> {
			l.onFileRemoved(new ChangeEvent(this));
		});
	}

	private void onBrowse() {
		JFileChooser fileChooser = getConfiguredFileChooser();
		
		int returnVal = mode.equals(Mode.SAVE) ? 
			fileChooser.showSaveDialog(JMultipleFileChooserPanel.this) : 
			fileChooser.showOpenDialog(JMultipleFileChooserPanel.this);

		saveLastFileFilter(fileChooser.getFileFilter());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			setSelectedFiles(fileChooser.getSelectedFiles());
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
		fileChooser.setMultiSelectionEnabled(true);

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
				.filter(JMultipleFileChooserPanel::isAcceptAllFileFilter).findAny();
	}

	private void clearFileChooser() {
		getFilechooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	/**
	 * Sets the selected files.
	 * 
	 * @param files the selected {@code File}s.
	 */
	public void setSelectedFiles(File[] files) {
		this.selectedFilesModel.addElements(Arrays.asList(files));
		this.selectedFilesListPanel.getList().updateUI();
	}

	/**
	 * Clears the selected files.
	 */
	public void clearSelectedFiles() {
		this.selectedFilesModel.removeAllElements();
		this.selectedFilesListPanel.getList().updateUI();
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
		if (this.browseAction == null) {
			this.browseAction = 
				new AbstractAction("Browse", DEFAULT_ICON_BROWSE) {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						onBrowse();
					}
				};
		}
		return this.browseAction;
	}
	
	/**
	 * Returns the selected files.
	 * 
	 * @return the selected files
	 */
	public List<File> getSelectedFiles() {
		List<File> toret = new LinkedList<>();
		Enumeration<File> elements = this.selectedFilesModel.elements();
		while (elements.hasMoreElements()) {
			toret.add(elements.nextElement());
		}
		return toret;
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
	 * @param l the {@code MultipleFileChooserListener}.
	 */
	public synchronized void addFileChooserListener(MultipleFileChooserListener l) {
		this.listenerList.add(MultipleFileChooserListener.class, l);
	}

	/**
	 * Returns an array of all the file chooser listeners registered on this
	 * component.
	 *
	 * @return all {@code MultipleFileChooserListener}s of this component or an empty
	 * array if no component listeners are currently registered
	 */
	public synchronized MultipleFileChooserListener[] getFileChooserListeners() {
		return this.listenerList.getListeners(MultipleFileChooserListener.class);
	}
}
