/*
 * #%L
 * GC4S genome browser
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
