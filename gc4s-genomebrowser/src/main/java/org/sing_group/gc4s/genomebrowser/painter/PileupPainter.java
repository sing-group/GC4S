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
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.sing_group.gc4s.genomebrowser.GenomeBrowser;
import org.sing_group.gc4s.genomebrowser.GenomeBrowserUtil;
import org.sing_group.gc4s.genomebrowser.TrackOption;
import org.sing_group.gc4s.genomebrowser.grid.GenericInfo;
import org.sing_group.gc4s.genomebrowser.grid.GridInfo;
import org.sing_group.gc4s.genomebrowser.grid.PileupInfo;
import org.sing_group.gc4s.genomebrowser.grid.TrackPositionInfo;

/**
 * A {@code GPPainter} extension to render pileup files.
 * 
 * @author hlfernandez
 *
 */
public class PileupPainter extends GPPainter {
	private enum pileupColums {
		Nothing, Ref, Cons, PhredCons, SNPQuality, RMSReads, Depth, ReadBases, ReadQualities
	};

	private enum pileupColumsSimple {
		Nothing, Ref, Depth, ReadBases, ReadQualities
	};

	private enum histogramType {
		entrycount, avgdepth
	};

	private int minSNPQual = 0, maxSNPQual = -1, maxConsQual = -1,
		minConsQual = 0, maxDepth = -1, minDepth = 0;
	private boolean entrycount = true;
	private boolean isCompletePileup = false;
	
	private Vector<Double> avgValues = new Vector<Double>();

	/**
	 * Creates a new {@code PileupPainter} for the specified track file.
	 * 
	 * @param file a pileup file
	 */
	public PileupPainter(File file) {
		super(file);
		try {
			isCompletePileup = GenomeBrowserUtil.isCompletePileup(file);
		} catch (IOException e) {
		}
		this.initializeOptions();
		this.fillOptions(null);
	}

	@Override
	public void paint(Graphics2D g2, GenomeBrowser genomeBrowser, int offset) {
		this.fillOptions(genomeBrowser);
		if (customColor != null)
			this.trackColor = customColor;
		this.offset = offset;
		double drawNucleotides = genomeBrowser.getTracksPanel()
			.drawNucleotides(g2);
		boolean histogram = true;
		GenomeBrowserUtil.drawString(g2, this.getTrackName(), trackHeight - 5,
			genomeBrowser.getTracksPanel(), offset);
		if (drawNucleotides > 0.0f)
			histogram = false;
		if (histogram) {
			this.renderHistogram(g2, this.file, genomeBrowser);
		} else {
			this.renderNucleotides(g2, this.file, 0, this.trackColor,
				genomeBrowser);
		}
	}

	private void renderHistogram(Graphics2D g2, File current, GenomeBrowser gv) {
		g2.setColor(this.trackColor);
		FontMetrics fm = g2.getFontMetrics();
		int trackPosition = trackHeight;
		g2.setStroke(new BasicStroke(1f));
		Line2D line = new Line2D.Double(gv.getTracksPanel().getX(125),
			trackPosition - 1, gv.getTracksPanel().getX(875),
			trackPosition - 1);
		g2.draw(line);
		int maxValue = 0;

		int size = this.histogramIntervals;

		if (tmpValues.isEmpty()) {
			tmpValues = new Vector<Integer>(size);
			avgValues = new Vector<Double>(size);
			Iterator<String> it;
			long intervalStart = gv.getInitialPosition();
			long histogramIntervalSize = (gv.getFinalPosition()
				- gv.getInitialPosition()) / size;

			long intervalEnd = gv.getInitialPosition() + histogramIntervalSize;
			int sum = 0;
			String currentLine;
			int depthAcum = 0;
			try {

				for (int c = 0; c < size; c++) {

					it = this.seek.seek(gv.getCurrentSequence(),
						(int) intervalStart, (int) intervalEnd);
					while (it.hasNext()) {
						currentLine = it.next();
						if (isValidLine(currentLine, gv)) {
							sum++;
							if (!entrycount) {
								depthAcum += Integer
									.valueOf(parsePileupLine(currentLine, 7));
							}
						}
					}
					if (entrycount) {
						tmpValues.add(sum);
						if (sum > maxValue)
							maxValue = sum;
					} else {
						int media = 0;
						if (depthAcum > 0)
							media = depthAcum / sum;
						tmpValues.add(media);
						avgValues.add((double) depthAcum / (double) sum);
						if (media > maxValue)
							maxValue = media;
					}
					sum = 0;
					depthAcum = 0;
					intervalStart += histogramIntervalSize;
					intervalEnd += histogramIntervalSize;
					if (intervalEnd > gv.getFinalPosition())
						intervalEnd = gv.getFinalPosition();
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
			double anchoIntervalo = (gv.getTracksPanel().getWidth() * 0.75)
				/ size;
			double initialXPos = gv.getTracksPanel().getX(125);
			Color old = g2.getColor();
			int total = 0;
			for (Integer x : tmpValues) {
				total += x;
				Rectangle2D rectangle;
				Area a;
				if (x > limit) {
					rectangle = new Rectangle2D.Float((float) initialXPos,
						(float) (trackPosition - unitHeight * limit),
						(float) anchoIntervalo, (float) unitHeight * limit);
					a = new Area(rectangle);
					g2.setColor(Color.LIGHT_GRAY);
					g2.fill(a);
					g2.setColor(Color.BLACK);
					double middle = (anchoIntervalo / 2);
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
						(float) anchoIntervalo, (float) unitHeight * x);
					g2.draw(rectangle);
					a = new Area(rectangle);
					g2.fill(a);

				}
				String count = gv.getTracksPanel().getFormatter()
					.format(x);
				if (!entrycount && tmpValues.indexOf((x)) < avgValues.size()) {
					count = avgValues.get(tmpValues.indexOf((x))).toString();
				}
				if (fm.stringWidth(count) + 4 <= anchoIntervalo && x != 0) {
					g2.setColor(GenomeBrowserUtil.LIGHT_BLACK);
					double middle = (anchoIntervalo / 2)
						- (fm.stringWidth(count) / 2);
					g2.drawString(count, (int) (initialXPos + middle),
						trackPosition);
					g2.setColor(old);
				} else if (x != 0 && anchoIntervalo > 1) {
					GenericInfo data = new TrackPositionInfo(x, old);
					GridInfo cI = new GridInfo(data, GridInfo.TRACKINFO);
					for (int increment = 4; increment < unitHeight
						* x; increment += gv.getTracksPanel()
							.getSquareWidth()) {
						gv.getTracksPanel()
							.addCuadriculaInfo((int) (initialXPos), gv
								.getTracksPanel().getY(offset - increment),
								cI);
					}
				}

				initialXPos += anchoIntervalo;
			}
			g2.setColor(GenomeBrowserUtil.LIGHT_BLACK);
			String max = "";
			DecimalFormat df = new DecimalFormat("0.00");
			if (gv.getTracksPanel().getMaxHistogramValue() == 0)
				max = gv.getTracksPanel().getFormatter().format(maxValue);
			else
				max = gv.getTracksPanel().getFormatter()
					.format(gv.getTracksPanel().getMaxHistogramValue());
			if (this.maxHistogramValue == 0) {

				max = gv.getTracksPanel().getFormatter().format(maxValue);
				if (!entrycount) {
					max = GenomeBrowserUtil.getMaxDoubleValue(avgValues)
						.toString();
					max = df.format(maxValue).toString();
				}
			} else {
				max = gv.getTracksPanel().getFormatter()
					.format(this.maxHistogramValue);
				if (!entrycount) {
					max = (new Double(maxHistogramValue)).toString();
					max = df.format(maxHistogramValue).toString();
				}
			}

			if (entrycount)
				g2.drawString(
					gv.getTracksPanel().getFormatter().format(total)
						+ " ocurrences",
					(int) gv.getTracksPanel().getX(875) + 10,
					trackPosition - 5);
			else
				g2.drawString("Depth average",
					(int) gv.getTracksPanel().getX(875) + 10,
					trackPosition - 5);
			g2.drawString("Max = " + max,
				(int) gv.getTracksPanel().getX(875) + 2,
				(int) (trackPosition - histogramHeight + 15));
		} else {
			g2.setColor(GenomeBrowserUtil.LIGHT_BLACK);
			if (entrycount)
				g2.drawString("0 ocurrences",
					(int) gv.getTracksPanel().getX(875) + 2,
					trackPosition - 5);
		}

	}

	protected boolean isValidLine(String line, GenomeBrowser gv) {
		if (isCompletePileup) {
			int depth = Integer.valueOf(parsePileupLine(line, 7));
			int snp = Integer.valueOf(parsePileupLine(line, 5));
			int phredCons = Integer.valueOf(parsePileupLine(line, 4));

			return (((this.maxConsQual == -1 && phredCons >= this.minConsQual)
				|| (this.maxConsQual > this.minConsQual
					&& phredCons >= this.minConsQual
					&& phredCons <= this.maxConsQual))
				&& ((this.maxDepth == -1 && depth >= this.minDepth)
					|| (this.maxDepth > this.minDepth && depth >= this.minDepth
						&& depth <= this.maxDepth))
				&& ((this.maxSNPQual == -1 && snp >= this.minSNPQual)
					|| (this.maxSNPQual > this.minSNPQual
						&& snp >= this.minSNPQual && snp <= this.maxSNPQual)));
		} else {
			int depth = Integer.valueOf(parsePileupLine(line, 3));
			return ((this.maxDepth == -1 && depth >= this.minDepth)
				|| (this.maxDepth > this.minDepth && depth >= this.minDepth
					&& depth <= this.maxDepth));
		}
	}

	protected void renderNucleotides(Graphics2D g2, File current, int i,
		Color currentColor, GenomeBrowser gv) {
		double width = (gv.getTracksPanel().getWidth() * 0.75)
			/ (gv.getFinalPosition() - gv.getInitialPosition());
		FontMetrics fm = g2.getFontMetrics();
		int trackPosition = this.trackHeight;
		boolean displayColums = this.columnToDisplay > 1;
		if (displayColums)
			g2.drawString(this.getColumName(columnToDisplay),
				gv.getTracksPanel().getX(875 + 5),
				gv.getTracksPanel().getY(trackPosition) - 5);

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

				String columValue = parsePileupLine(tmp, this.columnToDisplay);

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

				PileupInfo pileupInfo = new PileupInfo(tmp, nucleotidColor,
					isCompletePileup);
				GridInfo aux = new GridInfo(pileupInfo, GridInfo.PILEUPINFO);
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

		TrackOption displayColum = new TrackOption() {

			private pileupColums value = pileupColums.Cons;

			@Override
			public String getName() {

				return "Display column: ";
			}

			@Override
			public Class<?> getType() {

				return pileupColums.class;
			}

			@Override
			public Object getValue() {
				return this.value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (pileupColums) value;

			}

		};

		TrackOption displayColumSimple = new TrackOption() {

			private pileupColumsSimple value = pileupColumsSimple.Ref;

			@Override
			public String getName() {

				return "Display column: ";
			}

			@Override
			public Class<?> getType() {

				return pileupColumsSimple.class;
			}

			@Override
			public Object getValue() {
				return this.value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (pileupColumsSimple) value;

			}

		};

		TrackOption colorChooser = new TrackOption() {

			private Color value = PileupPainter.this.trackColor;

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

			private Color value = PileupPainter.this.backGroundColor;

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

		TrackOption minDepth = new TrackOption() {

			String value = "0";

			@Override
			public String getName() {
				return "Min depth: ";
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

		TrackOption maxDepth = new TrackOption() {

			String value = "-1";

			@Override
			public String getName() {
				return "Max depth (-1: no limit): ";
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

		TrackOption minConsQual = new TrackOption() {

			String value = "0";

			@Override
			public String getName() {
				return "Min cons qual: ";
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

		TrackOption maxConsQual = new TrackOption() {

			String value = "-1";

			@Override
			public String getName() {
				return "Max cons qual (-1: no limit): ";
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

		TrackOption minSNPQual = new TrackOption() {

			String value = "0";

			@Override
			public String getName() {
				return "Min snp qual: ";
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

		TrackOption maxSNPQual = new TrackOption() {

			String value = "-1";

			@Override
			public String getName() {
				return "Max snp qual (-1: no limit): ";
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

		TrackOption histogramTypeOption = new TrackOption() {

			private histogramType value = histogramType.entrycount;

			@Override
			public String getName() {

				return "Histogram value: ";
			}

			@Override
			public Class<?> getType() {

				return histogramType.class;
			}

			@Override
			public Object getValue() {
				return this.value;
			}

			@Override
			public void setValue(Object value) {
				this.value = (histogramType) value;

			}

		};
		if (isCompletePileup) {
			this.options = java.util.Arrays.asList(new TrackOption[] {
				histogramIntervals, maxHistogramValue, histogramTypeOption,
				displayColum, colorChooser, backGroundcolorChooser, trackSize,
				minDepth, maxDepth, minConsQual, maxConsQual, minSNPQual,
				maxSNPQual, trackName });
		} else {
			this.options = java.util.Arrays.asList(new TrackOption[] {
				histogramIntervals, maxHistogramValue, histogramTypeOption,
				displayColumSimple, colorChooser, backGroundcolorChooser,
				trackSize, minDepth, maxDepth, trackName });
		}
	}

	private void fillOptions(GenomeBrowser genomeBrowser) {

		boolean changedColumnFilters = false;
		for (TrackOption option : this.options) {
			if (option.getName().startsWith("Display column")) {
				this.columnToDisplay = this.getPileupColum(option.getValue());
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
			} else if (option.getName().contains("Min depth")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.minDepth != newValue) {
						this.minDepth = newValue;
						changedColumnFilters = true;
					}
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.minDepth));
				}
			} else if (option.getName().contains("Max depth")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.maxDepth != newValue) {
						this.maxDepth = newValue;
						changedColumnFilters = true;
					}
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.maxDepth));
				}
			} else if (option.getName().contains("Min cons qual")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.minConsQual != newValue) {
						this.minConsQual = newValue;
						changedColumnFilters = true;
					}
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.minConsQual));
				}
			} else if (option.getName().contains("Max cons qual")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.maxConsQual != newValue) {
						this.maxConsQual = newValue;
						changedColumnFilters = true;
					}
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.maxConsQual));
				}
			} else if (option.getName().contains("Min snp qual")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.minSNPQual != newValue) {
						this.minSNPQual = newValue;
						changedColumnFilters = true;
					}
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.minSNPQual));
				}
			} else if (option.getName().contains("Max snp qual")) {
				try {
					int newValue = Integer.valueOf((String) option.getValue());
					if (this.maxSNPQual != newValue) {
						this.maxSNPQual = newValue;
						changedColumnFilters = true;
					}
				} catch (NumberFormatException nFE) {
					option.setValue((String) String.valueOf(this.maxSNPQual));
				}
			} else if (option.getName().contains("Track name")) {
				if (!((String) option.getValue()).equals(""))
					this.trackName = (String) option.getValue();
				else
					this.trackName = "default";
			} else if (option.getName().startsWith("Histogram value:")) {
				boolean last = this.entrycount;
				this.entrycount = (option.getValue()
					.equals(histogramType.entrycount));
				if (last != this.entrycount)
					changedColumnFilters = true;
			}
		}
		if (changedColumnFilters)
			genomeBrowser.getTracksPanel()
				.setPileupColumnsFiltersChanged(true);
	}

	private int getPileupColum(Object value) {
		if (isCompletePileup) {
			if (value.equals(pileupColums.Nothing))
				return 0;
			if (value.equals(pileupColums.Ref))
				return 2;
			if (value.equals(pileupColums.Cons))
				return 3;
			if (value.equals(pileupColums.PhredCons))
				return 4;
			if (value.equals(pileupColums.SNPQuality))
				return 5;
			if (value.equals(pileupColums.RMSReads))
				return 6;
			if (value.equals(pileupColums.Depth))
				return 7;
			if (value.equals(pileupColums.ReadBases))
				return 8;
			if (value.equals(pileupColums.ReadQualities))
				return 9;
		} else {
			if (value.equals(pileupColumsSimple.Nothing))
				return 0;
			if (value.equals(pileupColumsSimple.Ref))
				return 2;
			if (value.equals(pileupColumsSimple.Depth))
				return 3;
			if (value.equals(pileupColumsSimple.ReadBases))
				return 4;
			if (value.equals(pileupColumsSimple.ReadQualities))
				return 5;
		}
		return 0;
	}

	private String getColumName(int column) {
		if (isCompletePileup) {
			switch (column) {
			case 2:
				return "Ref";
			case 3:
				return "Cons";
			case 4:
				return "Phred. Cons";
			case 5:
				return "SNP Quality";
			case 6:
				return "RMS Reads";
			case 7:
				return "Depth";
			case 8:
				return "Read Bases";
			case 9:
				return "Read Qualities";
			default:
				return "null";
			}
		} else {
			switch (column) {
			case 2:
				return "Ref";
			case 3:
				return "Depth";
			case 4:
				return "Read Bases";
			case 5:
				return "Read Qualities";
			default:
				return "null";
			}
		}

	}

	@Override
	public void setOptions(Collection<TrackOption> options) {
		if (options != null)
			this.options = options;
	}

	@Override
	public void reset() {
		this.avgValues.clear();
		this.tmpValues.clear();
	}

	private String parsePileupLine(String line, int i) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		String toret = null;
		int j = 0;
		while (tokenizer.hasMoreTokens() && (j <= i)) {
			toret = tokenizer.nextToken();
			j++;
		}

		return toret;
	}
}
