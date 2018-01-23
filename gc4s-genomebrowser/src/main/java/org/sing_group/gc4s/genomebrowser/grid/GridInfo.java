package org.sing_group.gc4s.genomebrowser.grid;

/**
 * A class that encapsulates a {@code GenericInfo} associated to a specific
 * {@code Coordinates} location.
 * 
 * @author hlfernandez
 *
 */
public class GridInfo {
	public final static int FILENAME = 0;
	public final static int TRACKPOSITION = 1;
	public final static int TRACKINFO = 2;
	public final static int GENOMEBROWSERINFO = 3;
	public final static int INTERVALINFO = 4;
	public final static int PILEUPINFO = 5;

	private Coordinates coordinates;
	private int type;
	private GenericInfo data;

	/**
	 * Creates a new {@code GridInfo} instance with the specified initial
	 * values.
	 * 
	 * @param data a {@code GenericInfo} object
	 * @param type the type of information
	 */
	public GridInfo(GenericInfo data, int type) {
		this.data = data;
		this.type = type;
	}

	/**
	 * Returns the type of the information contained.
	 * 
	 * @return the type of the information contained
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the {@code Coordinates}.
	 * 
	 * @return the {@code Coordinates}
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Sets the component coordinates in which the information should be shown.
	 * 
	 * @param c a {@code Coordinates} object
	 */
	public void setCoordinates(Coordinates c) {
		coordinates = c;
	}

	/**
	 * Returns the {@code GenericInfo}.
	 * 
	 * @return the {@code GenericInfo}
	 */
	public GenericInfo getData() {
		return data;
	}
}
