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
public class BioSyntaxBaseColorScheme extends DefaultBaseColorScheme {
  public static final Map<Character, Color> BIO_SYNTAX_COLOR_SCHEME = new HashMap<>();

  static {
    BIO_SYNTAX_COLOR_SCHEME.put('A', decode("#47ff19"));
    BIO_SYNTAX_COLOR_SCHEME.put('G', decode("#f09000"));
    BIO_SYNTAX_COLOR_SCHEME.put('C', decode("#ff4641"));
    BIO_SYNTAX_COLOR_SCHEME.put('T', decode("#4192ff"));

    BIO_SYNTAX_COLOR_SCHEME.put('U', decode("#8a89ff"));

    BIO_SYNTAX_COLOR_SCHEME.put('R', decode("#fffe80"));
    BIO_SYNTAX_COLOR_SCHEME.put('S', decode("#ff9b80"));
    BIO_SYNTAX_COLOR_SCHEME.put('Y', decode("#e180ff"));
    BIO_SYNTAX_COLOR_SCHEME.put('W', decode("#80fff2"));

    BIO_SYNTAX_COLOR_SCHEME.put('D', decode("#c7ffb9"));
    BIO_SYNTAX_COLOR_SCHEME.put('V', decode("#ffe3b9"));
    BIO_SYNTAX_COLOR_SCHEME.put('B', decode("#f8c1c0"));
    BIO_SYNTAX_COLOR_SCHEME.put('H', decode("#bfd8f9"));

    BIO_SYNTAX_COLOR_SCHEME.put('X', decode("#e6e6e6"));
    BIO_SYNTAX_COLOR_SCHEME.put('N', decode("#ffffff"));
    BIO_SYNTAX_COLOR_SCHEME.put('M', decode("#83831f"));
    BIO_SYNTAX_COLOR_SCHEME.put('K', decode("#8b4915"));
  }

  /**
   * Creates a new {@code BioSyntaxBaseColorScheme} instance.
   */
  public BioSyntaxBaseColorScheme() {
    super(asFunction(BIO_SYNTAX_COLOR_SCHEME), (Character c) -> Color.BLACK);
  }
}
