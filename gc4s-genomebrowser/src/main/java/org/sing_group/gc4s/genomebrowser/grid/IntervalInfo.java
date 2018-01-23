package org.sing_group.gc4s.genomebrowser.grid;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code GenericInfo} implementation for providing interval information.
 * 
 * @author hlfernandez
 *
 */
public class IntervalInfo implements GenericInfo {
	private String data;
	private String start;
	private String stop;
	private Color color;

	/**
	 * Creates a new {@code IntervalInfo} started with the specified values.
	 * 
	 * @param data the interval data
	 * @param start the interval start
	 * @param end the interval end
	 */
	public IntervalInfo(String data, String start, String end) {
		this.data = data;
		this.start = start;
		this.stop = end;
		this.color = Color.LIGHT_GRAY;
	}

	/**
	 * Creates a new {@code IntervalInfo} started with the specified values.
	 * 
	 * @param data the interval data
	 * @param start the interval start
	 * @param end the interval end
	 * @param color the tooltip color
	 */
	public IntervalInfo(String data, String start, String end, Color color) {
		this.data = data;
		this.start = start;
		this.stop = end;
		this.color = color;
	}

	@Override
	public String toString() {
		return ("(" + this.start + "," + this.stop + ")=" + this.data);
	}

	@Override
	public Color getToolTipColor() {
		return this.color;
	}

	@Override
	public List<String> getLines() {
		return Arrays.asList(this.toString());
	}
}
