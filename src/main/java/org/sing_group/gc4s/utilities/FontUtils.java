package org.sing_group.gc4s.utilities;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * Provides functionalities to deal with fonts.
 * 
 * @author hlfernandez
 *
 */
public class FontUtils {

	/**
	 * Returns an array containing all available fonts in system.
	 * 
	 * @return all available fonts in system.
	 */
	public static Font[] getAvailableFonts() {
		GraphicsEnvironment e = getLocalGraphicsEnvironment();
		return e.getAllFonts();
	}
}
