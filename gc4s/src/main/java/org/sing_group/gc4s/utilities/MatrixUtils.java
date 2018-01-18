package org.sing_group.gc4s.utilities;

import static java.util.stream.DoubleStream.of;

import java.util.Arrays;
import java.util.List;
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

	/**
	 * Removes column at the specified index from {@code data} matrix.
	 *
	 * @param column the column index to remove.
	 * @param data the data matrix.
	 * @return a new data matrix without the specified column.
	 */
	public static Object[][] removeColumn(int column, Object[][] data) {
		if(column < 0 || column > data[0].length) {
			throw new IllegalArgumentException("Invalid column " + column);
		}

		Object[][] toret = new Object[data.length][data[0].length-1];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0, destCol = 0; j < data[i].length; j++) {
				if (j != column) {
					toret[i][destCol++] = data[i][j];
				}
			}
		}

		return toret;
	}

	/**
	 * Converts {@code matrix} into a {@code String[][]}.
	 *
	 * @param matrix a {@code Object[][]} matrix.
	 * @return a {@code String[][]} matrix.
	 */
	public static String[][] asStringMatrix(Object[][] matrix) {
		String[][] toret = new String[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i++) {
			toret[i] = Arrays.stream(matrix[i]).map(Object::toString).toArray(String[]::new);
		}

		return toret;
	}

	/**
	 * Converts a list of {@code Double} into a primitive array of
	 * {@code double}.
	 * 
	 * @param list a list of {@code Double}
	 * @return a primitive array with the contents of the list.
	 */
	public static double[] asPrimitiveArray(List<Double> list) {
		double[] toret = new double[list.size()];
		for (int i = 0; i < toret.length; i++) {
			toret[i] = list.get(i);
		}
		return toret;
	}
}
