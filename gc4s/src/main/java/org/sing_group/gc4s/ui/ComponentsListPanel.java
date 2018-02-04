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
package org.sing_group.gc4s.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.sing_group.gc4s.ui.icons.Icons;

/**
 * A component that allows showing a list of generic components with control
 * buttons to add and remove them.
 *
 * @author hlfernandez
 *
 * @param <T> the type of the elements in this component
 */
public abstract class ComponentsListPanel<T extends Component> extends JPanel {
	private static final long serialVersionUID = 1L;

	private int initialComponents;
	private JPanel componentsPanel;
	private List<ComponentWrapPanel> wrapedComponents = new ArrayList<>();

	public ComponentsListPanel(int initialComponents) {
		this.initialComponents = initialComponents;
		this.init();
	}

	protected void init() {
		this.setLayout(new BorderLayout());
		this.add(getNorthComponent(), BorderLayout.NORTH);
		this.add(getCenterComponent(), BorderLayout.CENTER);
	}

	protected JPanel getNorthComponent() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		northPanel.add(Box.createHorizontalGlue());
		northPanel.add(getAddComponentButton());
		northPanel.add(Box.createHorizontalStrut(5));
		northPanel.add(getRemoveAllComponentsButton());
		northPanel.add(Box.createHorizontalGlue());

		return northPanel;
	}

	protected JButton getAddComponentButton() {
		JButton addComponentButton = new JButton(
			getAddComponentButtonLabel(), Icons.ICON_ADD_16);
		addComponentButton.addActionListener(
			event -> this.addComponentWrapPanelComponent());

		return addComponentButton;
	}

	protected String getAddComponentButtonLabel() {
		return "Add component";
	}

	protected JButton getRemoveAllComponentsButton() {
		JButton removeAllComponentsButton = new JButton(
			getRemoveAllComponentsButtonLabel(), Icons.ICON_TRASH_16);
		removeAllComponentsButton
			.addActionListener(event -> this.removeAllComponents());

		return removeAllComponentsButton;
	}

	protected String getRemoveAllComponentsButtonLabel() {
		return "Remove all";
	}

	protected Component getCenterComponent() {
		JPanel centerPanel = new JPanel();
		this.componentsPanel = new JPanel();
		this.componentsPanel.setLayout(
			new BoxLayout(this.componentsPanel, BoxLayout.Y_AXIS));
		centerPanel.add(componentsPanel);

		for (int i = 0; i < initialComponents; i++) {
			this.addComponentWrapPanelComponent();
		}

		return new JScrollPane(centerPanel);
	}

	private class ComponentWrapPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private T wrappedComponent;

		ComponentWrapPanel() {
			this.init();
		}

		private void init() {
			this.setLayout(new FlowLayout());
			wrappedComponent = getGenericComponent();
			this.add(wrappedComponent);
			this.add(getRemoveButton());
		}

		private JButton getRemoveButton() {
			JButton removeButton = new JButton(Icons.ICON_CANCEL_16);
			removeButton.addActionListener(
				event -> removeComponentWrapPanel(this));
			return removeButton;
		}

		public T getWrappedComponent() {
			return wrappedComponent;
		}
	}

	protected abstract T getGenericComponent();

	/**
	 * Returns the actual list of {@code T} components being shown.
	 *
	 * @return the actual list of {@code T} components being shown
	 */
	public List<T> getComponentsList() {
		return this.wrapedComponents.stream()
			.map(ComponentWrapPanel::getWrappedComponent)
			.collect(Collectors.toList());
	}

	protected void addComponentWrapPanelComponent() {
		ComponentWrapPanel newComponent = new ComponentWrapPanel();
		this.wrapedComponents.add(newComponent);
		this.componentsPanel.add(newComponent);
		this.updateUI();
	}

	protected void removeComponentWrapPanel(ComponentWrapPanel component) {
		this.wrapedComponents.remove(component);
		this.componentsPanel.remove(component);
		this.updateUI();
	}

	protected void removeAllComponents() {
		this.wrapedComponents.clear();
		this.componentsPanel.removeAll();
		this.updateUI();
	}
}
