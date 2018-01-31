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
package org.sing_group.gc4s.genomebrowser.grid;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A {@code GenericInfo} implementation for providing pileup information.
 * 
 * @author hlfernandez
 *
 */
public class PileupInfo implements GenericInfo{
	private String info;
	private Color color;
	private String ref;
	private String cons;
	private String phredCons;
	private String snpQuality;
	private String rmsReads;
	private String depth;
	private String readBases;
	private String readQualities;
	private boolean isCompletePileup;

	/**
	 * Creates a new {@code PileupInfo} instance for the pileup line.
	 * 
	 * @param line the pileup line to parse
	 * @param color the tooltip color
	 * @param complete whether the pileup is complete or not
	 */
	public PileupInfo(String line, Color color, boolean complete) {
		this.info = line;
		this.color = color;
		this.isCompletePileup = complete;
		parsePileupLine(this.info);
	}

	@Override
	public String toString() {
		return "Ref = " + ref;
	}

	@Override
	public Color getToolTipColor() {
		return this.color;
	}

	@Override
	public List<String> getLines() {
		List<String> toret = new LinkedList<String>();
		toret.add(new String("Ref = " + ref));
		if (isCompletePileup) {
			toret.add(new String("Cons. = " + cons));
		}

		if (isCompletePileup) {
			toret.add(new String("phredCons = " + phredCons));
		}
		
		if (isCompletePileup) {
			toret.add(new String("snpQuality = " + snpQuality));
		}

		if (isCompletePileup) {
			toret.add(new String("rmsReads = " + rmsReads));
		}

		toret.add(new String("depth = " + depth));

		toret.add(new String("readBases = " + readBases));
		toret.add(new String("readQualities = " + readQualities));

		return toret;
	}

	protected void parsePileupLine(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		tokenizer.nextToken();
		tokenizer.nextToken();
		if (isCompletePileup) {
			ref = tokenizer.nextToken();
			cons = tokenizer.nextToken();
			phredCons = tokenizer.nextToken();
			snpQuality = tokenizer.nextToken();
			rmsReads = tokenizer.nextToken();
			depth = tokenizer.nextToken();
			readBases = tokenizer.nextToken();
			readQualities = tokenizer.nextToken();
		} else {
			ref = tokenizer.nextToken();
			cons = "N\\A";
			phredCons = "N\\A";
			snpQuality = "N\\A";
			rmsReads = "N\\A";
			depth = tokenizer.nextToken();
			readBases = tokenizer.nextToken();
			readQualities = tokenizer.nextToken();
		}
	}
}
