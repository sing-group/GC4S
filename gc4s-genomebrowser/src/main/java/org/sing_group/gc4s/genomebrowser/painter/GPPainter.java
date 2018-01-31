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
package org.sing_group.gc4s.genomebrowser.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.sing_group.gc4s.genomebrowser.GenomeBrowser;
import org.sing_group.gc4s.genomebrowser.GenomeBrowserUtil;
import org.sing_group.gc4s.genomebrowser.TrackOption;
import org.sing_group.gc4s.genomebrowser.grid.GenericInfo;
import org.sing_group.gc4s.genomebrowser.grid.GridInfo;
import org.sing_group.gc4s.genomebrowser.grid.TrackPositionInfo;

import es.cnio.bioinfo.pileline.core.FastSeeker;
import es.cnio.bioinfo.pileline.core.FastSeekerFactory;

/**
 * A {@code Painter} implementation to render generic genomic position files.
 * 
 * @author hlfernandez
 *
 */
public class GPPainter implements Painter {
	protected File file;
	protected FastSeeker seek;
	protected Color trackColor;
	protected int trackHeight = 65;
	protected Color backGroundColor = null;
	protected int columnToDisplay = 0;
	protected Color customColor = null;
	protected Collection<TrackOption> options;
	protected int offset;
	protected int histogramIntervals;
	protected int maxHistogramValue;
	protected Vector<Integer> tmpValues = new Vector<Integer>();
	protected String trackName = "default";

	/**
	 * Creates a new {@code GPPainter} for the specified track file.
	 * 
	 * @param file a genomic position file
	 */
	public GPPainter(File file) {
		this.file = file;
		initializeOptions();
	}

	@Override
	public void init(GenomeBrowser genomeBrowser)
		throws RuntimeException, IOException {
		this.seek = FastSeekerFactory.createFastSeeker(this.file);

		Set<String> fileSequences = this.seek.getSequences();
		Set<String> genomeSequences = genomeBrowser.getGenomeIndex()
			.getSequences();
		HashSet<String> intersect = new HashSet<String>();
		for (String s : fileSequences) {
			if (genomeSequences.contains(s))
				intersect.add(s);
		}
		double intersectValue = (double) intersect.size()
			/ (double) (fileSequences.size());
		if (intersectValue < 0.5) {
			throw new RuntimeException("Sequences in genome and track ("
				+ this.file.getName() + ") have poor overlapping.");
		}
	}

	@Override
	public int computeHeight(GenomeBrowser genomeBrowser) {
		return trackHeight;
	}

	@Override
	public Collection<TrackOption> getOptions() {
		return this.options;
	}

	@Override
	public void paint(Graphics2D g2, GenomeBrowser genomeBrowser, int offset) {
		if (customColor != null)
			this.trackColor = customColor;
		this.fillOptions();
		this.offset = offset;
		GenomeBrowserUtil.drawString(g2, this.getTrackName(), trackHeight - 5,
			genomeBrowser.getTracksPanel(), offset);
		double drawNucleotides = genomeBrowser.getTracksPanel()
			.drawNucleotides(g2);
		boolean histogram = true;
		if (drawNucleotides > 0.0f)
			histogram = false;
		if (histogram) {
			this.renderHistogram(g2, this.file, genomeBrowser);
		} else {
			this.renderColumns(g2, this.file, 0, this.trackColor,
				genomeBrowser);
		}

	}
	
	private void renderHistogram(Graphics2D g2, File current, GenomeBrowser genomeBrowser) {
		g2.setColor(this.trackColor);
		FontMetrics fm = g2.getFontMetrics();
		int trackPosition = trackHeight;
		g2.setStroke(new BasicStroke(1f));
		Line2D line = new Line2D.Double(genomeBrowser.getTracksPanel().getX(125),
			trackPosition - 1, genomeBrowser.getTracksPanel().getX(875),
			trackPosition - 1);
		g2.draw(line);
		int maxValue = 0;

		int size = this.histogramIntervals;

		if (tmpValues.isEmpty()) {
			tmpValues = new Vector<Integer>(size);
			Iterator<String> it;
			long intervalStart = genomeBrowser.getInitialPosition();
			long histogramIntervalSize = (genomeBrowser.getFinalPosition()
				- genomeBrowser.getInitialPosition()) / size;
			long intervalEnd = genomeBrowser.getInitialPosition() + histogramIntervalSize;
			int sum = 0;
			try {

				for (int c = 0; c < size; c++) {

					it = this.seek.seek(genomeBrowser.getCurrentSequence(),
						(int) intervalStart, (int) intervalEnd);
					while (it.hasNext()) {
						it.next();
						sum++;
					}
					tmpValues.add(sum);
					if (sum > maxValue)
						maxValue = sum;
					sum = 0;
					intervalStart += histogramIntervalSize;
					intervalEnd += histogramIntervalSize;
					if (intervalEnd > genomeBrowser.getFinalPosition())
						intervalEnd = genomeBrowser.getFinalPosition();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		Color barsColor = this.trackColor;
		maxValue = GenomeBrowserUtil.getMaxIntegerValue(tmpValues);
		if (maxValue > 0) {
			double unitHeight;
			int limit;
			if (this.maxHistogramValue > 0)
				limit = this.maxHistogramValue;
			else
				limit = maxValue;
			double histogramHeight = trackHeight * 0.95;
			if (this.maxHistogramValue > 0)
				unitHeight = (double) histogramHeight
					/ (double) this.maxHistogramValue;
			else
				unitHeight = (double) histogramHeight / (double) maxValue;
			double intervalWidth = (genomeBrowser.getTracksPanel().getWidth() * 0.75)
				/ size;
			double initialXPos = genomeBrowser.getTracksPanel().getX(125);
			Color oldColor = g2.getColor();
			int total = 0;
			for (Integer x : tmpValues) {
				total += x;
				Rectangle2D rectangle;
				Area a;
				if (x > limit) {
					rectangle = new Rectangle2D.Float((float)initialXPos, (float)(trackPosition-unitHeight*limit), (float)intervalWidth, (float)unitHeight*limit);
					a = new Area(rectangle);
					g2.setColor(Color.LIGHT_GRAY);
					g2.fill(a);
					g2.setColor(Color.BLACK);
					double middle = (intervalWidth / 2);
					Line2D notComplete = new Line2D.Float(
						(float) (initialXPos + middle - 1),
						(float) (trackPosition - unitHeight * limit - 2),
						(float) (initialXPos + 4 + middle - 1),
						(float) (trackPosition - unitHeight * limit + 5));
					g2.draw(notComplete);
					notComplete = new Line2D.Float(
						(float) (initialXPos + middle + 1),
						(float) (trackPosition - unitHeight * limit - 2),
						(float) (initialXPos + 4 + middle + 1),
						(float) (trackPosition - unitHeight * limit + 5));
					g2.draw(notComplete);
					g2.setColor(barsColor);
				} else {
					rectangle = new Rectangle2D.Float((float) initialXPos,
						(float) (trackPosition - unitHeight * x),
						(float) intervalWidth, (float) unitHeight * x);
					g2.draw(rectangle);
					a = new Area(rectangle);
					g2.fill(a);

				}
				String count = genomeBrowser.getTracksPanel().getFormatter()
					.format(x);
				if (fm.stringWidth(count) + 4 <= intervalWidth && x != 0) {
					g2.setColor(GenomeBrowserUtil.LIGHT_BLACK);
					double middle = (intervalWidth / 2)
						- (fm.stringWidth(count) / 2);
					g2.drawString(Integer.toString(x),
						(int) (initialXPos + middle), trackPosition);
					g2.setColor(oldColor);
				} else {
					if (x != 0) {
						GenericInfo data = new TrackPositionInfo(x, oldColor);
						GridInfo cI = new GridInfo(data, GridInfo.TRACKINFO);
						genomeBrowser.getTracksPanel().addCuadriculaInfo(
							(int) (initialXPos),
							genomeBrowser.getTracksPanel().getY(offset - 4), cI);
					}
				}

				initialXPos += intervalWidth;
			}
			g2.setColor(GenomeBrowserUtil.LIGHT_BLACK);
			String max = "";
			if (genomeBrowser.getTracksPanel().getMaxHistogramValue() == 0)
				max = genomeBrowser.getTracksPanel().getFormatter().format(maxValue);
			else
				max = genomeBrowser.getTracksPanel().getFormatter()
					.format(genomeBrowser.getTracksPanel().getMaxHistogramValue());
			if (this.maxHistogramValue == 0)
				max = genomeBrowser.getTracksPanel().getFormatter().format(maxValue);
			else
				max = genomeBrowser.getTracksPanel().getFormatter()
					.format(this.maxHistogramValue);
			g2.drawString(
				genomeBrowser.getTracksPanel().getFormatter().format(total)
					+ " ocurrences",
				(int) genomeBrowser.getTracksPanel().getX(875) + 10,
				trackPosition - 5);
			g2.drawString("Max = " + max,
				(int) genomeBrowser.getTracksPanel().getX(875) + 2,
				(int) (trackPosition - histogramHeight + 15));
		} else {
			g2.setColor(GenomeBrowserUtil.LIGHT_BLACK);
			g2.drawString("0 ocurrences",
				(int) genomeBrowser.getTracksPanel().getX(875) + 2,
				trackPosition - 5);
		}

	}

	private void renderColumns(Graphics2D g2, File current, int i,
		Color currentColor, GenomeBrowser gv) {
		double width = (gv.getTracksPanel().getWidth() * 0.75)
			/ (gv.getFinalPosition() - gv.getInitialPosition());
		FontMetrics fm = g2.getFontMetrics();
		int trackPosition = this.trackHeight;
		boolean displayColums = this.columnToDisplay > 1;

		try {
			Iterator<String> it = this.seek.seek(gv.getCurrentSequence(),
				(int) gv.getInitialPosition(), (int) gv.getFinalPosition() - 1);

			while (it.hasNext()) {

				String tmp = it.next();
				StringTokenizer tokenizer = new StringTokenizer(tmp);
				String genomePosition = "0";
				try {
					tokenizer.nextToken();
					genomePosition = tokenizer.nextToken();
				} catch (Exception e) {
					e.printStackTrace();
				}

				double xCoordinate = computeTrackLinePosition(
					Long.valueOf(genomePosition), width,
					gv.getInitialPosition());

				String columValue = this.parseLine(tmp, this.columnToDisplay);

				Color nucleotidColor = currentColor;
				if (!displayColums)
					nucleotidColor = this.trackColor;
				else if (this.columnToDisplay == 2 || this.columnToDisplay == 3)
					nucleotidColor = GenomeBrowserUtil.getColors().get(columValue);
				if (nucleotidColor == null)
					nucleotidColor = this.trackColor;
				g2.setColor(nucleotidColor);

				Rectangle2D rectangle = new Rectangle2D.Float(
					(float) ((float) gv.getTracksPanel().getX(125)
						+ xCoordinate),
					(float) gv.getTracksPanel().getY(trackPosition) - 19,
					(float) width, (float) 16);
				g2.draw(rectangle);
				Area a = new Area(rectangle);
				g2.fill(a);

				if ((displayColums) && (width > fm.stringWidth(columValue))) {

					g2.setColor(Color.DARK_GRAY);
					g2.drawString(columValue,
						(int) (gv.getTracksPanel().getX(125) + xCoordinate
							+ ((width / 2) - (fm.stringWidth(columValue) / 2))),
						gv.getTracksPanel().getY(trackPosition - 7));

				}
				GenericInfo data = new TrackPositionInfo(columValue,
					Long.valueOf(genomePosition), currentColor);
				GridInfo aux = new GridInfo(data, GridInfo.TRACKPOSITION);
				gv.getTracksPanel().addCuadriculaInfo(
					(int) (gv.getTracksPanel().getX(125) + xCoordinate
						+ ((width / 2) - (fm.stringWidth("-") / 2))),
					gv.getTracksPanel().getY(-7 + offset), aux);
				g2.setColor(Color.black);
				g2.setColor(nucleotidColor);

				g2.setColor(currentColor);

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private String parseLine(String tmp, int columnToDisplay) {
		StringTokenizer tokenizer = new StringTokenizer(tmp);
		int i = 0;
		String toret = null;
		while (tokenizer.hasMoreTokens() && i < columnToDisplay) {
			toret = tokenizer.nextToken();
			i++;
		}
		if (i == columnToDisplay && toret != null)
			return toret;
		return "N/A";
	}

	@Override
	public void reset() {
		this.tmpValues.clear();
	}

	@Override
	public void setTrackColor(Color trackColor) {
		this.trackColor = trackColor;
	}

	@Override
	public void setOptions(Collection<TrackOption> options) {
		if (options != null)
			this.options = options;
	}

	@Override
	public Color getBackgroundColor() {
		return this.backGroundColor;
	}

	private void initializeOptions() {
		TrackOption histogramIntervals = new TrackOption() {

			String value = "20";

			@Override
			public String getName() {
				return "Histogram intervals: ";
			}

			@Override
			public Class<?> getType() {
				return String.class;
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (String) value;

			}

		};

		TrackOption maxHistogramValue = new TrackOption() {

			String value = "0";

			@Override
			public String getName() {
				return "Histogram limit: ";
			}

			@Override
			public Class<?> getType() {
				return String.class;
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (String) value;

			}

		};
		TrackOption displayColumn = new TrackOption() {

			String value = "3";

			@Override
			public String getName() {
				return "Display column: ";
			}

			@Override
			public Class<?> getType() {
				return String.class;
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (String) value;

			}

		};

		TrackOption colorChooser = new TrackOption() {

			private Color value = GPPainter.this.trackColor;

			@Override
			public String getName() {

				return "Color: ";
			}

			@Override
			public Class<?> getType() {

				return Color.class;
			}

			@Override
			public Object getValue() {
				return this.value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (Color) value;

			}

		};

		TrackOption backGroundcolorChooser = new TrackOption() {

			private Color value = GPPainter.this.backGroundColor;

			@Override
			public String getName() {

				return "Background Color: ";
			}

			@Override
			public Class<?> getType() {

				return Color.class;
			}

			@Override
			public Object getValue() {
				return this.value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (Color) value;

			}

		};

		TrackOption trackSize = new TrackOption() {

			String value = "65";

			@Override
			public String getName() {
				return "Track height (px): ";
			}

			@Override
			public Class<?> getType() {
				return String.class;
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (String) value;

			}

		};

		TrackOption trackName = new TrackOption() {

			String value = "";

			@Override
			public String getName() {
				return "Track name (empty takes default value): ";
			}

			@Override
			public Class<?> getType() {
				return String.class;
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (String) value;

			}

		};

		this.options = java.util.Arrays.asList(
			new TrackOption[] { displayColumn, histogramIntervals, maxHistogramValue,
				colorChooser, backGroundcolorChooser, trackSize, trackName });
	}

	private void fillOptions() {

		for (TrackOption option : this.options) {
			if (option.getName().startsWith("Display")) {
				try {
					this.columnToDisplay = Integer
						.valueOf((String) option.getValue());
				} catch (NumberFormatException nFE) {
					option.setValue(
						(String) String.valueOf(this.columnToDisplay));
				}
			} else if (option.getName().startsWith("Histogram intervals: ")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.histogramIntervals != newValue)
						this.reset();
					this.histogramIntervals = newValue;
				} catch (NumberFormatException nFE) {
					option.setValue(
						(String) String.valueOf(this.histogramIntervals));
				}
			} else if (option.getName().startsWith("Histogram limit: ")) {
				try {
					this.maxHistogramValue = Integer
						.valueOf((String) option.getValue());
				} catch (NumberFormatException nFE) {
					option.setValue(
						(String) String.valueOf(this.maxHistogramValue));
				}

			} else if (option.getName().startsWith("Color")) {
				if (option.getValue() != null
					&& !option.getValue().equals(this.trackColor)) {
					this.customColor = (Color) option.getValue();
				}
			} else if (option.getName().startsWith("Track height ")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.trackHeight != newValue)
						this.trackHeight = newValue;
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.trackHeight));
				}
			} else if (option.getName().startsWith("Background")) {
				if (option.getValue() != null) {
					this.backGroundColor = (Color) option.getValue();
				}
			} else if (option.getName().contains("Track name")) {
				if (!((String) option.getValue()).equals(""))
					this.trackName = (String) option.getValue();
				else
					this.trackName = "default";
			}
		}
	}

	protected double computeTrackLinePosition(long base, double ancho,
		long initialPosition) {
		long desplazamiento = (base - initialPosition);
		return (desplazamiento * ancho);
	}

	@Override
	public String getTrackName() {
		if (this.trackName.equals("default"))
			return file.getName();
		else
			return this.trackName;
	}

	@Override
	public Color getTrackColor() {
		return this.trackColor;
	}
}
