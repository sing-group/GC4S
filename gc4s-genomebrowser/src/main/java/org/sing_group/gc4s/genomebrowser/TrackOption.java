package org.sing_group.gc4s.genomebrowser;

/**
 * An interface for describing track options
 * 
 * @author hlfernandez
 *
 */
public interface TrackOption {

	/**
	 * Returns the option name.
	 * 
	 * @return the option name
	 */
	public String getName();

	/**
	 * Returns the option class.
	 * 
	 * @return the option class
	 */
	public Class<?> getType();

	/**
	 * Returns the option value.
	 * 
	 * @return the option value
	 */
	public Object getValue();

	/**
	 * Sets the option value.
	 * 
	 * @param value the new option value
	 */
	public void setValue(Object value);
}
