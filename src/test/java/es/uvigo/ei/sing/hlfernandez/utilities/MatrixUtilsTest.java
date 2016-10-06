package es.uvigo.ei.sing.hlfernandez.utilities;

import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.center;
import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.max;
import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.mean;
import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.min;
import static es.uvigo.ei.sing.hlfernandez.utilities.MatrixUtils.transform;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;

public class MatrixUtilsTest {

	private static final double[][] MATRIX = new double[][] {
			{1d, 2d},
			{1d, 3d},
			{1d, Double.NaN}
	};
	
	private static final double[] MEANS_NO_NANS = 
		new double[] { 1.5d, 2d, 1d};
	private static final double[] MEANS_NANS = 
		new double[] { 1.5d, 2d, Double.NaN};
	private static final double[] MEDIANS_NO_NANS = 
		new double[] { 1.5d, 2d, 1d};
	private static final double[] MEDIANS_NANS = 
		new double[] { 1.5d, 2d, Double.NaN};
	
	@Test
	public void maxTest() {
		double max = max(MATRIX);
		assertEquals(3d, max, 0d);
	}
	
	@Test
	public void minTest() {
		double min = min(MATRIX);
		assertEquals(1d, min, 0d);
	}
	
	@Test
	public void transformTest() {
		Function<Double, Double> square = (d) -> Math.pow(d, 2d);
		
		double[][] expected = new double[][] {
				{1d, 4d},
				{1d, 9d},
				{1d, Double.NaN}
		};
		
		double[][] actual = transform(MATRIX, square);
		
		assertMatrixEquals(expected, actual);
	}

	@Test
	public void meanWithoutNansTest() {
		for (int i = 0; i < MEANS_NO_NANS.length; i++) {
			double mean = mean(MATRIX[i], true);
			assertEquals(MEANS_NO_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void meanWithNansTest() {
		for (int i = 0; i < MEANS_NANS.length; i++) {
			double mean = mean(MATRIX[i], false);
			assertEquals(MEANS_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void medianWithoutNansTest() {
		for (int i = 0; i < MEDIANS_NO_NANS.length; i++) {
			double mean = mean(MATRIX[i], true);
			assertEquals(MEDIANS_NO_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void medianWithNansTest() {
		for (int i = 0; i < MEDIANS_NANS.length; i++) {
			double mean = mean(MATRIX[i], false);
			assertEquals(MEDIANS_NANS[i], mean, 0d);
		}
	}
	
	@Test
	public void meanCenteringTest() {
		double[][] actual = center(MATRIX, MatrixUtils::mean, true);
		
		double[][] expected = new double[][] {
				{-0.5d	, 0.5d},	
				{-1d	, 1d},	
				{0d		, Double.NaN}	
		};
		
		assertMatrixEquals(expected, actual);
	}
	
	private static void assertMatrixEquals(double[][] expected,
		double[][] actual
	) {
		for (int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], actual[i], 0d);
		}
	}
}
