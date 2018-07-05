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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A panel that allows to set the values for a
 * {@code MultipleSequenceAlignmentViewerConfiguration}. Changes in the
 * configuration values can be listened using {@code PropertyChangeListener}s on
 * the properties of this panel
 * ({@link MultipleSequenceAlignmentViewerConfigurationPanel#properties()}).
 *
 * @author hlfernandez
 * @author mrjato
 *
 * @see MultipleSequenceAlignmentViewerConfiguration
 *
 */
public class MultipleSequenceAlignmentViewerConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_BLOCKS_PER_LINE = "configuration.blocks.per.line";
	public static final String PROPERTY_BLOCK_LENGTH = "configuration.block.length";
	public static final String PROPERTY_LABEL_TAB = "configuration.label.tag";
	public static final String PROPERTY_LABEL_LENGTH = "configuration.label.length";
	public static final String PROPERTY_FONT_SIZE = "configuration.font.size";
	public static final String PROPERTY_SHOW_INDEXES = "configuration.show.indexes";
	public static final String PROPERTY_SHOW_UPPER_TRACKS = "configuration.show.tracks.upper";
	public static final String PROPERTY_SHOW_BOTTOM_TRACKS = "configuration.show.tracks.bottom";

	private int oldLabelLength;
	private int oldLabelTab;
	private int oldBlockLength;
	private int oldBlocksPerLine;
	private int oldFontSize;
	private boolean oldShowIndexes;
	private boolean oldShowUpperTracks;
	private boolean oldShowBottomTracks;

	private JSpinner spnLabelLength;
	private JSpinner spnLabelTab;
	private JSpinner spnBlockLength;
	private JSpinner spnBlockPerLine;
	private JSpinner spnFontSize;
	private JCheckBox cbShowIndexes;
	private JCheckBox cbShowUpperTracks;
	private JCheckBox cbShowBottomTracks;

	/**
	 * Creates a new dialog with the controls initialized with the values
	 * specified by {@code configuration}.
	 *
	 * @param initialConfiguration the initial configuration values
	 */
	public MultipleSequenceAlignmentViewerConfigurationPanel(
		final MultipleSequenceAlignmentViewerConfiguration initialConfiguration
	) {
		super(new BorderLayout(10, 10));

		this.init(initialConfiguration);
	}

	private void init(
		final MultipleSequenceAlignmentViewerConfiguration initialConfiguration
	) {
		this.oldBlockLength = initialConfiguration.getLabelLength();
		this.oldLabelTab = initialConfiguration.getLabelTab();
		this.oldBlockLength = initialConfiguration.getBlockLength();
		this.oldBlocksPerLine = initialConfiguration.getBlocksPerLine();
		this.oldFontSize = initialConfiguration.getFontSize();
		this.oldShowIndexes = initialConfiguration.isShowIndexes();
		this.oldShowUpperTracks = initialConfiguration.isShowUpperTracks();
		this.oldShowBottomTracks = initialConfiguration.isShowBottomTracks();


		final JPanel controlPanelWrapper = new JPanel(new BorderLayout());
		controlPanelWrapper.setBorder(BorderFactory.createTitledBorder("Text Options"));

		final JPanel controlPanel = new JPanel(new GridLayout(5, 3, 5, 5));
		final JLabel lblLabelLength = new JLabel("Label Length");
		spnLabelLength = new JSpinner(new SpinnerNumberModel(initialConfiguration.getLabelLength(), 1, Integer.MAX_VALUE, 1));
		lblLabelLength.setLabelFor(spnLabelLength);

		final JLabel lblLabelTab = new JLabel("Label Tab");
		spnLabelTab = new JSpinner(new SpinnerNumberModel(initialConfiguration.getLabelTab(), 1, Integer.MAX_VALUE, 1));
		lblLabelTab.setLabelFor(spnLabelTab);

		final JLabel lblBlockLength = new JLabel("Block Length");
		spnBlockLength = new JSpinner(new SpinnerNumberModel(initialConfiguration.getBlockLength(), 1, Integer.MAX_VALUE, 1));
		lblBlockLength.setLabelFor(spnBlockLength);

		final JLabel lblBlocksPerLine = new JLabel("Blocks Per Line");
		spnBlockPerLine = new JSpinner(new SpinnerNumberModel(initialConfiguration.getBlocksPerLine(), 1, Integer.MAX_VALUE, 1));
		lblBlocksPerLine.setLabelFor(spnBlockPerLine);

		final JLabel lblFontSize = new JLabel("Font Size");
		final JLabel lblFontPts = new JLabel("pt");
		spnFontSize = new JSpinner(new SpinnerNumberModel(initialConfiguration.getFontSize(), 1, Integer.MAX_VALUE, 1));
		lblFontSize.setLabelFor(spnFontSize);

		cbShowIndexes = new JCheckBox("Show indexes (block length must be 5 or greater)");
		cbShowUpperTracks = new JCheckBox("Show upper tracks", initialConfiguration.isShowUpperTracks());
		cbShowBottomTracks = new JCheckBox("Show bottom tracks", initialConfiguration.isShowBottomTracks());

		controlPanel.add(lblFontSize);
		controlPanel.add(spnFontSize);
		controlPanel.add(lblFontPts);
		controlPanel.add(lblLabelLength);
		controlPanel.add(spnLabelLength);
		controlPanel.add(new JLabel());
		controlPanel.add(lblLabelTab);
		controlPanel.add(spnLabelTab);
		controlPanel.add(new JLabel());
		controlPanel.add(lblBlockLength);
		controlPanel.add(spnBlockLength);
		controlPanel.add(new JLabel());
		controlPanel.add(lblBlocksPerLine);
		controlPanel.add(spnBlockPerLine);
		controlPanel.add(new JLabel());

		final JPanel cbPanel = new JPanel(new GridLayout(3, 1));
		cbPanel.add(cbShowIndexes);
		cbPanel.add(cbShowUpperTracks);
		cbPanel.add(cbShowBottomTracks);

		controlPanelWrapper.add(controlPanel, BorderLayout.CENTER);
		controlPanelWrapper.add(cbPanel, BorderLayout.SOUTH);

		this.add(controlPanelWrapper);

		spnLabelLength.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					firePropertyChange(PROPERTY_LABEL_LENGTH, oldLabelLength, getLabelLenth());
					oldLabelLength = getLabelLenth();
				}
			}
		);
		spnLabelTab.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					firePropertyChange(PROPERTY_LABEL_TAB, oldLabelTab, getLabelTab());
					oldLabelTab = getLabelTab();
				}
			}
		);
		spnBlockLength.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					firePropertyChange(PROPERTY_BLOCK_LENGTH, oldBlockLength, getBlockLength());
					oldBlockLength = getBlockLength();
				}
			}
		);
		spnBlockPerLine.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					firePropertyChange(PROPERTY_BLOCKS_PER_LINE, oldBlocksPerLine, getBlocksPerLine());
					oldBlocksPerLine = getBlocksPerLine();
				}
			}
		);
		spnFontSize.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					firePropertyChange(PROPERTY_FONT_SIZE, oldFontSize, getFontSize());
					oldFontSize = getFontSize();
				}
			}
		);
		cbShowIndexes.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					firePropertyChange(PROPERTY_SHOW_INDEXES, oldShowIndexes, isShowIndexes());
					oldShowIndexes = isShowIndexes();
				};
			}
		);
		cbShowUpperTracks.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					firePropertyChange(PROPERTY_SHOW_UPPER_TRACKS, oldShowUpperTracks, isShowUpperTracks());
					oldShowUpperTracks = isShowUpperTracks();
				};
			}
		);
		cbShowBottomTracks.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					firePropertyChange(PROPERTY_SHOW_BOTTOM_TRACKS, oldShowBottomTracks, isShowBottomTracks());
					oldShowBottomTracks = isShowBottomTracks();
				};
			}
		);

	}

	public int getLabelLenth() {
		return (Integer) spnLabelLength.getValue();
	}

	public int getLabelTab() {
		return (Integer) spnLabelTab.getValue();
	}

	public int getBlockLength() {
		return (Integer) spnBlockLength.getValue();
	}

	public int getBlocksPerLine() {
		return (Integer) spnBlockPerLine.getValue();
	}

	public int getFontSize() {
		return (Integer) spnFontSize.getValue();
	}

	public boolean isShowIndexes() {
		return cbShowIndexes.isSelected();
	}

	public boolean isShowUpperTracks() {
		return cbShowUpperTracks.isSelected();
	}

	public boolean isShowBottomTracks() {
		return cbShowBottomTracks.isSelected();
	}

	/**
	 * Returns a set containing the specific properties of this component that
	 * can be notified with property changes.
	 *
	 * @return a set of the propeties that can be notified with property changes
	 */
	public static Set<String> properties() {
		return new HashSet<>(Arrays.asList(
			PROPERTY_BLOCKS_PER_LINE, PROPERTY_BLOCK_LENGTH,
			PROPERTY_LABEL_TAB, PROPERTY_LABEL_LENGTH,
			PROPERTY_FONT_SIZE, PROPERTY_SHOW_INDEXES,
			PROPERTY_SHOW_UPPER_TRACKS, PROPERTY_SHOW_BOTTOM_TRACKS
		));
	}

	/**
	 * Returns a new {@code MultipleSequenceAlignmentViewerConfiguration} object
	 * with the values introduced by the user.
	 *
	 * @return the {@code MultipleSequenceAlignmentViewerConfiguration} object
	 */
	public MultipleSequenceAlignmentViewerConfiguration getConfiguration() {
		return new MultipleSequenceAlignmentViewerConfiguration(getLabelLenth(), getLabelTab(),
			getBlockLength(), getBlocksPerLine(), getFontSize(),
			isShowIndexes(), isShowUpperTracks(), isShowBottomTracks());
	}
}
