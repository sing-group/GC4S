package es.uvigo.ei.sing.hlfernandez.utilities;

import static java.util.stream.DoubleStream.of;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;
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
	
	/**
	 * Transforms the {@code data} matrix by applying the {@code transformation}
	 * function to each single value.
	 * 
	 * @param matrix the data matrix to be transformed.
	 * @param transformation the transformation function.
	 * @return the transformed data matrix.
	 */
	public static double[][] transform(double[][] matrix,
		Function<Double, Double> transformation
	) {
		double[][] transformed = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			transformed[i] = of(matrix[i]).map(transformation::apply).toArray();
		}
		return transformed;
	}
	
	/**
	 * Centers the {@code data} matrix by applying the {@code computeCenter}
	 * function to each row. If {@code excludeNan} is {@code true}, then
	 * {@code Double.NaN} values are excluded.
	 * 
	 * @param matrix the data matrix to be centered.
	 * @param centering the function that calculates the center of each row.
	 * @param excludeNan if {@code true}, then {@code Double.NaN} values are 
	 * 			excluded.
	 * @return the centered data array.
	 */
	public static double[][] center(double[][] matrix,
		BiFunction<double[], Boolean, Double> centering, boolean excludeNan
	) {
		double[][] centered = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			centered[i] = center(matrix[i], centering, excludeNan);
		}
		return centered;
	}
	
	/**
	 * Centers the {@code data} array by applying the {@code computeCenter}
	 * function. If {@code excludeNan} is {@code true}, then {@code Double.NaN}
	 * values are excluded.
	 * 
	 * @param data the data array to be centered.
	 * @param computeCenter the function that calculates the center.
	 * @param excludeNan if {@code true}, then {@code Double.NaN} values are 
	 * 			excluded.
	 * @return the centered data array.
	 */
	private static double[] center(double[] data,
		BiFunction<double[], Boolean, Double> computeCenter, boolean excludeNan
	) {
		double center = computeCenter.apply(data, excludeNan);
		double[] centered = new double[data.length];
		for(int i = 0; i < data.length; i++) {
			centered[i] = data[i] - center;
		}
		return centered;
	}
	
	/**
	 * Returns the mean value of {@code data}. If {@code excludeNan} is
	 * {@code true}, then {@code Double.NaN} values are excluded.
	 * 
	 * @param data a data array.
	 * @param excludeNan if {@code true}, then {@code Double.NaN} values are 
	 * 			excluded.
	 * @return the mean value of {@code data}.
	 */
	public static double mean(double[] data, boolean excludeNan) {
		double sum = 0;
		if(excludeNan) {
			data = removeNan(data);
		}
		for (int i = 0; i < data.length; i++) {
			sum += data[i];
		}
		return sum / data.length;
	}
	
	/**
	 * Returns the median value of {@code data}. If {@code excludeNan} is
	 * {@code true}, then {@code Double.NaN} values are excluded.
	 * 
	 * @param data a data array.
	 * @param excludeNan if {@code true}, then {@code Double.NaN} values are 
	 * 			excluded.
	 * @return the median value of {@code data}.
	 */
	public static double median(double[] data, boolean excludeNan) {
		if(excludeNan) {
			data = removeNan(data);
		}
		Arrays.sort(data);
		double median;
		if (data.length % 2 == 0) {
			median = ((double) data[data.length / 2] + (double) data[data.length / 2 - 1]) / 2;
		} else {
			median = (double) data[data.length / 2];
		}
		return median;
	}

	private static double[] removeNan(double[] data) {
		return 	DoubleStream.of(data)
				.filter(d -> !Double.isNaN(d)).toArray();
	}
}
