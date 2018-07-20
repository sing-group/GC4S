/*
 * #%L
 * GC4S genome browser demo
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
package org.sing_group.gc4s.genomebrowser.demo;

import static java.lang.Thread.sleep;
import static org.sing_group.gc4s.visualization.VisualizationUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.sing_group.gc4s.genomebrowser.GenomeBrowser;

import es.cnio.bioinfo.pileline.refgenomeindex.GenomeIndex;
import es.cnio.bioinfo.pileline.refgenomeindex.PileLineGenomeIndex;

// This class shows the basic usage of the GenomeBrowser component using real
// data files.
public class GenomeBrowserDemo {
	public static URL INDEX_URL;
	public static URL BED_URL;
	public static URL BAM_URL;
	public static URL BAM_INDEX_URL;

	static {
		try {
			INDEX_URL = new URL("http://static.sing-group.org/software/GC4S/data/genomebrowser/chr22.fa.pilelineindex");
			BED_URL = new URL("http://static.sing-group.org/software/GC4S/data/genomebrowser/hg18_hgnc_ensembl_genes_chr22.bed");
			BAM_URL = new URL("http://static.sing-group.org/software/GC4S/data/genomebrowser/track.bam");
			BAM_INDEX_URL = new URL("http://static.sing-group.org/software/GC4S/data/genomebrowser/track.bam.bai");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final File DATA_DIR = new File("target/data");
	public static final File PILELINE_INDEX_FILE = new File(DATA_DIR, "chr22.fa.pilelineindex");
	public static final File BED_FILE = new File(DATA_DIR, "track.bed");
	public static final File BAM_FILE = new File(DATA_DIR, "track.bam");
	public static final File BAM_INDEX_FILE = new File(DATA_DIR, "track.bam.bai");

	public static void main(String[] args) throws IOException {
		downloadTestData();

		setNimbusLookAndFeel();

		try {
			// Creation of the GenomeIndex object required by the GenomeBrowser
			// to work.
			GenomeIndex genomeIndex = new PileLineGenomeIndex(
				PILELINE_INDEX_FILE);

			// Instantiation and visualization of the GenomeBrowser, which only
			// requires a GenomeIndex to be created.
			GenomeBrowser genomeBrowser = new GenomeBrowser(genomeIndex);
			showComponent(genomeBrowser);

			// Establishment of the sequence and positions to be shown. They
			// can also be established manually by the user trough the GUI
			// component.
			genomeBrowser.setSequence("22");
			genomeBrowser.seekPositions(14880570, 18748443);

			// Addition of file tracks to the GenomeBrowser programmatically.
			sleep(1000);
			genomeBrowser.addTrack(BED_FILE);

			sleep(1000);
			genomeBrowser.addTrack(BAM_FILE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void downloadTestData() throws IOException {
		if (!DATA_DIR.exists()) {
			DATA_DIR.mkdirs();
		}

		downloadUrlIfNotExists(INDEX_URL, PILELINE_INDEX_FILE);
		downloadUrlIfNotExists(BED_URL, BED_FILE);
		downloadUrlIfNotExists(BAM_URL, BAM_FILE);
		downloadUrlIfNotExists(BAM_INDEX_URL, BAM_INDEX_FILE);
	}

	private static void downloadUrlIfNotExists(URL url, File file)
			throws IOException {
		if (!file.exists()) {
			System.err.print("Downloading file " + file.getName() + " ...");
			try (InputStream in = url.openStream()) {
				Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			System.err.println(" [DONE]");
		}
	}
}
