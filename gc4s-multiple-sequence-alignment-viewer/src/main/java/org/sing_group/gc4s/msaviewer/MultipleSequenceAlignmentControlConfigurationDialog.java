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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;


/**
 * A dialog that allows user to set the values of a
 * {@code MultipleSequenceAlignmentViewerConfiguration}. This dialog uses a
 * {@code MultipleSequenceAlignmentViewerConfigurationPanel}.
 *
 * @author hlfernandez
 *
 * @see MultipleSequenceAlignmentViewerConfigurationPanel
 *
 */
public class MultipleSequenceAlignmentControlConfigurationDialog
	extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private MultipleSequenceAlignmentViewerConfiguration configuration;
	private JPanel componentsPane;
	private JCheckBox liveUpdateCheckbox;
	private MultipleSequenceAlignmentViewerConfigurationPanel configurationPanel;

	/**
	 * Creates a new dialog with the controls initialized with the values
	 * specified by {@code configuration}.
	 *
	 * @param parent the parent {@code Window}
	 * @param configuration the initial configuration values
	 */
	protected MultipleSequenceAlignmentControlConfigurationDialog(Window parent,
		MultipleSequenceAlignmentViewerConfiguration configuration
	) {
		super(parent);
		this.configuration = configuration;

		this.init();
	}

	private void init() {
		configurationPanel =
			new MultipleSequenceAlignmentViewerConfigurationPanel(configuration);
		configurationPanel
			.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (MultipleSequenceAlignmentViewerConfigurationPanel
						.properties().contains(evt.getPropertyName())
					) {
						configurationEdited(evt);
					}
				}
			});
		componentsPane.add(configurationPanel, BorderLayout.CENTER);
	}

	@Override
	protected Component getInputComponentsPane() {
		componentsPane = new JPanel(new BorderLayout());

		componentsPane.add(getCustomButtonsPane(), BorderLayout.SOUTH);

		return componentsPane;
	}

	private Component getCustomButtonsPane() {
		JPanel panel = new JPanel(new FlowLayout());
		liveUpdateCheckbox = new JCheckBox("Live update");
		panel.add(liveUpdateCheckbox);

		return panel;
	}

	@Override
	protected String getDialogTitle() {
		return "Edit configuration";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to edit the configuration.";
	}

	private void configurationEdited(PropertyChangeEvent event) {
		firePropertyChange(
			event.getPropertyName(), event.getOldValue(), event.getNewValue());
	}

	@Override
	public void setVisible(boolean aFlag) {
		okButton.setEnabled(true);
		this.pack();
		super.setVisible(aFlag);
	}

	/**
	 * Returns {@code true} if the live update option is selected and
	 * {@code false} if it is not.
	 *
	 * @return {@code true} if the live update option is selected and
	 * 		   {@code false} if it is not
	 */
	public boolean isLiveUpdate() {
		return this.liveUpdateCheckbox.isSelected();
	}

	/**
	 * Returns a new {@code MultipleSequenceAlignmentViewerConfiguration} object
	 * with the values introduced by the user.
	 *
	 * @return the {@code MultipleSequenceAlignmentViewerConfiguration} object
	 */
	public MultipleSequenceAlignmentViewerConfiguration getConfiguration() {
		return this.configurationPanel.getConfiguration();
	}
}
