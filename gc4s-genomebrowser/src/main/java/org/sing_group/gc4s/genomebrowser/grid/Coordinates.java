package org.sing_group.gc4s.genomebrowser.grid;

/**
 * A class that encapsulates a pair of X and Y coordinates.
 * 
 * @author hlfernandez
 *
 */
public class Coordinates {
	private double x;
	private double y;
	
	public static Coordinates calculateXYCoordinates(double x, double y,
		int size) {
		double coordinateX = (double) x - (x % size);
		double coordinateY = (double) y - (y % size);

		return new Coordinates(coordinateX, coordinateY);
	}

	/**
	 * Creates a new {@code Coordinates} instance with the specified initial
	 * values.
	 * 
	 * @param xValue the x value
	 * @param yValue the y value
	 */
	public Coordinates(double xValue, double yValue) {
		this.x = xValue;
		this.y = yValue;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return (int) (x + y);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Coordinates)) {
			return false;
		}
		Coordinates other = (Coordinates) object;
		if ((other.getX() == this.getX()) && (other.getY() == this.getY()))
			return true;
		else
			return false;
	}

	public String toString() {
		return ("X=" + this.getX() + " ; Y=" + this.getY());
	}
}
