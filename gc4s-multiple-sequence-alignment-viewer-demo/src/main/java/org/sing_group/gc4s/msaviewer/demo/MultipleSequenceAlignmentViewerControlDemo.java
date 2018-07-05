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
import static org.sing_group.gc4s.msaviewer.demo.Data.getRandomScores;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JFrame;

import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentTracksModel;
import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerConfiguration;
import org.sing_group.gc4s.msaviewer.MultipleSequenceAlignmentViewerControl;
import org.sing_group.gc4s.msaviewer.Sequence;
import org.sing_group.gc4s.msaviewer.SequenceAlignmentRenderer;
import org.sing_group.gc4s.msaviewer.SequenceBaseRenderingInfo;
import org.sing_group.gc4s.msaviewer.Track;
import org.sing_group.gc4s.visualization.VisualizationUtils;

/**
 * This class show the basic usage of the
 * {@code MultipleSequenceAlignmentViewerControl} component.
 * 
 * @author hlfernandez
 *
 */
public class MultipleSequenceAlignmentViewerControlDemo {
	public static void main(String[] args) {
		int sequenceLength = 120;
		
		MultipleSequenceAlignmentTracksModel model1 = 
			new MultipleSequenceAlignmentTracksModel() {
		
				@Override
				public String getName() {
					return "Model 1";
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

		SequenceAlignmentRenderer model1Renderer = new SequenceAlignmentRenderer() {
			private SequenceBaseRenderingInfo RED = 
				new SequenceBaseRenderingInfo(Color.RED, Color.YELLOW, false, false);
			private SequenceBaseRenderingInfo BLUE = 
				new SequenceBaseRenderingInfo(Color.BLUE, Color.GREEN, false, false);
			private SequenceBaseRenderingInfo BOLD = 
				new SequenceBaseRenderingInfo(null, null, true, false);
			
			@Override
			public Optional<SequenceBaseRenderingInfo> renderTrack(Track track,
				int position) {
				return Optional.empty();
			}
			
			@Override
			public Optional<SequenceBaseRenderingInfo> render(Sequence sequence,
				int position) {
				if(Arrays.asList(0, 1, 2, 3, 4).contains(position)) {
					return Optional.of(RED);
				} else if(Arrays.asList(6, 11, 16, 21, 26).contains(position)) {
					return Optional.of(BLUE);
				} else if(Arrays.asList(7, 12, 17, 22, 27, 37, 47, 57).contains(position)) {
					return Optional.of(BOLD);
				} else {
					return Optional.empty();
				}
			}
		};

		MultipleSequenceAlignmentTracksModel model2 = 
			new MultipleSequenceAlignmentTracksModel() {
				
				@Override
				public String getName() {
					return "Model 2";
				}
				
				@Override
				public List<Track> getUpperTracks() {
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
				
				@Override
				public List<Track> getBottomTracks() {
					return Collections.emptyList();
				}
			};

		MultipleSequenceAlignmentViewerConfiguration configuration = 
			new MultipleSequenceAlignmentViewerConfiguration(
				10, 5, 10, 4, 16, true, true, true);

	
		List<Sequence> sequences = Data.getRandomSequences(6, sequenceLength);
		
		Map<MultipleSequenceAlignmentTracksModel, SequenceAlignmentRenderer> renderersMap = new HashMap<>();
		renderersMap.put(model1, model1Renderer);
		
		VisualizationUtils.showComponent(
			new MultipleSequenceAlignmentViewerControl(
				sequences, asList(model1, model2), renderersMap, configuration), 
			JFrame.MAXIMIZED_BOTH
		);
	}
}
