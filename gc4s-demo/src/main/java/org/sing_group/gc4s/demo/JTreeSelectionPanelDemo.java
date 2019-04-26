/*
 * #%L
 * GC4S components demo
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
package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.createPanelAndCenterComponent;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.sing_group.gc4s.input.tree.JTreeSelectionPanel;
import org.sing_group.gc4s.input.tree.PathCheckTreeSelectionModel;
import org.sing_group.gc4s.utilities.JTreeUtils;

/**
 * An example showing the usage of a {@code JTreeSelectionPanel}.
 * 
 * @author hlfernandez
 *
 */
public class JTreeSelectionPanelDemo {

  public static void main(String[] args) {

    JTree tree = createTestTree();
    JTreeSelectionPanel tsp =
      new JTreeSelectionPanel(tree, "Choose", true, false, true, false);

    showComponent(createPanelAndCenterComponent(tsp));
  }

  private static JTree createTestTree() {
    TreeSelectionListener tsl = new TreeSelectionListener() {

      @Override
      public void valueChanged(TreeSelectionEvent e) {
        if (e.getNewLeadSelectionPath() != null) {
          String text = ((DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent()).toString();
          System.err.println(text);
        }
      }
    };

    final JTree tree = new JTree(createTestTreemodel());
    tree.setShowsRootHandles(true);
    tree.setRootVisible(false);
    tree.setSelectionModel(new PathCheckTreeSelectionModel());
    tree.getSelectionModel()
      .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    tree.getSelectionModel().addTreeSelectionListener(tsl);

    JTreeUtils.collapseAll(tree);

    return tree;
  }

  private static DefaultMutableTreeNode createTestTreemodel() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

    DefaultMutableTreeNode g1 = new DefaultMutableTreeNode("Group 1");
    root.add(g1);

    DefaultMutableTreeNode g2 = new DefaultMutableTreeNode("Group 2");
    root.add(g2);

    for (int i = 1; i < 5; i++) {
      g1.add(new DefaultMutableTreeNode("Item " + i));
      g2.add(new DefaultMutableTreeNode("Item " + i));
    }

    return root;
  }
}
