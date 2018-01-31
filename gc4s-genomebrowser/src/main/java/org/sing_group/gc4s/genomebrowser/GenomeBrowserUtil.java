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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.sing_group.gc4s.genomebrowser.grid.FileInfo;
import org.sing_group.gc4s.genomebrowser.grid.GridInfo;
import org.sing_group.gc4s.genomebrowser.painter.Painter;

import es.cnio.bioinfo.pileline.core.GPFileConstants;
//import htsjdk.samtools.util.BlockCompressedInputStream;
import net.sf.samtools.util.BlockCompressedInputStream;

/**
 * This class provides different utilities for the genome browser and its
 * associated components.
 * 
 * @author hlfernandez
 *
 */
public class GenomeBrowserUtil {

	public final static Color LIGHT_BLACK = Color.decode("#151515");
	public final static Color GRAY_1 = Color.decode("#E6E6E6");
	public final static Color GRAY_2 = Color.decode("#D8D8D8");
	public final static Color RED_PASTEL = Color.decode("#FAA787");
	public final static Color BLUE_PASTEL = Color.decode("#A4B2D7");
	public final static Color GREEN_APPLE = Color.decode("#B0DAB2");
	public final static Color BROWN_PASTEL = Color.decode("#CBA580");
	public final static Color DARK_GREEN_PASTEL = Color.decode("#9DC6A6");
	public final static Color GREEN_M = Color.decode("#D1EBA4");
	public final static Color PURPLE_PASTEL = Color.decode("#DBCEDF");
	public final static Color BLUE_LAVANDA = Color.decode("#A4B2D7");

	/**
	 * Returns the maximum value in the specified list of integers.
	 * 
	 * @param v a list of integers
	 * @return the maximum value in the list
	 */
	public static Integer getMaxIntegerValue(List<Integer> v) {
		Integer max = 0;
		for (Integer n : v) {
			if (n > max)
				max = n;
		}
		return max;
	}

	/**
	 * Returns the maximum value in the specified list of doubles.
	 * 
	 * @param v a list of doubles
	 * @return the maximum value in the list
	 */
	public static Double getMaxDoubleValue(List<Double> v) {
		Double max = 0d;
		for (Double n : v) {
			if (n > max)
				max = n;
		}

		return max;
	}

	/**
	 * Draws the specified string.
	 * 
	 * @param g2 a {@code Graphics2D} object
	 * @param string the string to draw
	 * @param yPosition the fixed Y position for the string
	 * @param panel the {@code TracksPanel} in which the string is rendered
	 * @param offset the absolute offset
	 */
	public static void drawString(Graphics2D g2, String string,
		int yPosition, TracksPanel panel, int offset) {

		Color original = g2.getColor();
		g2.setColor(LIGHT_BLACK);
		String toPaint = string.toString();
		FontMetrics fm = g2.getFontMetrics();
		int width = fm.stringWidth(toPaint);

		if (width > 90) {
			int i = toPaint.getBytes().length;
			while (width > 90) {
				toPaint = toPaint.substring(0, i);
				width = fm.stringWidth(toPaint);
				i--;
			}
			toPaint += "[...]";
		}

		g2.drawString(toPaint, panel.getX(12), panel.getY(yPosition - 5));

		int i = 12;
		FileInfo fI = new FileInfo(string);
		GridInfo aux;
		while (i < 13 + fm.stringWidth(toPaint)) {
			aux = new GridInfo(fI, GridInfo.FILENAME);
			panel.addCuadriculaInfo(panel.getX(i),
				panel.getY(offset - panel.getSquareWidth()), aux);
			panel.addCuadriculaInfo(panel.getX(i),
				panel.getY(offset - panel.getSquareWidth()*2), aux);
			i += panel.getSquareWidth();
		}
		g2.setColor(original);
	}
	  	
	/**
	 * Returns the color associated to a nucleotide.
	 * 
	 * @param nucleotide the nucleotide character
	 * @return the associated color
	 */
	public static Color getNucleotideColor(char nucleotide) {
		if ((nucleotide == 'A') || (nucleotide == 'a'))
			return GREEN_APPLE;
		else if ((nucleotide == 'T') || (nucleotide == 't'))
			return RED_PASTEL;
		else if ((nucleotide == 'G') || (nucleotide == 'g'))
			return BROWN_PASTEL;
		else if ((nucleotide == 'C') || (nucleotide == 'c'))
			return BLUE_PASTEL;

		return Color.black;
	}
	  
	/**
	 * Returns a map of correspondences between nucleotides and their associated
	 * colors.
	 * 
	 * @return a map from nucleotides to colors
	 */
	public static HashMap<String, Color> getColors() {
		HashMap<String, Color> colors = new HashMap<String, Color>();

		colors.put("A", GREEN_APPLE);
		colors.put("a", GREEN_APPLE);

		colors.put("T", RED_PASTEL);
		colors.put("t", RED_PASTEL);

		colors.put("G", BROWN_PASTEL);
		colors.put("g", BROWN_PASTEL);

		colors.put("C", BLUE_PASTEL);
		colors.put("c", BLUE_PASTEL);

		colors.put("U", PURPLE_PASTEL);
		colors.put("u", PURPLE_PASTEL);

		colors.put("R", DARK_GREEN_PASTEL);
		colors.put("r", DARK_GREEN_PASTEL);

		colors.put("Y", BLUE_LAVANDA);
		colors.put("y", BLUE_LAVANDA);

		colors.put("M", GREEN_M);
		colors.put("m", GREEN_M);

		colors.put("K", Color.LIGHT_GRAY);
		colors.put("k", Color.LIGHT_GRAY);

		colors.put("S", Color.LIGHT_GRAY);
		colors.put("s", Color.LIGHT_GRAY);

		colors.put("W", Color.LIGHT_GRAY);
		colors.put("w", Color.LIGHT_GRAY);

		colors.put("B", Color.LIGHT_GRAY);
		colors.put("b", Color.LIGHT_GRAY);

		colors.put("D", Color.LIGHT_GRAY);
		colors.put("d", Color.LIGHT_GRAY);

		colors.put("H", Color.LIGHT_GRAY);
		colors.put("h", Color.LIGHT_GRAY);

		colors.put("V", Color.LIGHT_GRAY);
		colors.put("v", Color.LIGHT_GRAY);

		colors.put("N", Color.LIGHT_GRAY);
		colors.put("n", Color.LIGHT_GRAY);

		return colors;
	}

	/**
	 * Saves the specified {@code TracksPanel} as a PNG image.
	 * 
	 * @param tracksPanel a {@code TracksPanel} object
	 */
	public static void saveAsPNG(TracksPanel tracksPanel) {
		try {
			JFileChooser fileChooser = new JFileChooser();
			int seleccion = fileChooser.showSaveDialog(null);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File destFile = fileChooser.getSelectedFile();
				if (!destFile.getName().contains(".png")
					&& !destFile.getName().contains(".PNG")) {
					File f = new File(destFile.getAbsolutePath() + ".png");
					destFile.delete();
					destFile = f;
				}
				ImageIO.write(tracksPanel.getDoubleBuffer(), "png", destFile);
			}
		} catch (IOException ex) {
		}
	}

	/**
	 * Computes the height of a {@code GenomeBrowser}.
	 * 
	 * @param gv a {@code GenomeBrowser} object
	 * 
	 * @return the calculated height
	 */
	public static int computeHeight(GenomeBrowser gv) {
		int toret = 200;
		for (Painter p : gv.getPainters()) {
			toret += p.computeHeight(gv);
		}

		return toret;
	}

	/**
	 * Returns {@code true} if the specified file contains a complete pileup and
	 * {@code false} otherwise.
	 * 
	 * @param file a pileup file.
	 * @return {@code true} if the specified file contains a complete pileup and
	 *         {@code false} otherwise
	 * @throws IOException if an error occurrs while reading the file
	 */
	public static boolean isCompletePileup(File file) throws IOException {
		boolean toret = false;
		BufferedReader br = null;

		if (file.getName().endsWith(".pileup")) {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
		} else if (file.getName().endsWith(".pileup.bgz")) {
			br = new BufferedReader(new InputStreamReader(
				new BlockCompressedInputStream(new FileInputStream(file))));
		} else {
			throw new IllegalArgumentException("The specified file is not a pileup");
		}

		String line = br.readLine();
		while (line != null && line.startsWith("#")) {
			if (line.contains(GPFileConstants.COLNAMES_HEADER))
				break;
			line = br.readLine();
		}
		if (line != null && line.contains(GPFileConstants.COLNAMES_HEADER)) {

		} else if (line != null) {
			StringTokenizer columnsTokens = new StringTokenizer(line);
			LinkedList<String> columnsNamesList = new LinkedList<String>();
			while (columnsTokens.hasMoreTokens()) {
				columnsNamesList.addLast(columnsTokens.nextToken("\t"));
			}
			try {
				Integer.valueOf(columnsNamesList.get(3));
			} catch (NumberFormatException nFe) {
				toret = true;
			}
		}
		return toret;
	}
}
