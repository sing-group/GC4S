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

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.sing_group.gc4s.utilities.builder.JButtonBuilder.newJButtonBuilder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;

import org.sing_group.gc4s.utilities.JTreeUtils;

/**
 * A component to select an element from a {@code JTree}. The tree is displayed in a popup menu when user clicks the
 * button and the selected element in the tree is displayed in the component.
 * 
 * @author hlfernandez
 *
 */
public class JTreeSelectionPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_CHOOSE_BUTTON_LABEL = "Choose";
  private static final boolean DEFAULT_CLOSE_POPUP_TREE_SELECTION = false;
  private static final boolean DEFAULT_BUTTON_CLOSE_VISIBLE = true;
  private static final boolean DEFAULT_BUTTON_EXPAND_ALL_VISIBLE = true;
  private static final boolean DEFAULT_BUTTON_COLLAPSE_ALL_VISIBLE = true;

  private JTree tree;
  private JLabel selectionLabel;
  private JTreePopupMenu popupMenu;
  private String chooseButtonLabel;
  private JButton chooseButton;

  private boolean closePopupOnTreeSelection;
  private boolean closeButtonVisible;
  private boolean expandAllButtonVisible;
  private boolean collapseAllButtonVisible;

  /**
   * Constructs a new {@code JTreeSelectionPanel} with the specified tree and default values for the rest of the
   * parameters.
   * 
   * @param tree the {@code JTree} for the user selection
   */
  public JTreeSelectionPanel(
    JTree tree
  ) {
    this(
      tree, DEFAULT_CHOOSE_BUTTON_LABEL, DEFAULT_CLOSE_POPUP_TREE_SELECTION, DEFAULT_BUTTON_CLOSE_VISIBLE,
      DEFAULT_BUTTON_EXPAND_ALL_VISIBLE, DEFAULT_BUTTON_COLLAPSE_ALL_VISIBLE
    );
  }

  /**
   * Constructs a new {@code JTreeSelectionPanel} with the specified tree. This constructor also allows to specify:
   * 
   * <ul>
   * <li>The label for the choose button.</li>
   * <li>Whether the popup must be closed when an item is selected.</li>
   * <li>The visibility of the close, expand all and collapse all buttons.</li>
   * </ul>
   * 
   * @param tree the {@code JTree} for the user selection
   * @param chooseButtonLabel the label for the choose button
   * @param closePopupOnTreeSelection whether the popup must be closed when an item is selected
   */
  public JTreeSelectionPanel(
    JTree tree, String chooseButtonLabel, boolean closePopupOnTreeSelection,
    boolean closeButtonVisible, boolean expandAllButtonVisible, boolean collapseAllButtonVisible
  ) {
    this.tree = tree;
    this.chooseButtonLabel = chooseButtonLabel;
    this.closePopupOnTreeSelection = closePopupOnTreeSelection;
    this.closeButtonVisible = closeButtonVisible;
    this.expandAllButtonVisible = expandAllButtonVisible;
    this.collapseAllButtonVisible = collapseAllButtonVisible;

    this.init();

    this.tree.getSelectionModel().addTreeSelectionListener(this::treeValueChanged);
  }

  private void init() {
    this.popupMenu =
      new JTreePopupMenu(this.tree, this.closeButtonVisible, this.expandAllButtonVisible, this.collapseAllButtonVisible);

    this.setLayout(new BorderLayout());
    this.add(getChooseButton(), BorderLayout.WEST);
    this.add(getSelectionLabel(), BorderLayout.CENTER);
  }

  private Component getChooseButton() {
    if (this.chooseButton == null) {
      this.chooseButton =
        newJButtonBuilder().withText(this.chooseButtonLabel).thatDoes(getChooseButtonAction()).build();
    }
    return this.chooseButton;
  }

  private Action getChooseButtonAction() {
    return new AbstractAction() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        chooseButtonAction();
      }
    };
  }

  private void chooseButtonAction() {
    popupMenu.show(
      this.chooseButton, this.chooseButton.getX(), this.chooseButton.getY()
    );
    popupMenu.pack();
  }

  /**
   * Returns the selection label component.
   * 
   * @return the {@code JLabel} used to display the current selection
   */
  public JLabel getSelectionLabel() {
    if (this.selectionLabel == null) {
      this.selectionLabel = new JLabel(getCurrentSelection());
      this.selectionLabel.setMinimumSize(getSelectionLabelMinimumSize());
      this.selectionLabel.setPreferredSize(getSelectionLabelMinimumSize());
      this.selectionLabel.setBorder(createEmptyBorder(0, 10, 0, 5));
      this.selectionLabel.setFont(getSelectionLabelFont());
    }
    return this.selectionLabel;
  }

  /**
   * Returns the font of the selected item text label.
   * 
   * @return the {@code Font} of the selected item text label
   */
  protected Font getSelectionLabelFont() {
    return new JLabel().getFont();
  }

  /**
   * Returns the minimum size of the selected item text label.
   * 
   * @return the minimum size of the selected item text label
   */
  protected Dimension getSelectionLabelMinimumSize() {
    return new Dimension(150, 30);
  }

  private String getCurrentSelection() {
    if (this.tree.getSelectionModel().isSelectionEmpty()) {
      return "";
    } else {
      return this.tree.getSelectionModel().getSelectionPath()
        .getLastPathComponent().toString();
    }
  }

  private void treeValueChanged(TreeSelectionEvent e) {
    this.selectionLabel.setText(getCurrentSelection());
    if (this.closePopupOnTreeSelection) {
      this.popupMenu.setVisible(false);
    }
  }

  /**
   * 
   * An extension of {@code JPopupMenu} that show a {@code JTree} when it becomes visible. It has also some buttons to
   * control the tree (expand/collapse nodes) and to close the menu.
   * 
   * @author hlfernandez
   *
   */
  public static class JTreePopupMenu extends JPopupMenu {
    private static final long serialVersionUID = 1L;

    private JTree tree;

    private JScrollPane scrollPane;
    private JPanel buttonsPanel;

    private boolean closeButtonVisible;
    private boolean expandAllButtonVisible;
    private boolean collapseAllButtonVisible;

    /**
     * Constructs a new {@code JTreePopupMenu} with the specified tree.
     * 
     * @param tree the {@code JTree} to be displayed
     */
    public JTreePopupMenu(JTree tree) {
      this(tree, true, true, true);
    }

    public JTreePopupMenu(
      JTree tree, boolean closeButtonVisible, boolean expandAllButtonVisible, boolean collapseAllButtonVisible
    ) {
      this.tree = tree;
      this.closeButtonVisible = closeButtonVisible;
      this.expandAllButtonVisible = expandAllButtonVisible;
      this.collapseAllButtonVisible = collapseAllButtonVisible;

      this.setLayout(new BorderLayout());

      this.add(getTreeViewScrollPane(), BorderLayout.CENTER);
      this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private Component getTreeViewScrollPane() {
      if (scrollPane == null) {
        JPanel treePanel = new JPanel();
        treePanel.setLayout(new BorderLayout());
        treePanel.add(this.tree, BorderLayout.NORTH);

        treePanel.setBackground(tree.getBackground());

        scrollPane = new JScrollPane(treePanel);

        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
      }
      return scrollPane;
    }

    private Component getButtonsPanel() {
      if (this.buttonsPanel == null) {
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.setVisible(closeButtonVisible);
        closeButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (e.getSource() == closeButton) {
              setVisible(false);
            }
          }
        });

        JButton collapseAllButton = new JButton("Collapse all");
        collapseAllButton.setVisible(collapseAllButtonVisible);
        collapseAllButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (e.getSource() == collapseAllButton) {
              JTreeUtils.collapseAll(tree);
            }
          }
        });

        JButton expandAllButton = new JButton("Expand all");
        expandAllButton.setVisible(expandAllButtonVisible);
        expandAllButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (e.getSource() == expandAllButton) {
              JTreeUtils.expandAll(tree);
            }
          }
        });

        buttonsPanel.add(closeButton);
        buttonsPanel.add(collapseAllButton);
        buttonsPanel.add(expandAllButton);
      }
      return this.buttonsPanel;
    }
  }
}
