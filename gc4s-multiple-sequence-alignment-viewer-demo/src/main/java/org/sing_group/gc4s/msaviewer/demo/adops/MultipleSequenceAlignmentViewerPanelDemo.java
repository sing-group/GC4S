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
package org.sing_group.gc4s.msaviewer.demo.adops;

import static java.util.Arrays.asList;
import static org.sing_group.gc4s.msaviewer.demo.Data.getRandomScores;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentTracksModel;
import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerConfiguration;
import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerPanel;
import org.sing_group.gc4s.msaviewer.SequenceAlignmentRenderer;
import org.sing_group.gc4s.msaviewer.Sequence;
import org.sing_group.gc4s.msaviewer.Track;
import org.sing_group.gc4s.msaviewer.demo.Data;
import org.sing_group.gc4s.visualization.VisualizationUtils;

public class MultipleSequenceAlignmentViewerPanelDemo {
	public static void main(String[] args) {
		int sequenceLength = 120;
		
		MultipleSequenceAlignmentTracksModel model = new MultipleSequenceAlignmentTracksModel() {

			@Override
			public String getName() {
				return "MultipleSequenceAlignmentTracksModel 1";
			}
			
			@Override
			public List<Track> getUpperTracks() {
				return Collections.emptyList();
			}

			@Override
			public List<Track> getBottomTracks() {
				return asList(new Track() {
					
					@Override
					public String getName() {
						return "Scores";
					}
					
					@Override
					public String getContent() {
						return getRandomScores(sequenceLength);
					}
				});
			}
			
		};

		final Map<Integer, Confidence> confidences = new TreeMap<>();
		confidences.put(4, new Confidence(0.99d, 0.99d));
		confidences.put(14, new Confidence(0.99d, 0.99d));
		confidences.put(8, new Confidence(0.99d, 0.92d));
		confidences.put(12, new Confidence(0.92d, 0.99d));
		confidences.put(16, new Confidence(0.92d, 0.92d));
		
		SequenceAlignmentRenderer renderer = new CodeMlPositiveSelectionSequenceAlignmentRenderer(confidences);

		MultipleSequenceAlignmentViewerConfiguration configuration = new MultipleSequenceAlignmentViewerConfiguration(10, 5, 10, 4, 16, true, true, true);
	
		List<Sequence> sequences = Data.getRandomSequences(6, sequenceLength);
		
		VisualizationUtils.showComponent(
			new MultipleSequenceAlignmentViewerPanel(sequences, model, renderer, configuration), 
			JFrame.MAXIMIZED_BOTH
		);
	}
}
