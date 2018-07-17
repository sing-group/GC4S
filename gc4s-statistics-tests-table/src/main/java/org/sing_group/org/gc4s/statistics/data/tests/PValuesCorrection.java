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
package org.sing_group.org.gc4s.statistics.data.tests;

import static java.util.Optional.empty;

import java.util.Optional;

import es.uvigo.ei.sing.math.statistical.corrections.Correction;

/**
 * The interface that defines a statistical correction that is applied to
 * correct a set of p-values.
 *
 * @author hlfernandez
 *
 */
public interface PValuesCorrection extends Correction {
	/**
	 * Returns the name of the correction.
	 *
	 * @return the name of the correction
	 */
	public String getName();

	/**
	 * Returns the description of the correction wrapped as an {@code Optional}.
	 *
	 * @return the description of the correction
	 */
	public default Optional<String> getDescription() {
		return empty();
	}

	/**
	 * Returns the additional information URL of the correction wrapped as an
	 * {@code Optional}.
	 *
	 * @return the additional information URL of the correction
	 */
	public default Optional<String> getAdditionalInfoUrl() {
		return empty();
	}
}
