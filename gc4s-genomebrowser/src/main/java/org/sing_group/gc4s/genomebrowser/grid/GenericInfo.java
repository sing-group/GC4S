package org.sing_group.gc4s.genomebrowser.grid;

import java.awt.Color;
import java.util.List;

/**
 * The interface for providing a piece generic information that can be displayed
 * as a tooltip.
 *  
 * @author hlfernandez
 *
 */
public interface GenericInfo {
	/**
	 * Return a string representation.
	 * 
	 * @return a string representation.
	 */
	public String toString();

	/**
	 * Return the color for the tooltip.
	 * 
	 * @return the color for the tooltip
	 */
	public Color getToolTipColor();

	/**
	 * Returns the list of lines to display in the tooltip.
	 * 
	 * @return the list of lines to display in the tooltip
	 */
	public List<String> getLines();
}
