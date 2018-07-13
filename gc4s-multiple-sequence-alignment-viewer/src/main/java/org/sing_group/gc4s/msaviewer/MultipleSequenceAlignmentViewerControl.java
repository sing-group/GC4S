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

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JOptionPane.showConfirmDialog;
import static org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerPanel.requireSameLengthSequences;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.sing_group.gc4s.ui.menu.HamburgerMenu;
import org.sing_group.gc4s.ui.menu.HamburgerMenu.Size;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;

/**
 * A panel that wraps a {@code MultipleSequenceAlignmentViewerPanel} and allows
 * the selection of different models and configuring the visual display
 * settings.
 *
 * @author hlfernandez
 * @author mrjato
 *
 * @see MultipleSequenceAlignmentViewerPanel
 *
 */
public class MultipleSequenceAlignmentViewerControl extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Sequence> alignedSequences;
	private List<MultipleSequenceAlignmentTracksModel> models;
	private Map<MultipleSequenceAlignmentTracksModel, SequenceAlignmentRenderer> modelRenderers;
	private Map<String, MultipleSequenceAlignmentTracksModel> modelsMap;
	private MultipleSequenceAlignmentViewerConfiguration configuration;
	private MultipleSequenceAlignmentViewerPanel msaViewerPanel;
	private JComboBox<String> cmbModels;

	/**
	 * Creates a new {@code MultipleSequenceAlignmentViewerControl} with the
	 * specified sequences, configuration, tracks models and sequence alignment
	 * renderers.
	 *
	 * Note that all sequences in the list must have the same length.
	 *
	 * @param alignedSequences a list of {@code Sequence}
	 * @param models the list of {@code MultipleSequenceAlignmentTracksModel}s
	 * @param modelRenderers the map of {@code SequenceAlignmentRenderer}
	 * @param configuration the {@code MultipleSequenceAlignmentViewerConfiguration}
	 */
	public MultipleSequenceAlignmentViewerControl(
		final List<Sequence> alignedSequences,
		final List<MultipleSequenceAlignmentTracksModel> models,
		final Map<MultipleSequenceAlignmentTracksModel, SequenceAlignmentRenderer> modelRenderers,
		final MultipleSequenceAlignmentViewerConfiguration configuration
	) {
		super(new BorderLayout());

		this.alignedSequences = requireSameLengthSequences(alignedSequences);
		this.configuration = configuration;
		this.modelRenderers = modelRenderers;
		this.models = models;
		this.modelsMap = new HashMap<>();
		this.models.forEach(m -> {
			this.modelsMap.put(m.getName(), m);
		});

		this.init();
	}

	private void init() {
		final JComponent selectionComponent;
		if (!models.isEmpty()) {
			this.cmbModels = new JComboBox<>(new Vector<>(modelsMap.keySet()));
			this.cmbModels.setSelectedItem(models.get(0).getName());
			this.cmbModels.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						updateSelectedModel();
					}
				}
			);

			selectionComponent = this.cmbModels;
		} else {
			this.cmbModels = null;
			selectionComponent = new JLabel(getNoModelsMessage());
		}

		if(!this.models.isEmpty()) {
			this.msaViewerPanel = new MultipleSequenceAlignmentViewerPanel(
				this.alignedSequences, models.get(0),
				getModelRenderer(models.get(0)),
				configuration
			);
		} else {
			this.msaViewerPanel = new MultipleSequenceAlignmentViewerPanel(
				this.alignedSequences, configuration);
		}

		final JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.setBorder(createEmptyBorder(5, 5, 5, 5));
		controlPanel.add(selectionComponent, BorderLayout.WEST);
		controlPanel.add(getButtonsPanel(), BorderLayout.EAST);

		this.add(controlPanel, BorderLayout.NORTH);
		this.add(this.msaViewerPanel, BorderLayout.CENTER);
	}

	protected JPanel getButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		HamburgerMenu menu = new HamburgerMenu(Size.SIZE24);

		for (Action a : getButtonActions()) {
			menu.add(a);
		}
		buttonsPanel.add(menu);

		return buttonsPanel;
	}

	protected List<Action> getButtonActions() {
		List<Action> actions = new LinkedList<>();
		actions.add(getEditConfigurationAction());
		actions.add(getExportToHtmlAction());
		actions.add(getExportToPngAction());

		return actions;
	}

	private Action getEditConfigurationAction() {
		return new ExtendedAbstractAction("Edit configuration", this::editConfiguration);
	}

	private Action getExportToHtmlAction() {
		return new ExtendedAbstractAction("Export to HTML", this::exportToHtml);
	}

	private Action getExportToPngAction() {
		return new ExtendedAbstractAction("Export to PNG", this::exportToPng);
	}

	private SequenceAlignmentRenderer getModelRenderer(
		MultipleSequenceAlignmentTracksModel model
	) {
		return modelRenderers.getOrDefault(model,
			new DefaultSequenceAlignmentRenderer());
	}

	protected String getNoModelsMessage() {
		return "No Models";
	}

	private void updateSelectedModel() {
		MultipleSequenceAlignmentTracksModel model = getSelectedModel();
		this.msaViewerPanel.setModelAndRenderer(model, getModelRenderer(model));
	}

	private MultipleSequenceAlignmentTracksModel getSelectedModel() {
		return this.modelsMap.get(((String) this.cmbModels.getSelectedItem()));
	}

	private void editConfiguration() {
		MultipleSequenceAlignmentViewerConfiguration previousConfiguration = this.msaViewerPanel.getConfiguration();
		MultipleSequenceAlignmentControlConfigurationDialog dialog =
			new MultipleSequenceAlignmentControlConfigurationDialog(
				SwingUtilities.getWindowAncestor(this), previousConfiguration);

		dialog.addPropertyChangeListener((e) -> {
			if (MultipleSequenceAlignmentViewerConfigurationPanel.properties()
				.contains(e.getPropertyName()) && dialog.isLiveUpdate()
			) {
				setConfiguration(dialog.getConfiguration());
			}
		});

		dialog.setVisible(true);

		if (!dialog.isCanceled() && !dialog.isLiveUpdate()) {
			setConfiguration(dialog.getConfiguration());
		} else if (dialog.isCanceled()) {
			setConfiguration(previousConfiguration);
		}
	}

	private void exportToHtml() {
		Optional<File> exportFile = getExportFile();
		if (exportFile.isPresent()) {
			try {
				this.msaViewerPanel.exportToHtml(exportFile.get().toPath());
			} catch (IOException e) {
				showConfirmDialog(this,
					"An error has been produced while writing alignment to "
						+ exportFile.get().getAbsolutePath() + ".",
					"Write error", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void exportToPng() {
		Optional<File> exportFile = getExportFile();
		if (exportFile.isPresent()) {
			try {
				this.msaViewerPanel.exportToPng(exportFile.get().toPath());
			} catch (IOException e) {
				showConfirmDialog(this,
					"An error has been produced while writing image alignment to "
						+ exportFile.get().getAbsolutePath() + ".",
					"Write error", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private Optional<File> getExportFile() {
		JFileChooser fc = getFileChooser();
		int result = fc.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			return Optional.of(fc.getSelectedFile());
		} else {
			return Optional.empty();
		}
	}

	protected JFileChooser getFileChooser() {
		return new JFileChooser();
	}

	private void setConfiguration(
		MultipleSequenceAlignmentViewerConfiguration configuration
	) {
		this.msaViewerPanel.setConfiguration(configuration);
	}
}
