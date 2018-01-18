package org.sing_group.gc4s.visualization.heatmap;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.sing_group.gc4s.utilities.MatrixUtils;

/**
 * This class stores different types operations that can be applied to heatmap
 * data.
 * 
 * @author hlfernandez
 *
 */
public class JHeatMapOperations {

	public static enum Transform {
		LOG2("Log 2", Math::log),
		NONE("None", (e) -> e);
		
		private String name;
		private Function<Double, Double> function;

		Transform(String name, Function<Double, Double> function) {
			this.name = name;
			this.function = function;
		}

		public String getName() {
			return name;
		}

		public Function<Double, Double> getFunction() {
			return function;
		}
	}

	public static enum Centering {
		MEAN("Mean", MatrixUtils::mean),
		MEDIAN("Median", MatrixUtils::median),
		NONE("None", (a,b) -> 0d);

		private BiFunction<double[], Boolean, Double> function;
		private String name;

		Centering(String name, BiFunction<double[], Boolean, Double> function) {
			this.name = name;
			this.function = function;
		}

		public String getName() {
			return name;
		}

		public BiFunction<double[], Boolean, Double> getFunction() {
			return function;
		}
	};
	
	public static double[][] center(double[][] matrix, Centering centering,
		boolean excludeNan
	) {
		return MatrixUtils.center(matrix, centering.getFunction(), excludeNan);
	}

	public static double[][] transform(double[][] matrix, Transform transform) {
		return MatrixUtils.transform(matrix, transform.getFunction());
	}
}
