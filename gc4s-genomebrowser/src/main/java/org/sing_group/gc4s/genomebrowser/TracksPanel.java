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

import static org.sing_group.gc4s.genomebrowser.grid.Coordinates.calculateXYCoordinates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sing_group.gc4s.genomebrowser.grid.Coordinates;
import org.sing_group.gc4s.genomebrowser.grid.GenomeBrowserInfo;
import org.sing_group.gc4s.genomebrowser.grid.GridInfo;
import org.sing_group.gc4s.genomebrowser.painter.Painter;
import org.sing_group.gc4s.ui.icons.Icons;

import es.cnio.bioinfo.pileline.refgenomeindex.GenomeIndex;

/**
 * A panel for managing tracks and track painters.
 * 
 * @author hlfernandez
 *
 */
public class TracksPanel extends JPanel
	implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private static final ImageIcon TRACK_SETTINGS_ICON = Icons.ICON_SETTINGS_2_16;
	private static final ImageIcon TRACK_REMOVE_ICON = Icons.ICON_CANCEL_16;

	private GenomeBrowser genomeBrowser;
	private GenomeIndex genomeIndex;

	private int width;
	private int height;
	private int histogramIntervals = 20;
	private int maxHistogramValue = 0;

	private int maxHeight;
	private int gridLineCount = -1;
	private final NumberFormat formatter = new DecimalFormat("#,###,###");

	private Color[] trackColors = { 
		Color.decode("#9AB1D7"),
		Color.decode("#9DC6A6"), 
		Color.decode("#D29EEE"),
		Color.decode("#F6B9AC") 
	};

	private BufferedImage doubleBuffer;
	private boolean changed = true;
	private boolean changedGenomicPositions = true;
	private boolean changedPileupColumFilters = false;

	private CenterHereJMenuItem centerHereMenuItem = 
		new CenterHereJMenuItem("Center here");
	private JPopupMenu popup;

	private HashMap<JButton, Painter> buttonToPainter = new HashMap<JButton, Painter>();

	private static final int DEFAULT_SQUARE_WIDTH = 10;
	private int squareWidth = DEFAULT_SQUARE_WIDTH;

	private HashMap<Coordinates, LinkedList<GridInfo>> coordinatesMapping = 
		new HashMap<Coordinates, LinkedList<GridInfo>>();

	private int XmousePressed;
	private int YmousePressed;
	private boolean dragging = false;
		
	/**
	 * Creates a new {@code TracksPanel} instance for the specified
	 * {@code GenomeBrowser}.
	 * 
	 * @param gv a {@code GenomeBrowser} instance
	 */
	public TracksPanel(GenomeBrowser gv) {
		this.setLayout(null);
		genomeBrowser = gv;
		genomeIndex = gv.getGenomeIndex();
		if (genomeBrowser.getStatus().getNumberOfIntervals() > 0) {
			this.histogramIntervals = 
				genomeBrowser.getStatus().getNumberOfIntervals();
		}

		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.centerHereMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(centerHereMenuItem.getCoordinates() != null) {
					TracksPanel.this.changedGenomicPositions = true;
					TracksPanel.this.genomeBrowser
						.centerAtCoordinate(centerHereMenuItem.getCoordinates());
				}
			}
		});

		this.popup = new JPopupMenu();
		this.popup.add(centerHereMenuItem);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		genomeBrowser.changeCursor(Cursor.WAIT_CURSOR);
		int height = GenomeBrowserUtil.computeHeight(genomeBrowser);

		Graphics2D realGraphics = (Graphics2D) g;
		realGraphics.drawImage(doubleBuffer, 0, 0, width, height, this);

		genomeBrowser.setOptionTrackButtonEnabled(genomeBrowser.getFiles().size() > 0);
		genomeBrowser.setRemoveTrackButtonEnabled(genomeBrowser.getFiles().size() > 0);

		this.setBounds(0, 0, (int) (genomeBrowser.getWidth() * 0.985), height);
		genomeBrowser.getToolTipsPane().setBounds(0, 0,
			(int) (genomeBrowser.getWidth() * 0.985), height);
		genomeBrowser.getDraggingPane().setBounds(0, 0,
			(int) (genomeBrowser.getWidth() * 0.985), height);
		genomeBrowser.getLayeredPane().setPreferredSize(
			new Dimension((int) (genomeBrowser.getWidth() * 0.985), height));

		genomeBrowser.changeCursor(Cursor.DEFAULT_CURSOR);
	}

	private boolean painting = false;

	public void updateDoubleBuffer() {
		if (!painting && changed) {
			int currentHeight = GenomeBrowserUtil.computeHeight(genomeBrowser);
			painting = true;
			gridLineCount = -1;

			if (changedGenomicPositions || changedPileupColumFilters) {
				for (Painter p : genomeBrowser.getPainters()) {
					p.reset();
				}
			}
			coordinatesMapping.clear();

			maxHeight = this.getHeight();
			maxHeight = currentHeight;

			Dimension size = this.getSize();

			width = size.width;
			height = maxHeight;

			BufferedImage dummyBuffer = 
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

			Graphics2D g2 = (Graphics2D) dummyBuffer.createGraphics();

			getY(currentHeight);

			BufferedImage doubleBuffer = 
				new BufferedImage(width, currentHeight, BufferedImage.TYPE_INT_RGB);

			g2 = (Graphics2D) doubleBuffer.createGraphics();

			g2.setBackground(this.getBackground());

			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, width, currentHeight);

			g2.setColor(Color.BLACK);
			
			this.paintRuler(g2);
			this.paintTracks(g2);

			this.changed = false;
			this.changedGenomicPositions = false;
			this.doubleBuffer = doubleBuffer;
			this.painting = false;
		}
	}

	public int getX(int value) {
		return (width * value / 1000);
	}

	public int getY(int value) {
		if (value > maxHeight) {
			maxHeight = value + 20;
		}

		return value;
	}

	public int getY(int value, int offset) {
		if (value + offset > maxHeight)
			maxHeight = value + 20;

		return value;
	}

	private void paintGrid(Graphics2D g2, int yStart, int yStop) {
		double interval = 0.0f;
		int lineCount = gridLineCount;

		if (gridLineCount == -1) {
			lineCount = 0;

			if (width > 1000) {
				interval = (width * 0.75) / 30;
				lineCount = 30;
			} else if (width > 700) {
				interval = (width * 0.75) / 23;
				lineCount = 23;
			} else if (width > 350) {
				interval = (width * 0.75) / 18;
				lineCount = 18;
			} else {
				interval = (width * 0.75) / 12;
				lineCount = 12;
			}

		} else {
			interval = (width * 0.75) / lineCount;
		}

		Color oldColor = g2.getColor();
		g2.setColor(Color.lightGray);

		float dash[] = { 4.0f, 4.0f };

		BasicStroke stroke = new BasicStroke(
			0.01f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER, (float) 1.0, 
			dash, 0
		);
		g2.setStroke(stroke);

		double initialXPos = getX(125);

		Line2D rulerHeight;
		for (int i = 0; i < lineCount; i++) {
			rulerHeight = new Line2D.Double(initialXPos, getY(yStart),
				initialXPos, getY(yStop) * 2);
			g2.draw(rulerHeight);
			initialXPos += interval;
		}
	
		rulerHeight = new Line2D.Double(
			getX(875), getY(yStart), getX(875), getY(yStop) * 2);
		g2.draw(rulerHeight);
		g2.setStroke(new BasicStroke(0.01f));
		g2.setColor(oldColor);
	}
	
	private void paintRuler(Graphics2D g2) {
		Font f = new Font("Courier", Font.PLAIN, getFontSize());
		g2.setFont(f);

		FontMetrics fm = g2.getFontMetrics();
		int shift = fm
			.stringWidth(String.valueOf(genomeBrowser.getFinalPosition()));

		double baseWidth = drawNucleotides(g2);

		g2.setStroke(new BasicStroke(0.5f));
		
		int bases = (int) 
			(genomeBrowser.getFinalPosition() - genomeBrowser.getInitialPosition());
		int factor = 10;
		if (bases < 14) {
			factor = 1;
		} else if (bases < 25) {
			factor = 4;
		} else if (bases < 45) {
			factor = 6;
		} else if (bases < 60) {
			factor = 8;
		}

		Stroke normal = g2.getStroke();
		Stroke thin = new BasicStroke(0.01f);
		Color oldColor = g2.getColor();

		if (baseWidth > 0.0f) {
			shift = fm.stringWidth(genomeBrowser.getCurrentSequence());

			g2.drawString(
				genomeBrowser.getCurrentSequence(), 
				getX(125 - shift - 3),
				getY(130)
			);

			try {
				Reader r = genomeIndex.seek(
					genomeBrowser.getCurrentSequence(),
					(int) genomeBrowser.getInitialPosition(),
					(int) genomeBrowser.getFinalPosition()
				);
				
				double initialXPos = getX(125);
				
				gridLineCount = (int) 
					(genomeBrowser.getFinalPosition() - genomeBrowser.getInitialPosition());

				paintGrid(g2, 0, height);
				
				for (int i = 0; i < (int) (genomeBrowser.getFinalPosition()
					- genomeBrowser.getInitialPosition()); i++) {
					char current = (char) r.read();
					float posicionCaracter = (float) ((float) (baseWidth/2) - 3.5f);
					
					g2.setColor(GenomeBrowserUtil.getNucleotideColor(current));
					g2.drawString(String.valueOf(current),  (int) initialXPos + posicionCaracter,getY(130));

					g2.setColor(Color.lightGray);
					g2.setStroke(thin);
					
					Line2D smallLine;
					
					g2.setColor(oldColor);
					g2.setStroke(normal);

					if ((i != 0) && ((i % factor) == 0)) {
						smallLine = new Line2D.Double((int) initialXPos,
							getY(65), (int) initialXPos, getY(71));
						g2.draw(smallLine);
						g2.drawString(
							formatter.format(genomeBrowser.getInitialPosition() + i),
							(int) initialXPos + posicionCaracter, getY(68));
					}

					initialXPos += baseWidth;
				}
				
				g2.setColor(Color.lightGray);
				g2.setStroke(thin);

				Line2D smallLine = new Line2D.Double((int) getX(125), getY(0),
					(int) getX(125), getY(height));
				g2.draw(smallLine);

				smallLine = new Line2D.Double((int) getX(875), getY(0),
					(int) getX(875), getY(height));
				g2.draw(smallLine);

				g2.setColor(oldColor);
				g2.setStroke(normal);

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			int amountOfBases = 5;
			paintGrid(g2, 0, height);
			double basesResult = bases / amountOfBases;
			
			double valorBase = genomeBrowser.getInitialPosition() + basesResult;
			baseWidth = (width * 0.75) / amountOfBases;
			double initialXPos = getX(125);
			initialXPos += baseWidth;

			for (int i = 1; i < amountOfBases; i++) {
				Line2D smallLine = new Line2D.Double((int) initialXPos,
					getY(62), (int) initialXPos, getY(71));
				g2.draw(smallLine);
				g2.drawString(formatter.format(valorBase),
					(int) initialXPos + 1, getY(62));
				valorBase += basesResult;
				initialXPos += baseWidth;
			}

		}

		paintRulerLine(g2);
	}

	private void paintRulerLine(Graphics2D g2) {
		Font f = new Font("Courier", Font.PLAIN, getFontSize());
		g2.setFont(f);

		Line2D rulerWidthLine = new Line2D.Double(getX(125), getY(75),
			getX(875), getY(75));
		g2.setStroke(new BasicStroke(3.0f));
		g2.draw(rulerWidthLine);

		g2.setStroke(new BasicStroke(3.0f));

		Line2D rulerHeight = new Line2D.Double(getX(125), getY(68), getX(125),
			getY(75));
		g2.drawString(formatter.format(genomeBrowser.getInitialPosition()),
			getX(125), getY(62));
		g2.draw(rulerHeight);

		rulerHeight = new Line2D.Double(getX(875), getY(68), getX(875),
			getY(75));
		g2.draw(rulerHeight);

		Long lengh = genomeIndex
			.getSequenceLength(genomeBrowser.getCurrentSequence());
		g2.drawString("Sequence length: " + formatter.format(lengh) + " bases",
			getX(445), getY(30));

		g2.drawString("Viewing", getX(900), getY(26));
		g2.drawString(
			formatter.format(
				genomeBrowser.getFinalPosition() - genomeBrowser.getInitialPosition()),
			getX(900), getY(35));
		g2.drawString("bases", getX(900),getY(44));
		
		GenomeBrowserInfo gVI = new GenomeBrowserInfo(formatter.format(lengh),
			formatter.format(genomeBrowser.getInitialPosition()).toString(),
			formatter.format(genomeBrowser.getFinalPosition()).toString(),
			genomeBrowser.getCurrentSequence());
		GridInfo cI = new GridInfo(gVI, GridInfo.GENOMEBROWSERINFO);
		this.addCuadriculaInfo(getX(900), getY(35), cI);
	}

	private int getFontSize() {
		return 11;
	}

	public double drawNucleotides(Graphics2D g2) {
		double letterWidth = ((width * 0.75))
			/ (genomeBrowser.getFinalPosition() - genomeBrowser.getInitialPosition());

		if (letterWidth >= 7) {
			return letterWidth;
		}

		return 0.0f;
	}

	private void paintTracks(Graphics2D g2) {
		TracksPanel.this.removeAll();
		int trackColorSelector = 0;
		int trackPosition = 200;
		this.fillBackground(g2, trackPosition);

		for (Painter painter : genomeBrowser.getPainters()) {
			int trackHeight = painter.computeHeight(genomeBrowser);
			Graphics area = ((Graphics) g2).create(0, trackPosition - 65,
				this.width, trackHeight);
			if (painter.getTrackColor() == null) {
				painter
					.setTrackColor(this.nextTrackColor(trackColorSelector++));
			}
			painter.paint((Graphics2D) area, genomeBrowser, trackPosition);
			trackPosition += trackHeight;
			
			JButton options = new JButton();
			options.setBounds(getX(978), trackPosition - trackHeight - 63, 20,
				20);
			options.setIcon(TRACK_SETTINGS_ICON);
			this.add(options);
			buttonToPainter.put(options, painter);
			options.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Painter painter = buttonToPainter
						.get((JButton) arg0.getSource());
					TrackOptionsDialog optionsDialog = new TrackOptionsDialog(
						painter.getOptions());
					optionsDialog.setVisible(true);
					if (optionsDialog.getStatus() == TrackOptionsDialog.OK) {
						TracksPanel.this.setChanged();
						TracksPanel.this.genomeBrowser
							.repaintGenomeBrowser();
					}
				}
			});

			JButton remove = new JButton();
			remove.setBounds(getX(978), trackPosition - trackHeight - 43, 20,
				20);
			remove.setIcon(TRACK_REMOVE_ICON);
			remove.setToolTipText("Remove track");
			buttonToPainter.put(remove, painter);
			remove.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int option = JOptionPane.showConfirmDialog(
						TracksPanel.this,
						"Are you sure to delete this track?");
					if (option == JOptionPane.OK_OPTION) {
						Painter p = TracksPanel.this.buttonToPainter
							.get((JButton) arg0.getSource());
						if (p != null) {
							TracksPanel.this.buttonToPainter
								.remove((JButton) arg0.getSource());
							TracksPanel.this.genomeBrowser
								.removePainter(p);
							TracksPanel.this.setChanged();
							TracksPanel.this.genomeBrowser
								.repaintGenomeBrowser();
						}
					}
				}
			});
			this.add(remove);
		}
	}
	
	private Color nextTrackColor(int nextTrackColorPosition) {
		nextTrackColorPosition = nextTrackColorPosition % 4;
		Color toret = trackColors[nextTrackColorPosition];

		return toret;
	}

	private Color nextTrackBackgroundColor(int i) {
		i = i % 2;
		if (i == 0) {
			return GenomeBrowserUtil.GRAY_1;
		} else {
			return GenomeBrowserUtil.GRAY_2;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		genomeBrowser.getDraggingPane().reset();
		if (e.getButton() == MouseEvent.BUTTON1) {
			Coordinates c = calculateXYCoordinates(e.getX(), e.getY(),
				squareWidth);
			LinkedList<GridInfo> list = coordinatesMapping.get(c);
			if (list != null) {
				GridInfo cuadriculas = list.getFirst();
				if ((cuadriculas.getType() == GridInfo.FILENAME)
					|| (cuadriculas.getType() == GridInfo.TRACKINFO)
					|| (cuadriculas.getType() == GridInfo.GENOMEBROWSERINFO)
					|| (cuadriculas.getType() == GridInfo.INTERVALINFO)
					|| (cuadriculas.getType() == GridInfo.PILEUPINFO)
				) {
					genomeBrowser.getToolTipsPane()
						.setCuadriculaInfo(cuadriculas);
				}

			} else {
				genomeBrowser.getToolTipsPane().setCuadriculaInfo(null);
			}

			genomeBrowser.getToolTipsPane().repaint();
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			centerHereMenuItem
				.setCoordinates(new Coordinates(e.getX(), e.getY()));
			popup.show(this, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (dragging) {
			dragging = false;
			int xStop = e.getX();
			TracksPanel.this.changedGenomicPositions = true;
			if (xStop > this.XmousePressed) {
				genomeBrowser.seekCoordinates(XmousePressed, xStop);
			} else {
				genomeBrowser.seekCoordinates(xStop, XmousePressed);
			}
			genomeBrowser.getDraggingPane().reset();
			genomeBrowser.getDraggingPane().repaint();
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		genomeBrowser.changeCursor(Cursor.DEFAULT_CURSOR);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() > this.getX(125) && e.getX() < this.getX(875)) {
			XmousePressed = e.getX();
			YmousePressed = e.getY();
		}
	}

	public void addCuadriculaInfo(double x, double y, GridInfo cI) {
		Coordinates c = calculateXYCoordinates(x, y, squareWidth);
		LinkedList<GridInfo> list = coordinatesMapping.get(c);
		cI.setCoordinates(c);
		if (list != null) {
			list.add(cI);
			coordinatesMapping.remove(c);
			coordinatesMapping.put(c, list);
		} else {
			list = new LinkedList<GridInfo>();
			list.add(cI);
			coordinatesMapping.put(c, list);
		}
	}

	public void addCuadriculaAtRectangle(GridInfo gridInfo, float xStart,
		float yStart, float width, float height) {
		int squareHeight = 0;
		int squareWidth = 0;
		while (squareHeight < height) {
			while (squareWidth < width) {
				addCuadriculaInfo(
					xStart + squareWidth, 
					yStart + squareHeight,
					gridInfo
				);
				squareWidth += this.squareWidth;
			}
			squareHeight += this.squareWidth;
		}
	}

	public void clearHistogramIntervalsHashMap() {
		changedGenomicPositions = true;
		for (Painter p : genomeBrowser.getPainters()) {
			p.reset();
		}
	}

	private class CenterHereJMenuItem extends JMenuItem {
		private static final long serialVersionUID = 1L;
		private Coordinates coordinates;

		public CenterHereJMenuItem(String string) {
			super(string);
		}

		public Coordinates getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(Coordinates c) {
			coordinates = c;
		}
	}

	public class RemoveTrackJMenuItem extends JMenuItem {
		private static final long serialVersionUID = 1L;
		private double yCoordinate;

		public RemoveTrackJMenuItem(String string) {
			super(string);
		}

		public void setYCoordinate(double d) {
			this.yCoordinate = d;
		}

		public double setYCoordinate() {
			return this.yCoordinate;
		}
	}

	public void setChanged() {
		changed = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		genomeBrowser.changeCursor(Cursor.CROSSHAIR_CURSOR);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() > this.getX(125) && e.getX() < this.getX(875)) {
			dragging = true;
			genomeBrowser.getDraggingPane().setxStart(XmousePressed);
			genomeBrowser.getDraggingPane().setyStart(YmousePressed);
			genomeBrowser.getDraggingPane().setxStop(e.getX());
			genomeBrowser.getDraggingPane().setyStop(e.getY());
			genomeBrowser.getDraggingPane().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Coordinates c = calculateXYCoordinates(e.getX(), e.getY(), squareWidth);
		LinkedList<GridInfo> list = coordinatesMapping.get(c);
		if (list != null) {
			genomeBrowser.changeCursor(Cursor.HAND_CURSOR);

		} else {
			genomeBrowser.changeCursor(Cursor.CROSSHAIR_CURSOR);
		}
	}

	private void fillBackground(Graphics2D g2, int y) {
		int selector = 0;
		for (Painter painter : genomeBrowser.getPainters()) {
			int trackHeight = painter.computeHeight(genomeBrowser);
			Rectangle2D rectangle = new Rectangle2D.Float(
				(float) 0,
				(float) (y - 65), 
				(float) this.getWidth(), 
				(float) trackHeight
			);
			Color backGroundColor = nextTrackBackgroundColor(selector++);
			Color painterBackGround = painter.getBackgroundColor();
			Color oldColor = g2.getColor();

			if (painterBackGround == null)
				g2.setColor(backGroundColor);
			else {
				g2.setColor(painterBackGround);
			}

			Area a = new Area(rectangle);
			g2.fill(a);
			g2.setColor(oldColor);
			paintGrid(g2, y - 65, y + trackHeight);
			y += trackHeight;
		}
	}

	public int getMaxHistogramValue() {
		return maxHistogramValue;
	}

	public void setMaxHistogramValue(int maxHistogramValue) {
		this.maxHistogramValue = maxHistogramValue;
	}

	public void setPileupColumnsFiltersChanged(boolean b) {
		this.changedPileupColumFilters = b;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public NumberFormat getFormatter() {
		return formatter;
	}

	public int getHistogramIntervals() {
		return histogramIntervals;
	}
	
	public BufferedImage getDoubleBuffer() {
		return doubleBuffer;
	}
	
	public int getSquareWidth() {
		return squareWidth;
	}

	public void repaintTracks() {
		super.repaint();
	}
}
