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

import static java.util.Optional.of;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;

/**
 * A {@code DefaultSequenceAlignmentRenderer} implementation that renders each
 * sequence base using the color specified by the {@code BaseColorScheme}.
 * 
 * @author hlfernandez
 * @see BaseColorScheme
 * @see DefaultSequenceAlignmentRenderer
 *
 */
public class BaseColorSequenceAlignmentRenderer extends DefaultSequenceAlignmentRenderer {
  private Function<Character, Color> backgroundColorProvider;
  private Function<Character, Color> foregroundColorProvider;

  /**
   * Creates a new {@code BaseColorSequenceAlignmentRenderer} with the specified {@code colorScheme}.
   * 
   * @param colorScheme the {@code BaseColorScheme} to render sequence bases
   */
  public BaseColorSequenceAlignmentRenderer(
    BaseColorScheme colorScheme
  ) {
    this.backgroundColorProvider = colorScheme.getBaseBackgroundColorFunction();
    this.foregroundColorProvider = colorScheme.getBaseForegroundColorFunction();
  }

  @Override
  public Optional<SequenceBaseRenderingInfo> render(Sequence sequence, int position) {
    Character base = sequence.getSequence().charAt(position);
    return of(
      new SequenceBaseRenderingInfo(this.backgroundColorProvider.apply(base), this.foregroundColorProvider.apply(base), false, false)
    );
  }
}
