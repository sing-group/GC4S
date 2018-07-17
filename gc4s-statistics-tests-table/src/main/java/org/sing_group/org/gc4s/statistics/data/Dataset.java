/*
 * #%L
 * GC4S statistics tests table
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
package org.sing_group.org.gc4s.statistics.data;

import java.util.Set;

/**
 * The interface for defining a dataset.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in this dataset
 */
public interface Dataset<T> {
	/**
	 * Returns an array with the names of the features in the dataset.
	 *
	 * @return an array with the names of the features in the dataset
	 */
	public String[] getFeatures();

	/**
	 * Returns an array with the names of the samples in the dataset.
	 *
	 * @return an array with the names of the samples in the dataset
	 */
	public String[] getSamples();

	/**
	 * Returns an array with the conditions of each sample in the dataset. Note
	 * that this array must have the same length than the one returned  by
	 * {@link Dataset#getSamples()}, since each position is related to the
	 * corresponding sample.
	 *
	 * @return an array with the conditions of each sample in the dataset
	 */
	public String[] getConditionNames();

	/**
	 * Returns a set containing the unique condition names.
	 *
	 * @return a set containing the unique condition names
	 */
	public Set<String> getUniqueConditionNames();

	/**
	 * Returns the value associated with the specified {@code sample} and
	 * {@code feature}.
	 *
	 * @param sample the sample name
	 * @param feature the feature name
	 * @return the value associated with the specified sample and feature
	 */
	public T getValue(String sample, String feature);

	/**
	 * Returns the name of the condition associated with the specified
	 * {@code sample}.
	 *
	 * @param sample the sample name
	 * @return the name of the condition associated with the specified sample
	 */
	public String getCondition(String sample);


	/**
	 * Returns a {@code FeatureValues} object that represents the values of the
	 * specified {@code feature}.
	 *
	 * @param feature the feature name
	 * @return a {@code FeatureValues} object associated with the specified
	 * 		   feature
	 */
	public FeatureValues<T> getFeatureValues(String feature);
}
