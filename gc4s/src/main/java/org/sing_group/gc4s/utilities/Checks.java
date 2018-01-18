package org.sing_group.gc4s.utilities;

import java.util.function.IntFunction;

/**
 * 
 * A class that provides additional checks to use in component constructors.
 * 
 * @author hlfernandez
 *
 */
public class Checks {
	/**
	 * Checks if the provided number is greater than 0.
	 * 
	 * @param number the number to be checked.
	 * @param message the message of the exception thrown if the number is
	 * lower than or equals to 0.
	 * @return the provided number.
	 * @throws IllegalArgumentException if the number is lower than 0 or equals
	 * to 0. The message of this exception will be the provided.
	 */
	public static int requireStrictPositive(int number, String message) {
		return check(number, (int x) -> x > 0, message);
	}

	/**
	 * Checks if the provided number is greater than or 0.
	 * 
	 * @param number the number to be checked.
	 * @return the provided number.
	 * @throws IllegalArgumentException if the number is lower than or equals
	 * to 0.
	 */
	public static int requireStrictPositive(int number) {
		return requireStrictPositive(number, "number must be greater than 0");
	}

	private static int check(int value, IntFunction<Boolean> validator,
		String message
	) {
		if (validator.apply(value))
			return value;
		else
			throw new IllegalArgumentException(message);
	}
}
