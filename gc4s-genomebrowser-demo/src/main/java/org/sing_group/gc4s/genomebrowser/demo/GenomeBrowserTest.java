package org.sing_group.gc4s.genomebrowser.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.sing_group.gc4s.genomebrowser.GenomeBrowser;
import org.sing_group.gc4s.visualization.VisualizationUtils;

import es.cnio.bioinfo.pileline.refgenomeindex.PileLineGenomeIndex;

public class GenomeBrowserTest {

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
		VisualizationUtils.setNimbusLookAndFeel();

		try {
			GenomeBrowser genomeBrowser = new GenomeBrowser(
				new PileLineGenomeIndex(PILELINE_INDEX_FILE)
			);
			VisualizationUtils.showComponent(genomeBrowser);

			genomeBrowser.setSequence("22");
			genomeBrowser.seekPositions(14880570, 18748443);

			Thread.sleep(1000);
			genomeBrowser.addTrack(BED_FILE);

			Thread.sleep(1000);
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
