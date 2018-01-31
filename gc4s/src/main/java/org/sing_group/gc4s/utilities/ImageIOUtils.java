/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.utilities;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Provides functionalities to deal with {@link ImageIO}.
 * 
 * @author hlfernandez
 *
 */
public class ImageIOUtils {
	
	/**
	 * <p>
	 * Exports the given {@code component} as a image into {@code file}.
	 * </p>
	 * 
	 * <p>The image format is specified by {@code format}, which must be one
	 * of the available formats in the system (they can be obtained with
	 * {@link ImageIO#getWriterFormatNames()}.</p>
	 * 
	 * @param component the component to save.
	 * @param format the image format.
	 * @param file the file to save the image.
	 * @throws IOException if an error occurs while saving the image.
	 */
	public static void toImage(JComponent component, String format, File file)
		throws IOException {

		int w = component.getWidth();
		int h = component.getHeight();

		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		component.paint(g2);
		g2.dispose();

		ImageIO.write(bi, format, file);
	}

	/**
	 * <p>
	 * Exports the given {@code components} as a image into {@code file}.
	 * </p>
	 * 
	 * <p>The image format is specified by {@code format}, which must be one
	 * of the available formats in the system (they can be obtained with
	 * {@link ImageIO#getWriterFormatNames()}.</p>
	 * 
	 * @param components the components to save.
	 * @param format the image format.
	 * @param file the file to save the image.
	 * @throws IOException if an error occurs while saving the image.
	 */
	public static void toImage(String format, File file,
		Component... components
	) throws IOException {

		int weight = 0;
		int height = 0;
		for (Component component : components) {
			weight = Math.max(weight, component.getWidth());
			height = height + component.getHeight();
		}

		List<BufferedImage> bis = toBufferedImages(components);

		BufferedImage bi = new BufferedImage(weight, height, TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();

		int y = 0;
		for (BufferedImage image : bis) {
			int x = (weight - image.getWidth()) / 2;
			g2.drawImage(
				image, x, y, image.getWidth(), image.getHeight(), null);
			y = y + image.getHeight();
		}
		g2.dispose();

		ImageIO.write(bi, format, file);
	}

	private static List<BufferedImage> toBufferedImages(
		Component... components) {
		List<BufferedImage> toret = new ArrayList<>();
		for (Component component : components) {
			BufferedImage bi = new BufferedImage(component.getWidth(),
				component.getHeight(), TYPE_INT_ARGB);
			Graphics2D g2 = bi.createGraphics();
			component.paint(g2);
			g2.dispose();
			toret.add(bi);
		}

		return toret;
	}
}
