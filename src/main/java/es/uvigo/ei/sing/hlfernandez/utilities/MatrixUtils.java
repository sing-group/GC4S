package es.uvigo.ei.sing.hlfernandez.utilities;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Provides functionalities to deal with data matrices.
 * 
 * @author hlfernandez
 *
 */
public class MatrixUtils {

	public static double max(double[][] data) {
		return 	Stream.of(data)
				.map(a -> Arrays.stream(a).max())
				.mapToDouble(o -> o.getAsDouble())
				.max().getAsDouble();
	}
	
	public static double min(double[][] data) {
		return 	Stream.of(data)
				.map(a -> Arrays.stream(a).min())
				.mapToDouble(o -> o.getAsDouble())
				.min().getAsDouble();
	}
}
