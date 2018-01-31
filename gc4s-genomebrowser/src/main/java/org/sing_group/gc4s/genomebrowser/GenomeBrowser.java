/*
 * #%L
 * GC4S genome browser
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
package org.sing_group.gc4s.genomebrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sing_group.gc4s.event.EnterKeyPressedListener;
import org.sing_group.gc4s.genomebrowser.grid.Coordinates;
import org.sing_group.gc4s.genomebrowser.painter.Painter;
import org.sing_group.gc4s.genomebrowser.painter.PainterFactory;
import org.sing_group.gc4s.ui.icons.Icons;

import es.cnio.bioinfo.pileline.refgenomeindex.GenomeIndex;

/**
 * Allows user to interactively visualize a reference genome, with the 
 * possibility of adding and customizing different tracks.
 * 
 * @author hlfernandez
 *
 */
public class GenomeBrowser extends JComponent {
	private static final long serialVersionUID = 1L;

	private static HashMap<GenomeIndex, GenomeBrowserState> states = 
		new HashMap<GenomeIndex, GenomeBrowserState>();

	private JPanel sequenceAndRangeSelectionPanel;
	private JToolBar toolbar;
	private ToolTipPane toolTipsPane;
	private DraggingPane draggingPane;
	private TracksPanel rightPanel;
	private GenomeIndex genomeIndex;
	private JScrollPane scroll;
	private String[] zooms;
	private JTextField initialPositionText;
	private JTextField finalPositionText;
	private JComboBox<String> zoomCB;
	private JButton seekButton;
	private JButton moreZoomButton;
	private JButton lessZoomButton;
	private JButton resetZoomButton;
	private JComboBox<String> sequencesCB;
	private JButton moveToRight2;
	private JButton moveToRight1;
	private JButton moveToLeft1;
	private JButton moveToLeft2;
	private JButton optionTrackButton; 
	private JButton fileSelectionButton; 
	private JButton removeTrackButton;
	private JFileChooser fileChooser;
	private long initialPosition;
	private long finalPosition;
	private String currentSequence;
	private LinkedList<File> files;
	private LinkedList<Painter> painters = new LinkedList<Painter>();
	private HashMap<Painter,File> painterToFile = new HashMap<Painter,File>();
	private HashMap<File,Painter> fileToPainter = new HashMap<File,Painter>();
	private File nextFile = null;
	private JLayeredPane layeredPane;
	private long MAX;
	private GenomeBrowserState STATE;
	private boolean resumeState = false;
	private TrackFileProvider trackFileProvider;

	/**
	 * Creates a new {@code GenomeBrowser} instance to visualize the reference 
	 * genome provided by the specified {@code GenomeIndex}.
	 * 
	 * @param g a {@code GenomeIndex}
	 */
	public GenomeBrowser(GenomeIndex g) {
		this.genomeIndex = g;
		this.files = new LinkedList<File>();
		
		this.setTrackFileProvider(new TrackFileProvider() {
			@Override
			public File getTrackFile() {
				int seleccion = fileChooser.showOpenDialog(null);
				fileChooser.setFileFilter(new GPFilter());
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					return fileChooser.getSelectedFile();

				} else {
					return null;
				}
			}
		});
		this.initialize();
		this.repaintGenomeBrowser();
	}

	private void initialize() {
		STATE = GenomeBrowser.states.get(genomeIndex);
		if (STATE == null) {
			STATE = new GenomeBrowserState();
			GenomeBrowser.states.put(genomeIndex, STATE);
		}
		if (STATE.getChr() != "null" && STATE.getInitialPosition() != -1
			&& STATE.getFinalPosition() != -1) {
			resumeState = true;
			this.resumeStatus();
		}

		this.setSize(1000, 600);
		this.setMinimumSize(new Dimension(400, 200));
		this.setLayout(new BorderLayout());

		int height = GenomeBrowserUtil.computeHeight(this);

		this.sequenceAndRangeSelectionPanel = createSequenceAndRangeSelectionPanel();
		this.toolbar = createToolbar();
		this.rightPanel = new TracksPanel(this);

		this.layeredPane = new JLayeredPane();

		this.scroll = new JScrollPane((Component) this.layeredPane);
		this.scroll.setVisible(true);
		this.scroll.setPreferredSize(new Dimension(1000, 500));
		this.scroll.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.scroll.getViewport().addChangeListener(new ChangeListener() {

			int previousWidth;

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (((JViewport) arg0.getSource())
					.getWidth() != previousWidth) {
					this.previousWidth = ((JViewport) arg0.getSource()).getWidth();
					GenomeBrowser.this.repaintGenomeBrowser();
				}
			}

		});

		this.toolTipsPane = new ToolTipPane(this);
		this.toolTipsPane.setOpaque(false);
		this.toolTipsPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.toolTipsPane.setBounds(0, 0, 1182, height);
		this.draggingPane = new DraggingPane();
		this.draggingPane.setOpaque(false);
		this.draggingPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.draggingPane.setBounds(0, 0, 1182, height);
		this.layeredPane.setPreferredSize(scroll.getPreferredSize());
		this.rightPanel.setBounds(0, 0, 1182, height);
		this.layeredPane.add(rightPanel, new Integer(0));
		this.layeredPane.add(toolTipsPane, new Integer(1));
		this.layeredPane.add(draggingPane, new Integer(2));
		
		JPanel tools = new JPanel();
		tools.setLayout(new BoxLayout(tools, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		tools.add(this.toolbar);
		tools.add(this.sequenceAndRangeSelectionPanel);
		this.add(tools, BorderLayout.NORTH);
		this.add(this.scroll, BorderLayout.CENTER);

		this.rightPanel
			.setPreferredSize(new Dimension(rightPanel.getWidth(), 500));
		this.rightPanel.revalidate();

		if (this.resumeState) {
			this.resumeState = false;
		}
	}

	private void setTrackFileProvider(TrackFileProvider provider) {
		this.trackFileProvider = provider;
	}

	private JPanel createSequenceAndRangeSelectionPanel() {
		JPanel chrSeekPane = new JPanel();
		chrSeekPane.setLayout(new BoxLayout(chrSeekPane, BoxLayout.X_AXIS));

		JPanel chrPanel = new JPanel();
		chrPanel.setLayout(new BoxLayout(chrPanel, BoxLayout.X_AXIS));

		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.X_AXIS));

		JPanel rightPositionPanel = new JPanel();
		rightPositionPanel
			.setLayout(new BoxLayout(rightPositionPanel, BoxLayout.X_AXIS));
		JPanel initialPositionPanel = new JPanel();
		initialPositionPanel
			.setLayout(new BoxLayout(initialPositionPanel, BoxLayout.X_AXIS));
		JPanel finalPositionPanel = new JPanel();
		finalPositionPanel
			.setLayout(new BoxLayout(finalPositionPanel, BoxLayout.X_AXIS));

		positionPanel.add(rightPositionPanel);

		JPanel seekPane = new JPanel();
		seekPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		seekPane.setLayout(new BoxLayout(seekPane, BoxLayout.X_AXIS));

		JLabel chrLabel = new JLabel("Chromosome: ");
		Set<String> sequences = genomeIndex.getSequences();
		String sequencesArray[] = new String[sequences.size()];
		Vector<String> sequencesVector = new Vector<String>();
		Iterator<String> it = sequences.iterator();
		while (it.hasNext()) {
			sequencesVector.add(it.next());
		}
		
		Collections.sort(sequencesVector, new Comparator<String>() {

			@Override
			public int compare(String arg0, String arg1) {
				try {
					Integer arg0Int = Integer.valueOf(arg0.replace("chr", ""));
					try {
						Integer arg1Int = Integer
							.valueOf(arg1.replace("chr", ""));
						return arg0Int.compareTo(arg1Int);
					} catch (NumberFormatException ex1) {
						return -1;
					}
				} catch (NumberFormatException ex2) {
					try {
						Integer.valueOf(arg1.replace("chr", ""));
						return 1;
					} catch (NumberFormatException ex3) {
						return arg0.compareTo(arg1);
					}
				}
			}
		});

		sequencesArray = new String[sequences.size()];
		int i = 0;
		for (String s : sequencesVector) {
			sequencesArray[i++] = s;
		}

		sequencesCB = new JComboBox<String>(sequencesArray);
		sequencesCB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setSequence((String) sequencesCB.getSelectedItem());
				}
			}
		});

		chrPanel.add(chrLabel);
		chrPanel.add(sequencesCB);
		chrSeekPane.add(chrPanel);

		seekPane.add(chrSeekPane);

		JLabel initialPositionLabel = new JLabel("Initial position: ");
		initialPositionText = new JTextField(10);

		initialPositionPanel.add(initialPositionLabel);
		initialPositionPanel.add(initialPositionText);

		JLabel finalPositionLabel=new JLabel("Final  position: ");
		
		finalPositionText = new JTextField(10);
		
		finalPositionPanel.add(finalPositionLabel);
		finalPositionPanel.add(finalPositionText);
		
		rightPositionPanel.add(Box.createHorizontalStrut(5));
		rightPositionPanel.add(initialPositionPanel);
		rightPositionPanel.add(Box.createHorizontalStrut(5));
		rightPositionPanel.add(finalPositionPanel);
		rightPositionPanel.add(Box.createHorizontalStrut(5));
		
		seekPane.add(Box.createRigidArea(new Dimension(3, 0)));
		seekPane.add(rightPositionPanel);

		if (resumeState) {
			currentSequence = STATE.getChr();

			sequencesCB.setSelectedItem(currentSequence);

			MAX = genomeIndex
				.getSequenceLength((String) sequencesCB.getSelectedItem());
			currentSequence = STATE.getChr();
			initialPosition = STATE.getInitialPosition();
			finalPosition = STATE.getFinalPosition();

			initialPositionText.setText(Long.toString(initialPosition));
			finalPositionText.setText(Long.toString(finalPosition - 1));

		} else {
			initialPosition = 1;
			finalPosition = genomeIndex
				.getSequenceLength((String) sequencesCB.getSelectedItem());
			MAX = finalPosition;
			currentSequence = (String) sequencesCB.getSelectedItem();
			initialPositionText.setText(Long.toString(initialPosition));
			finalPositionText.setText(Long.toString(finalPosition));
		}

		KeyListener kL = new EnterKeyPressedListener(this::seekButtonAction);
		
		initialPositionText.addKeyListener(kL);
		finalPositionText.addKeyListener(kL);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		seekButton = new JButton("Seek", Icons.ICON_FIND_2_16);

		seekButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				seekButtonAction();
			}
		});

		buttonsPanel.add(seekButton);
		seekPane.add(buttonsPanel);

		return seekPane;
	}

	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		
		fileSelectionButton = new JButton("Add track", Icons.ICON_ADD_16);

		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new GPFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);

		fileSelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {

				File f = trackFileProvider.getTrackFile();
				if (f != null) {
					GenomeBrowser.this.nextFile = f;
					GenomeBrowser.this.repaintGenomeBrowser();
				} else {
					return;
				}

			}

		});
		
		removeTrackButton = new JButton("Remove track", Icons.ICON_CANCEL_16);

		removeTrackButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				RemoveTracksDialog dialogo = new RemoveTracksDialog(
					GenomeBrowser.this);
				dialogo.setVisible(true);
				GenomeBrowser.this.repaintGenomeBrowser();
			}
		});

		optionTrackButton = new JButton("Options", Icons.ICON_EDIT_16);

		optionTrackButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				SortTracksDialog dialog = new SortTracksDialog(
					GenomeBrowser.this);
				dialog.setVisible(true);
				if (dialog.getStatus() == SortTracksDialog.STATUS_ACCEPT)
					GenomeBrowser.this.repaintGenomeBrowser();
			}
		});

		toolbar.add(fileSelectionButton);
		toolbar.add(removeTrackButton);
		toolbar.add(optionTrackButton);
		toolbar.addSeparator();
		toolbar.addSeparator();

		removeTrackButton.setEnabled(this.files.size() > 0);
		optionTrackButton.setEnabled(this.files.size() > 0);
		
		moreZoomButton = new JButton("Zoom in", Icons.ICON_ZOOM_IN_16);

		moreZoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				moreZoom(zoomCB);
			}
		});

		toolbar.add(moreZoomButton);

		lessZoomButton= new JButton("Zoom out", Icons.ICON_ZOOM_OUT_16);
		
		lessZoomButton.addActionListener(new ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e){
				lessZoom(zoomCB);
			}
		});
		toolbar.add(lessZoomButton);

		zooms = new String[3];
		
		zooms[0] = "1.5x";
		zooms[1] = "3x";
		zooms[2] = "10x";
		
		zoomCB = new JComboBox<>(zooms);
		zoomCB.setMaximumSize(new Dimension(50, (int) zoomCB.getPreferredSize().getHeight()));
		
		resetZoomButton = new JButton("Reset zoom", Icons.ICON_REFRESH_3_16);
		resetZoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialPosition = 1;
				finalPosition = genomeIndex.getSequenceLength((String) sequencesCB.getSelectedItem());
				initialPositionText.setText(Long.toString(initialPosition));
				finalPositionText.setText(Long.toString(finalPosition));
				GenomeBrowser.this.rightPanel.clearHistogramIntervalsHashMap();
				GenomeBrowser.this.repaintGenomeBrowser();
			}
		});
		
		toolbar.addSeparator();
		toolbar.add(zoomCB);
		toolbar.addSeparator();
		toolbar.add(resetZoomButton);
		toolbar.addSeparator();
		toolbar.addSeparator();
		
		moveToLeft1 = new JButton("10%", Icons.ICON_ARROW_LEFT_16);
		moveToLeft2 = new JButton("30%", Icons.ICON_ARROWS_LEFT_16);

		moveToRight1 = new JButton("10%", Icons.ICON_ARROW_RIGHT_16);
		moveToRight2 = new JButton("30%", Icons.ICON_ARROWS_RIGHT_16);

		moveListener mL = new moveListener();
		
		moveToLeft1.addActionListener(mL);
		moveToLeft2.addActionListener(mL);
		moveToRight1.addActionListener(mL);
		moveToRight2.addActionListener(mL);
		
		moveToLeft1.setToolTipText("Move 10% to left");
		moveToRight1.setToolTipText("Move 10% to right");
		moveToLeft2.setToolTipText("Move 30% to left");
		moveToRight2.setToolTipText("Move 30% to right");
		
		toolbar.add(moveToLeft2);
		toolbar.add(moveToLeft1);
		toolbar.add(moveToRight1);
		toolbar.add(moveToRight2);
		toolbar.addSeparator();
		
		JButton exportAsPngButton = new JButton("Export", Icons.ICON_IMAGE_16);
		exportAsPngButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GenomeBrowserUtil.saveAsPNG(GenomeBrowser.this.rightPanel);
              }
  		});
		
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(exportAsPngButton);

		return toolbar;
	}

	private void moreZoom(JComboBox<String> c) {
		String newZoom = zooms[c.getSelectedIndex()];

		double factor = 0.0;

		if (newZoom.startsWith("1.5"))
			factor = 1.5;
		else if (newZoom.startsWith("3"))
			factor = 3;
		else if (newZoom.startsWith("10"))
			factor = 10;

		long visibleBases = this.finalPosition - this.initialPosition + 1;
		long newVisibleBases = (long) (visibleBases / factor);

		if (newVisibleBases > 0) {
			long difference = visibleBases - newVisibleBases;
			difference = difference / 2;

			this.initialPosition = this.initialPosition + difference;
			this.finalPosition = this.finalPosition - difference;

			this.initialPositionText
				.setText(String.valueOf(this.initialPosition));
			this.finalPositionText.setText(String.valueOf(this.finalPosition));

			this.rightPanel.clearHistogramIntervalsHashMap();
			this.repaintGenomeBrowser();
		}
	}

	private void lessZoom(JComboBox<String> c) {
		String newZoom = zooms[c.getSelectedIndex()];

		double factor = 0.0;

		if (newZoom.startsWith("1.5"))
			factor = 1.5;
		else if (newZoom.startsWith("3"))
			factor = 3;
		else if (newZoom.startsWith("10"))
			factor = 10;

		long visibleBases = this.finalPosition - this.initialPosition + 1;
		long newVisibleBases = (long) (visibleBases * factor);

		long difference = newVisibleBases - visibleBases;
		difference = difference / 2;

		this.initialPosition = this.initialPosition - difference;

		if (this.initialPosition < 1) {
			this.finalPosition = this.finalPosition + difference
				+ (this.initialPosition * -1);
			this.initialPosition = 1;
		}

		this.finalPosition = this.finalPosition + difference;

		long tope = this.genomeIndex
			.getSequenceLength((String) this.sequencesCB.getSelectedItem());

		if (this.finalPosition > tope) {
			this.finalPosition = tope;
		}

		this.initialPositionText.setText(String.valueOf(this.initialPosition));
		this.finalPositionText.setText(String.valueOf(this.finalPosition));

		this.rightPanel.clearHistogramIntervalsHashMap();
		this.repaintGenomeBrowser();

	}

	public void seekButtonAction() {
		long start;
		try {
			start = Long.parseLong(initialPositionText.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Initial position not valid",
				"Value error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		long end;
		try {
			end = Long.parseLong(finalPositionText.getText()) + 1;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Final position not valid",
				"Value error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (start > end) {
			JOptionPane.showMessageDialog(this,
				"Initial position could'nt be greater than final position",
				"Value error", JOptionPane.ERROR_MESSAGE);
		} else {

			if (start != initialPosition || end != finalPosition) {
				rightPanel.clearHistogramIntervalsHashMap();
			}

			initialPosition = start;
			finalPosition = end;
			currentSequence = (String) sequencesCB.getSelectedItem();

			this.repaintGenomeBrowser();
		}
	}
	
	private class moveListener implements ActionListener {
		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JButton source = (JButton) e.getSource();
			int divisor;
			int direction = 0;

			if (source.equals(moveToLeft1)) {
				divisor = 10;
				direction = 1;
			} else if (source.equals(moveToLeft2)) {
				divisor = 30;
				direction = 1;
			} else if (source.equals(moveToRight1)) {
				divisor = 10;
			} else
				divisor = 30;

			long visibleBases = finalPosition - initialPosition + 1;
			long displacement = ((visibleBases * divisor) / 100) / 2;

			if (direction == 1) {
				if (initialPosition == 1)
					return;

				initialPosition = initialPosition - displacement;
				if (initialPosition < 1) {
					initialPosition = 1;
					finalPosition = finalPosition - (displacement * 2);
				} else {
					finalPosition = finalPosition - displacement;
				}
			} else {
				if (finalPosition == MAX)
					return;

				finalPosition = finalPosition + displacement;
				if (finalPosition > MAX) {
					finalPosition = MAX;
					initialPosition = initialPosition + (displacement * 2);
				} else {
					initialPosition = initialPosition + displacement;
				}
			}

			GenomeBrowser.this.initialPositionText
				.setText(String.valueOf(GenomeBrowser.this.initialPosition));
			GenomeBrowser.this.finalPositionText
				.setText(String.valueOf(GenomeBrowser.this.finalPosition));
			GenomeBrowser.this.rightPanel.clearHistogramIntervalsHashMap();
			GenomeBrowser.this.repaintGenomeBrowser();
		}
	}

	/**
	 * Tells the genome browser to repaint.
	 * 
	 */
	public void repaintGenomeBrowser() {
		toolTipsPane.enableWait();
		new Thread() {
			public void run() {

				rightPanel.setChanged();
				if (nextFile != null) {
					createPainter(GenomeBrowser.this.nextFile);
				}

				rightPanel.updateDoubleBuffer();
				saveStatus();
				rightPanel.repaint();
				toolTipsPane.disableWait();

			}
		}.start();
	}

	private void saveStatus() {
		STATE.saveStatus(this);
	}

	/**
	 * Returns the current {@code GenomeBrowserState}.
	 * 
	 * @return the current {@code GenomeBrowserState}
	 */
	public GenomeBrowserState getStatus() {
		return this.STATE;
	}

	/**
	 * Centers the visualization range at the specified coordinates.
	 *  
	 * @param c the {@code Coordinates} to use as reference for centering the 
	 * 			range.
	 *  
	 */
	public void centerAtCoordinate(Coordinates c) {
		long positionToCenter = (long) ((double) (((this.finalPosition
			- this.initialPosition) * (c.getX() - rightPanel.getX(125))))
			/ (double) (rightPanel.getX(875) - rightPanel.getX(125)));
		positionToCenter += this.initialPosition;

		long visibleBases = finalPosition - initialPosition + 1;

		long difference = visibleBases;
		difference = difference / 2;

		this.initialPosition = positionToCenter - difference;
		this.finalPosition = positionToCenter + difference;

		if (this.initialPosition < 1) {
			this.initialPosition = 1;
			this.finalPosition += (this.initialPosition * -1);
		}

		if (this.finalPosition > this.MAX) {
			this.initialPosition -= (this.finalPosition - MAX);
			this.finalPosition = MAX;
		}

		this.initialPositionText.setText(String.valueOf(this.initialPosition));
		this.finalPositionText.setText(String.valueOf(this.finalPosition));
		this.repaintGenomeBrowser();
	}
	
	/**
	 * Returns the available sequences.
	 * 
	 * @return the available sequences
	 */
	public Set<String> getSequences() {
		return this.genomeIndex.getSequences();
	}
	
	/**
	 * Returns the current sequence.
	 * 
	 * @return the current sequence
	 */
	public String getCurrentSequence() {
		return this.currentSequence;
	}

	/**
	 * Sets the active sequence. It must be present in the list of available
	 * sequences ({@link GenomeBrowser#getSequences()}).
	 * 
	 * @param sequence the sequence to visualize
	 */
	public void setSequence(String sequence) {
		if (!sequence.equals(currentSequence)
			&& getSequences().contains(sequence)
		) {
			currentSequence = sequence;
			initialPosition = 1;
			finalPosition = genomeIndex.getSequenceLength(currentSequence);
			MAX = finalPosition;
			initialPositionText.setText(Long.toString(1));
			finalPositionText.setText(Long.toString(finalPosition));
			this.sequencesCB.setSelectedItem(sequence);
			
			GenomeBrowser.this.rightPanel.clearHistogramIntervalsHashMap();
			GenomeBrowser.this.repaintGenomeBrowser();
		}
	}

	/**
	 * Sets the visualization range to the one obtained by the specified
	 * component graphical coordinates.
	 * 
	 * @param xStart the range start
	 * @param xStop the range end
	 */
	public void seekCoordinates(int xStart, int xStop) {
		long newInitialPosition = (long) ((double) (((this.finalPosition
			- this.initialPosition) * (xStart - this.rightPanel.getX(125))))
			/ (double) (this.rightPanel.getX(875) - this.rightPanel.getX(125)));
		newInitialPosition += this.initialPosition;

		long newFinalPosition = (long) ((double) (((this.finalPosition
			- this.initialPosition) * (xStop - this.rightPanel.getX(125))))
			/ (double) (this.rightPanel.getX(875) - this.rightPanel.getX(125)));
		newFinalPosition += this.initialPosition;

		if (newInitialPosition < 0) {
			newInitialPosition = this.initialPosition;
		}
		if (newFinalPosition < 0) {
			newFinalPosition = this.finalPosition;
		}

		seekPositions(newInitialPosition, newFinalPosition);
		
	}

	/**
	 * Sets the visualization range to the one obtained by the specified genomic
	 * positions.
	 * 
	 * @param newInitialPosition the range start
	 * @param newFinalPosition the range end
	 */
	public void seekPositions(long newInitialPosition, long newFinalPosition) {
		this.initialPosition = newInitialPosition;
		this.finalPosition = newFinalPosition;
		this.initialPositionText.setText(String.valueOf(this.initialPosition));
		this.finalPositionText.setText(String.valueOf(this.finalPosition));

		this.repaintGenomeBrowser();
	}

	private void createPainter(File f) {
		Painter newPainter = PainterFactory.getPainter(f);

		this.painters.add(newPainter);
		this.files.add(f);
		this.painterToFile.put(newPainter, f);
		this.fileToPainter.put(f, newPainter);

		try {
			newPainter.init(this);
		} catch (RuntimeException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning",
				JOptionPane.WARNING_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid file",
				JOptionPane.ERROR_MESSAGE);
			this.removePainter(newPainter);
		}

		this.nextFile = null;
	}

	private void resumeStatus() {
		LinkedList<Painter> statusPainters = STATE.getPainters();
		LinkedList<File> statusFiles = STATE.getFiles();
		this.files = new LinkedList<File>();
		if (statusPainters != null) {
			for (int i = 0; i < statusFiles.size(); i++) {
				Painter p = statusPainters.get(i);
				File f = statusFiles.get(i);
				p.setOptions(STATE.getTrackOptions(p));

				this.painters.add(p);
				this.files.add(f);
				this.painterToFile.put(p, f);
				this.fileToPainter.put(f, p);
			}
		}
	}

	/**
	 * Changes the cursor.
	 * 
	 * @param cursor the new cursor.
	 */
	public void changeCursor(int cursor) {
		this.setCursor(Cursor.getPredefinedCursor(cursor));
	}

	public String toString() {
		return "Viewing chromosome " + this.currentSequence + " between "
			+ this.initialPosition + " and " + this.finalPosition;
	}

	/**
	 * Returns the actual initial position being visualized.
	 * 
	 * @return the actual initial position being visualized
	 */
	public long getInitialPosition() {
		return initialPosition;
	}

	/**
	 * Returns the the actual final position being visualized.
	 * 
	 * @return the the actual final position being visualized
	 */
	public long getFinalPosition() {
		return finalPosition;
	}

	/**
	 * Removes the specified painter.
	 * 
	 * @param p the painter to remove
	 */
	public void removePainter(Painter p) {
		File toRemove = painterToFile.get(p);
		this.files.remove(toRemove);
		this.painters.remove(p);
		this.painterToFile.remove(p);
		this.fileToPainter.remove(toRemove);
	}

	/**
	 * Removes the painter associated to the specified file.
	 * 
	 * @param f the file to remove its associated painter
	 */
	public void removeFile(File f) {
		Painter toRemove = this.fileToPainter.get(f);
		this.removePainter(toRemove);
	}

	/**
	 * Returns the {@code GenomeIndex}.
	 * 
	 * @return the {@code GenomeIndex}
	 */
	public GenomeIndex getGenomeIndex() {
		return this.genomeIndex;
	}

	/**
	 * Returns the {@code ToolTipPane}.
	 * 
	 * @return the {@code ToolTipPane}
	 */
	public ToolTipPane getToolTipsPane() {
		return this.toolTipsPane;
	}

	/**
	 * Returns the {@code DraggingPane}.
	 * 
	 * @return the {@code DraggingPane}
	 */
	public DraggingPane getDraggingPane() {
		return this.draggingPane;
	}

	/**
	 * Returns the {@code TracksPanel}.
	 * 
	 * @return the {@code TracksPanel}
	 */
	public TracksPanel getTracksPanel() {
		return this.rightPanel;
	}

	/**
	 * Returns the {@code JScrollPane}.
	 * 
	 * @return the {@code JScrollPane}
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}
	
	/**
	 * Adds a track associated to the specified file.
	 * 
	 * @param f the file containing the track information
	 */
	public void addTrack(File f) {
		this.nextFile = f;
		this.repaintGenomeBrowser();
	}

	/**
	 * Returns the current list of {@code Painter}s.
	 * 
	 * @return the current list of {@code Painter}s
	 */
	public List<Painter> getPainters() {
		return this.painters;
	}

	/**
	 * Returns the file associated to the specified painter.
	 * 
	 * @param p a {@code Painter}
	 * 
	 * @return the file associated to the painter
	 */
	public File getPainter(Painter p) {
		return this.painterToFile.get(p);
	}
	
	/**
	 * Returns the current list of file tracks.
	 * 
	 * @return the current list of file tracks.
	 */
	public List<File> getFiles() {
		return files;
	}
	
	/**
	 * Returns the {@code JLayeredPane}.
	 *  
	 * @return the {@code JLayeredPane}
	 */
	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	/**
	 * Enables (or disables) the track options button.
	 * 
	 * @param enabled {@code true} to enable the button, otherwise {@code false}
	 */
	public void setOptionTrackButtonEnabled(boolean enabled) {
		this.optionTrackButton.setEnabled(enabled);
	}

	/**
	 * Enables (or disables) the remove tracks button.
	 * 
	 * @param enabled {@code true} to enable the button, otherwise {@code false}
	 */
	public void setRemoveTrackButtonEnabled(boolean enabled) {
		this.removeTrackButton.setEnabled(enabled);
	}
}