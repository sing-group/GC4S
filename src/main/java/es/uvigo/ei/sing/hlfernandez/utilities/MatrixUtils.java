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

	/**
	 * Return the maximum value in {@code data} ignoring missing values 
	 * ({@code Double.NaN}).
	 * 
	 * @param matrix a {@code double[][]}.
	 * @return the maximum value in {@code data} ignoring missing values 
	 * 	({@code Double.NaN}).
	 */
	public static double max(double[][] matrix) {
		return 	Stream.of(matrix)
				.mapToDouble(MatrixUtils::max)
				.max().getAsDouble();
	}
	
	/**
	 * Return the maximum value in {@code data} ignoring missing values 
	 * ({@code Double.NaN}).
	 * 
	 * @param array a {@code double[]}.
	 * @return the maximum value in {@code data} ignoring missing values 
	 * 	({@code Double.NaN}).
	 */
	public static double max(double[] array) {
		return 	Arrays.stream(array)
				.filter(d -> !Double.isNaN(d))
				.max().getAsDouble();
	}
	
	/**
	 * Return the minimum value in {@code data} ignoring missing values 
	 * ({@code Double.NaN}).
	 * 
	 * @param matrix a {@code double[][]}.
	 * @return the minimum value in {@code data} ignoring missing values 
	 * 	({@code Double.NaN}).
	 */
	public static double min(double[][] matrix) {
		return 	Stream.of(matrix)
				.mapToDouble(MatrixUtils::min)
				.min().getAsDouble();
	}
	
	/**
	 * Return the minimum value in {@code data} ignoring missing values 
	 * ({@code Double.NaN}).
	 * 
	 * @param array a {@code double[]}.
	 * @return the minimum value in {@code data} ignoring missing values 
	 * 	({@code Double.NaN}).
	 */
	public static double min(double[] array) {
		return 	Arrays.stream(array)
				.filter(d -> !Double.isNaN(d))
				.min().getAsDouble();
	}
}
