package org.sing_group.gc4s.genomebrowser;

import java.io.File;

/**
 * An interface for providing track files.
 * 
 * @author hlfernandez
 *
 */
public interface TrackFileProvider {
	
	/**
	 * Returns the track file.
	 * 
	 * @return the track file
	 */
	public File getTrackFile();
}
