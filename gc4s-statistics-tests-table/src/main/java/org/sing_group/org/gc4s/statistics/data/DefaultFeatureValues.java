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

import java.util.List;
import java.util.Map;

/**
 * The default {@code FeatureValues} implementation.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in this set of feature values
 */
public class DefaultFeatureValues<T> implements FeatureValues<T> {
	private String name;
	private Map<String, List<T>> conditionsValues;
	private String[] conditionNames;

	public DefaultFeatureValues(String name,
		Map<String, List<T>> conditionsValues
	) {
		this.name  = name;
		this.conditionsValues = conditionsValues;
		this.conditionNames = conditionsValues.keySet()
			.stream().sorted().toArray(String[]::new);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String[] getConditionNames() {
		return this.conditionNames;
	}

	@Override
	public List<T> getConditionValues(String condition) {
		if (!this.conditionsValues.containsKey(condition)) {
			throw new IllegalArgumentException(
				"Condition " + condition + " does not belong to this feature");
		}
		return conditionsValues.get(condition);
	}
}
