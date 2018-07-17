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

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The default {@code Dataset} implementation. Using this representation, the
 * {@code data[][]} is the matrix of dataset values with samples in columns and
 * features in rows. Thus, the number of rows in the matrix must be equal to the
 * size of the features array and the number of columns equal to the size of the
 * samples array. The conditions array must be also equal to the number of
 * columns, as it provides a condition name for each sample.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in this dataset
 */
public class DefaultDataset<T> implements Dataset<T> {
	private String[] features;
	private String[] samples;
	private String[] conditions;
	private T[][] data;
	private Map<String, String> samplesConditions;
	private Map<String, Integer> samplesIndexes;
	private Map<String, Integer> featuresIndexes;

	/**
	 * Creates a new {@code DefaultDataset} instance with the specified initial
	 * values.
	 *
	 * @param features the names of the features in the dataset (i.e. the names
	 *        of the rows in {@code data[][]})
	 * @param samples the names of the the samples in the dataset (i.e. the
	 *        names of the columns in {@code data[][]})
	 * @param data the matrix of values
	 * @param conditions the names of the conditions in the dataset (i.e. the
	 *        condition name for each sample in the dataset)
	 */
	public DefaultDataset(String[] features, String[] samples, T[][] data,
		String[] conditions
	) {
		this.features = features;
		this.samples = samples;
		this.data = data;
		this.conditions = conditions;

		this.checkData();
		this.initializeMaps();
	}

	private void checkData() {
		if(features.length == 0) {
			throw new IllegalArgumentException(
				"features can't be empty");
		}

		if (features.length != data.length) {
			throw new IllegalArgumentException(
				"features length must be equals to the number of rows in data");
		}

		if(samples.length == 0) {
			throw new IllegalArgumentException(
				"samples can't be empty");
		}

		if (samples.length != data[0].length) {
			throw new IllegalArgumentException(
				"samples length must be equals to the number of columns in data");
		}

		if (conditions.length != data[0].length) {
			throw new IllegalArgumentException(
				"conditions length must be equals to the number of columns in data");
		}
	}

	private void initializeMaps() {
		this.samplesConditions = new HashMap<>();
		for(int i = 0; i < this.samples.length; i++) {
			this.samplesConditions.put(this.samples[i], conditions[i]);
		}

		this.samplesIndexes = new HashMap<>();
		for(int i = 0; i < this.samples.length; i++) {
			this.samplesIndexes.put(this.samples[i], i);
		}

		this.featuresIndexes = new HashMap<>();
		for(int i = 0; i < this.features.length; i++) {
			this.featuresIndexes.put(this.features[i], i);
		}
	}

	@Override
	public String[] getFeatures() {
		return this.features;
	}

	@Override
	public String[] getConditionNames() {
		return this.conditions;
	}

	@Override
	public Set<String> getUniqueConditionNames() {
		return new HashSet<>(asList(this.conditions));
	}

	@Override
	public String[] getSamples() {
		return this.samples;
	}

	@Override
	public T getValue(String sample, String feature) {
		this.checkSample(sample);

		this.checkFeature(feature);

		return data
				[this.featuresIndexes.get(feature)]
				[this.samplesIndexes.get(sample)];
	}

	@Override
	public String getCondition(String sample) {
		return this.samplesConditions.get(sample);
	}

	@Override
	public FeatureValues<T> getFeatureValues(String feature) {
		this.checkFeature(feature);

		Map<String, List<T>> conditionsValues = new HashMap<>();

		for(int i = 0; i < samples.length; i++) {
			String sampleCondition = getCondition(samples[i]);
			T value = getValue(samples[i], feature);
			conditionsValues.putIfAbsent(sampleCondition, new LinkedList<>());
			conditionsValues.get(sampleCondition).add(value);
		}

		return new DefaultFeatureValues<>(feature, conditionsValues);
	}

	private void checkSample(String sample) {
		if (!this.samplesIndexes.containsKey(sample)) {
			throw new IllegalArgumentException(
				"Sample " + sample + " does not belong to this dataset");
		}
	}

	private void checkFeature(String feature) {
		if (!this.featuresIndexes.containsKey(feature)) {
			throw new IllegalArgumentException(
				"Feature " + feature + " does not belong to this dataset");
		}
	}
}
