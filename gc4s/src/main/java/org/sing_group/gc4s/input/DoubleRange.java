package org.sing_group.gc4s.input;

/**
 * This class represents a range (i.e. a pair of a minimun and a maximum) of
 * {@code double}.
 * 
 * @author hlfernandez
 *
 */
public class DoubleRange {

	private double min;
	private double max;

	/**
	 * Creates a new {@code DoubleRange}.
	 * 
	 * @param min the minimun value.
	 * @param max the maximum value.
	 */
	public DoubleRange(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Return the minimum value of the range.
	 * @return the minimum value of the range.
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Return the maximum value of the range.
	 * @return the maximum value of the range.
	 */
	public double getMax() {
		return max;
	}
	
	@Override
	public String toString() {
		return "[" + getMin() + ", " + getMax() + "]";
	}
}
