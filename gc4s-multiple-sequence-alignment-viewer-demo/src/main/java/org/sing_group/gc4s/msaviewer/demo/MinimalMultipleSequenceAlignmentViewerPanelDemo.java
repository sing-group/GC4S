/*
 * #%L
 * GC4S multiple sequence alignment viewer demo
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
package org.sing_group.gc4s.msaviewer.demo;

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.util.List;

import javax.swing.JFrame;

import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerConfiguration;
import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerPanel;
import org.sing_group.gc4s.msaviewer.Sequence;

// This class show a very simple example of the usage of the
// MultipleSequenceAlignmentViewerPanel component.
public class MinimalMultipleSequenceAlignmentViewerPanelDemo {
	public static void main(String[] args) {
		
		// Creation of a list with two Sequence objects that will be viewed with
		// the MultipleSequenceAlignmentViewerPanel. Note that all sequences
		// must have the same length.
		List<Sequence> sequences = asList(new Sequence() {

			@Override
			public String getSequence() {
				return "ACTGACTGACTGACTGACTGACTGACTGACT-";
			}

			@Override
			public String getHeader() {
				return "Sequence 1";
			}
		}, new Sequence() {

			@Override
			public String getSequence() {
				return "AC-GAC-GACTGACT-ACTGACTGACTGACTG";
			}

			@Override
			public String getHeader() {
				return "Sequence 2";
			}
		});

		// The MultipleSequenceAlignmentViewerConfiguration used to set the
		// initial configuration of the viewer. This is optional since the
		// viewer panel can create a configuration with default values if it is
		// not provided.
		MultipleSequenceAlignmentViewerConfiguration configuration = 
			new MultipleSequenceAlignmentViewerConfiguration(
				10, 	// The length of the sequence label
				5,		// The number of tabs after the sequence label
				10, 	// The length of each block
				4, 		// The number of blocks per line
				16, 	// The font size
				true, 	// Whether position indexes must be shown or not
				true, 	// Whether upper tracks must be shown or not
				true 	// Whether bottom tracks must be shown or not
		);

		
		// Instantiation of the MultipleSequenceAlignmentViewerPanel using the
		// specified list of sequences and initial configuration.
		MultipleSequenceAlignmentViewerPanel viewerPanel = 
			new MultipleSequenceAlignmentViewerPanel(sequences, configuration);

		showComponent(viewerPanel, JFrame.MAXIMIZED_BOTH);
	}
}
