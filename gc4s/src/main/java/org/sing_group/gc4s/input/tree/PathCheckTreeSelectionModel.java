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
package org.sing_group.gc4s.input.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

public class PathCheckTreeSelectionModel extends DefaultTreeSelectionModel {
  private static final long serialVersionUID = 1L;

  private boolean canPathBeAdded(TreePath treePath) {
    return ((DefaultMutableTreeNode) treePath.getLastPathComponent()).isLeaf();
  }

  private TreePath[] getFilteredPaths(TreePath[] paths) {
    List<TreePath> returnedPaths = new ArrayList<TreePath>(paths.length);
    for (TreePath treePath : paths) {
      if (canPathBeAdded(treePath)) {
        returnedPaths.add(treePath);
      }
    }
    return returnedPaths.toArray(new TreePath[returnedPaths.size()]);
  }
  
  @Override
  public void setSelectionPath(TreePath path) {
      if (canPathBeAdded(path)) {
          super.setSelectionPath(path);
      }
  }

  @Override
  public void setSelectionPaths(TreePath[] paths) {
      paths = getFilteredPaths(paths);
      super.setSelectionPaths(paths);
  }

  @Override
  public void addSelectionPath(TreePath path) {
      if (canPathBeAdded(path)) {
        super.addSelectionPath(path);
      }
  }

  @Override
  public void addSelectionPaths(TreePath[] paths) {
      paths = getFilteredPaths(paths);
      super.addSelectionPaths(paths);
  }
}
