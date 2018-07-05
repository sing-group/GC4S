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

import java.awt.Color;
import java.util.Map;
import java.util.Optional;

import org.sing_group.gc4s.msaviewer.Sequence;
import org.sing_group.gc4s.msaviewer.SequenceAlignmentRenderer;
import org.sing_group.gc4s.msaviewer.SequenceBaseRenderingInfo;
import org.sing_group.gc4s.msaviewer.Track;

public class CodeMlPositiveSelectionSequenceAlignmentRenderer implements SequenceAlignmentRenderer {
	private Color neb95beb95Background = Color.YELLOW;
	private Color neb95beb9095Background = Color.RED;
	private Color neb9095beb95Background = Color.BLUE;
	private Color neb9095beb9095Background = Color.GREEN;

	private Color neb95beb95Foreground = Color.BLACK;
	private Color neb95beb9095Foreground = Color.WHITE;
	private Color neb9095beb95Foreground = Color.WHITE;
	private Color neb9095beb9095Foreground = Color.BLACK;
	
	private SequenceBaseRenderingInfo NEB95_BEB95 = new SequenceBaseRenderingInfo(
		neb95beb95Background, neb95beb95Foreground, false, false);
	private SequenceBaseRenderingInfo NEB95_BEB9095 = new SequenceBaseRenderingInfo(
		neb95beb9095Background, neb95beb9095Foreground, false, false);
	private SequenceBaseRenderingInfo NEB9095_BEB95 = new SequenceBaseRenderingInfo(
		neb9095beb95Background, neb9095beb95Foreground, false, false);
	private SequenceBaseRenderingInfo NEB90_BEB9095 = new SequenceBaseRenderingInfo(
		neb9095beb9095Background, neb9095beb9095Foreground, false, false);

	private Map<Integer, Confidence> alignmentConfidences;
	
	public CodeMlPositiveSelectionSequenceAlignmentRenderer(
		Map<Integer, Confidence> alignmentConfidences) {
		this.alignmentConfidences = alignmentConfidences;
	}

	@Override
	public Optional<SequenceBaseRenderingInfo> render(Sequence sequence,
		int position
	) {
		if(!this.alignmentConfidences.containsKey(position)) {
			return Optional.empty();
		}
		
		Confidence confidence = this.alignmentConfidences.get(position);
		
		if (confidence.getNeb() > 0.95d && confidence.getBeb() > 0.95d) {
			return Optional.of(NEB95_BEB95);
		} else if (confidence.getNeb() > 0.95d && confidence.getBeb() > 0.90d) {
			return Optional.of(NEB95_BEB9095);
		} else if (confidence.getNeb() > 0.90d && confidence.getBeb() > 0.95d) {
			return Optional.of(NEB9095_BEB95);
		} else if (confidence.getNeb() > 0.90d && confidence.getBeb() > 0.90d) {
			return Optional.of(NEB90_BEB9095);
		}

		return Optional.empty();
	}
	
	@Override
	public Optional<SequenceBaseRenderingInfo> renderTrack(Track track,
		int position
	) {		
		return Optional.empty();
	}
}
