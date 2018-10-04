/*
 * #%L
 * GC4S multiple sequence alignment viewer
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
package org.sing_group.gc4s.msaviewer;

import java.awt.Color;
import java.util.Map;
import java.util.function.Function;

/**
 * The default {@code BaseColorScheme} implementation.
 * 
 * @author hlfernandez
 *
 */
public class DefaultBaseColorScheme implements BaseColorScheme {

  private Function<Character, Color> backgroundColorFunction;
  private Function<Character, Color> foregroundColorFunction;

  /**
   * Creates a new {@code DefaultBaseColorScheme} instance with the specified background and foreground color functions.
   * 
   * @param backgroundColorFunction a function that returns a background {@code Color} for each sequence base
   *          {@code Character}
   * @param foregroundColorFunction a function that returns a foreground {@code Color} for each sequence base
   *          {@code Character}
   */
  public DefaultBaseColorScheme(
    Function<Character, Color> backgroundColorFunction, Function<Character, Color> foregroundColorFunction
  ) {
    this.backgroundColorFunction = backgroundColorFunction;
    this.foregroundColorFunction = foregroundColorFunction;
  }

  @Override
  public Function<Character, Color> getBaseBackgroundColorFunction() {
    return this.backgroundColorFunction;
  }

  @Override
  public Function<Character, Color> getBaseForegroundColorFunction() {
    return this.foregroundColorFunction;
  }

  /**
   * Converts a map into a {@code Function}.
   * 
   * @param colorMap the map to be converted
   * @return a function that returns the values associated to the map keys
   */
  public static Function<Character, Color> asFunction(Map<Character, Color> colorMap) {
    return (Character c) -> colorMap.get(c);
  }
}
