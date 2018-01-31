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

import java.io.File;

/**
 * A factory to create the appropriate {@code Painter} for each file type.
 * 
 * @author hlfernandez
 *
 */
public class PainterFactory {

	/**
	 * Returns an adequate painter implementation for the specified file type 
	 * based on the file extension.
	 * 
	 * @param f the track file
	 * @return a {@code Painter} object
	 */
	public static Painter getPainter(File f) {
		String fileName = f.getName();
		if (fileName.endsWith(".pileup") || fileName.endsWith(".pileup.bgz")) {
			return new PileupPainter(f);
		} else if (fileName.endsWith("bed") || fileName.endsWith(".bed.bgz")
			|| fileName.endsWith("gff") || fileName.endsWith("gff.bgz")) {
			return new IntervalsPainter(f);
		} else if (fileName.endsWith("bam")) {
			return new BamIntervalsPainter(f);
		} else
			return new GPPainter(f);
	}
}
