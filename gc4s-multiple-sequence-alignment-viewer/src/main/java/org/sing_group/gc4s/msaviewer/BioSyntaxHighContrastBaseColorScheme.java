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

import static java.awt.Color.decode;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@code DefaultBaseColorScheme} extension that returns the bioSyntax color scheme (https://biosyntax.org/,
 * https://doi.org/10.1186/s12859-018-2315-y).
 * 
 * @author hlfernandez
 *
 */
public class BioSyntaxHighContrastBaseColorScheme extends DefaultBaseColorScheme {
  public static final Map<Character, Color> BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST = new HashMap<>();

  static {
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('A', decode("#d1d1d1"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('G', decode("#fe3101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('C', decode("#010101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('T', decode("#4091fe"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('U', decode("#495bfe"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('R', decode("#f399cd"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('S', decode("#7c0101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('Y', decode("#00007d"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('W', decode("#7cc7fe"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('D', decode("#edb78d"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('V', decode("#fe6331"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('B', decode("#8a4814"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('H', decode("#bed7f8"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('X', decode("#ffffff"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('N', decode("#ffffff"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('M', decode("#fefd7f"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST.put('K', decode("#9208a0"));
  }

  public static final Map<Character, Color> BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND = new HashMap<>();

  static {
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('A', decode("#010101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('G', decode("#010101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('C', decode("#fefefe"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('T', decode("#fefefe"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('U', decode("#fefefe"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('R', decode("#010101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('S', decode("#fefefe"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('Y', decode("#fefefe"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('W', decode("#010101"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('D', decode("#fefefe"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('V', decode("#fefefe"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('B', decode("#fefefe"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('H', decode("#010101"));

    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('X', decode("#828282"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('N', decode("#828282"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('M', decode("#010101"));
    BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND.put('K', decode("#010101"));
  }

  public BioSyntaxHighContrastBaseColorScheme() {
    super(
      asFunction(BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST), asFunction(BIO_SYNTAX_COLOR_SCHEME_HIGH_CONTRAST_FOREGROUND)
    );
  }
}
