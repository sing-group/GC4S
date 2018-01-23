package org.sing_group.gc4s.genomebrowser.grid;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code GenericInfo} implementation for providing genome browser
 * information.
 * 
 * @author hlfernandez
 *
 */
public class GenomeBrowserInfo implements GenericInfo {
	public static final Color TOOLTIP_COLOR = Color.decode("#F3BC6B");
	
	private String lengh;
	private String from;
	private String to;
	private String chromosome;
	public NumberFormat formatter = new DecimalFormat("#,###,###"); 

	/**
	 * Creates a new {@code GenomeBrowserInfo} with the specified values.
	 * 
	 * @param length the chromosome length
	 * @param from the initial base
	 * @param to the end base
	 * @param chromosome the chromosome
	 */
	public GenomeBrowserInfo(String length, String from, String to,
		String chromosome
	) {
		this.lengh = length;
		this.from = from;
		this.to = to;
		this.chromosome = chromosome;
	}

	@Override
	public String toString() {
		return ("Viewing " + lengh + "bases at chromosome " + this.chromosome
			+ " from " + from + " to " + to);
	}

	@Override
	public Color getToolTipColor() {
		return TOOLTIP_COLOR;
	}

	@Override
	public List<String> getLines() {
		return Arrays.asList(this.toString());
	}
}
