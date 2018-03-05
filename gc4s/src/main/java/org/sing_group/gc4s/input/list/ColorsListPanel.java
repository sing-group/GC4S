/*
 * #%L
 * GC4S components
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
package org.sing_group.gc4s.input.list;

import java.awt.Color;
import java.awt.Component;
import java.io.InvalidClassException;
import java.util.Collections;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListDataListener;

import org.sing_group.gc4s.ui.ColorListCellRenderer;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;

/**
 * This class encloses a {@code JListPanel<Color>} to provide the ability of
 * adding ({@code Color}) elements to the list-
 *
 * @author hlfernandez
 * @see JListPanel
 *
 */
public class ColorsListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JList<Color> colorList;
	private JListPanel<Color> colorListPanel;
	private ExtendedDefaultListModel<Color> colorListModel;

	/**
	 * Creates a new empty {@code ColorsListPanel}.
	 * 
	 */
	public ColorsListPanel() {
		this(Collections.emptyList());
	}

	/**
	 * Creates a new {@code ColorsListPanel} containing the specified list of
	 * colors.
	 * 
	 * @param colors a list of {@code Color}.
	 */
	public ColorsListPanel(List<Color> colors) {
		this.add(getColorListPanel(colors));
	}

	private Component getColorListPanel(List<Color> colors) {
		this.colorListModel = new ExtendedDefaultListModel<Color>();
		this.colorListModel.addElements(colors);

		this.colorList = new JList<Color>(colorListModel);
		this.colorList.setCellRenderer(new ColorListCellRenderer() {
			private static final long serialVersionUID = 1L;
			private final Border defaultBorder = new JButton().getBorder();
			@Override
			public Component getListCellRendererComponent(
				JList<? extends Color> list, Color value, int index,
				boolean isSelected, boolean cellHasFocus
			) {
				Component toret = super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);
				if ((isSelected || cellHasFocus)
						&& toret instanceof JComponent) {
					((JComponent) toret).setBorder(BorderFactory
							.createLineBorder(Color.LIGHT_GRAY, 2));
				} else {
					((JComponent) toret).setBorder(defaultBorder);
				}
				return toret;
			}
		});
		try {
			this.colorListPanel = new JListPanel<>(colorList, true, false);
			this.colorListPanel.addAction(getAddColorAction(),
				"Adds a new color to the list");

			return colorListPanel;
		} catch (InvalidClassException e) {
			throw new RuntimeException("");
		}
	}
	
	private Action getAddColorAction() {
		return new ExtendedAbstractAction(
			"Add color", 
			Icons.ICON_ADD_16,
			this::addColorAction
		);
	}

	private void addColorAction() {
		new JColorChooser().setVisible(true);
		JColorChooser colorChooser = new JColorChooser();
		int response = JOptionPane.showConfirmDialog(
			getParent(), colorChooser, "Choose a color", 
			JOptionPane.YES_NO_OPTION
		);
		if (response == JOptionPane.YES_OPTION) {
			addColor(colorChooser.getSelectionModel().getSelectedColor());
		}
	}

	/**
	 * Adds the specified color to the list of selected colors.
	 * 
	 * @param color the {@code Color} to add
	 */
	public void addColor(Color color) {
		SwingUtilities.invokeLater(() -> {
			colorListModel.addElement(color);
			colorList.updateUI();
		});
	}
	
	/**
	 * Adds the specified colors to the list of selected colors.
	 * 
	 * @param colors the list of {@code Color}s to add
	 */
	public void addColors(List<Color> colors) {
		SwingUtilities.invokeLater(() -> {
			colorListModel.addElements(colors);
			colorList.updateUI();
		});
	}

	/**
	 * Returns a list containing the {@code Color}s selected by the user.
	 * 
	 * @return a list containing the {@code Color}s selected by the user
	 */
	public List<Color> getSelectedColors() {
		return Collections.list(this.colorListModel.elements());
	}
	
    /**
	 * Adds a listener to the list that's notified each time a change to the
	 * data model occurs.
	 *
	 * @param l the {@code ListDataListener} to be added
	 */
	public void addListDataListener(ListDataListener l) {
		this.colorListModel.addListDataListener(l);
	}
	
    /**
     * Removes a listener from the list that's notified each time a
     * change to the data model occurs.
     *
     * @param l the {@code ListDataListener} to be removed
     */
    public void removeListDataListener(ListDataListener l) {
    	this.colorListModel.removeListDataListener(l);
    }
}
