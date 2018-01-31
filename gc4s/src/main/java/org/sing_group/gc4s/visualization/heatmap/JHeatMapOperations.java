/*
 * #%L
 * GC4S components
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
