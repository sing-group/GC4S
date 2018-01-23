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
