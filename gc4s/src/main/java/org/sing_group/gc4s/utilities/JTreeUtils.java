/*
 * #%L
 * GC4S components
 * %%
 * Copyright (C) 2014 - 2019 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
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
package org.sing_group.gc4s.utilities;

import javax.swing.JTree;

/**
 * Utility methods for manipulating {@code JTree} objects.
 * 
 * @author hlfernandez
 *
 */
public class JTreeUtils {

  /**
   * Collapses all nodes of the tree.
   * 
   * @param tree a {@code JTree}
   */
  public static void collapseAll(JTree tree) {
    for (int i = 0; i < tree.getRowCount(); i++) {
      tree.collapseRow(i);
    }
  }

  /**
   * Expands all nodes of the tree.
   * 
   * @param tree a {@code JTree}
   */
  public static void expandAll(JTree tree) {
    for (int i = 0; i < tree.getRowCount(); i++) {
      tree.expandRow(i);
    }
  }
}
