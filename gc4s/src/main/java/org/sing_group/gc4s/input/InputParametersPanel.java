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
package org.sing_group.gc4s.input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sing_group.gc4s.ui.icons.Icons;

/**
 * An {@code InputParametersPanel} takes one or more {@link InputParameter} and
 * properly arranges them using a {@code GroupLayout}.
 *
 * @author hlfernandez
 *
 */
public class InputParametersPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static enum DescriptionAlignment {
		LEFT, RIGHT
	};

	private List<InputParameter> parameters;
	private DescriptionAlignment alignment;

	private Map<InputParameter, JComponent> descriptionLabels;
	private Map<InputParameter, JComponent> inputComponents;
	private Map<InputParameter, JComponent> helpLabels;

	/**
	 * Creates a new {@code InputParametersPanel} using the list of
	 * {@code parameters}.
	 *
	 * @param parameters one or more {@code InputParemeter}
	 */
	public InputParametersPanel(InputParameter... parameters) {
		this(DescriptionAlignment.RIGHT, parameters);
	}

	/**
	 * Creates a new {@code InputParametersPanel} using the list of
	 * {@code parameters}.
	 *
	 * @param alignment the alignment of the description labels
	 * @param parameters one or more {@code InputParemeter}
	 */
	public InputParametersPanel(DescriptionAlignment alignment,
		InputParameter... parameters
	) {
		this.alignment = alignment;
		this.parameters = Arrays.asList(parameters);
		this.initComponent();
	}

	private void initComponent() {
		this.initInputComponents();

		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		this.setLayout(groupLayout);

		SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
		Alignment labelsAlignment = getLabelsAlignment();
		ParallelGroup first = groupLayout.createParallelGroup();
		ParallelGroup second = groupLayout.createParallelGroup();
		ParallelGroup third = groupLayout.createParallelGroup();
		parameters.forEach(c -> {
			if(c instanceof InputParameterSeparator) {
				first.addComponent(descriptionLabels.get(c), Alignment.LEADING);
			} else {
				first.addComponent(descriptionLabels.get(c), labelsAlignment);
			}
			second.addComponent(inputComponents.get(c));
			third.addComponent(helpLabels.get(c));
		});
		horizontalGroup
			.addGroup(first)
			.addGroup(second)
			.addGroup(third);
		groupLayout.setHorizontalGroup(horizontalGroup);

		SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
		parameters.forEach(c -> {
			ParallelGroup current = groupLayout.createParallelGroup(Alignment.CENTER);
			if(c instanceof InputParameterSeparator) {
				current.addComponent(descriptionLabels.get(c), Alignment.TRAILING);
			} else {
				current.addComponent(descriptionLabels.get(c));
			}
			current.addComponent(inputComponents.get(c));
			current.addComponent(helpLabels.get(c));
			verticalGroup.addGroup(current);
		});
		groupLayout.setVerticalGroup(verticalGroup);
	}

	private Alignment getLabelsAlignment() {
		return getAlignment(this.alignment);
	}

	private static Alignment getAlignment(DescriptionAlignment alignment) {
		return alignment.equals(DescriptionAlignment.RIGHT)
			? Alignment.TRAILING
			: Alignment.LEADING;
	}

	private void initInputComponents() {
		descriptionLabels = new HashMap<InputParameter, JComponent>();
		inputComponents = new HashMap<InputParameter, JComponent>();
		helpLabels = new HashMap<InputParameter, JComponent>();
		parameters.forEach(c -> {
			descriptionLabels.put(c, new JLabel(c.getLabel()));
			inputComponents.put(c, c.getInput());

			if(c instanceof InputParameterSeparator) {
				helpLabels.put(c, new JLabel(""));
			}
			else {
				JLabel helpLabel = new JLabel(Icons.ICON_INFO_2_16);
				helpLabel.setToolTipText(c.getHelpLabel());
				helpLabels.put(c, helpLabel);
			}
		});
	}

    /**
     * Makes the specified parameter visible or invisible.
     *
     * @param parameter the {@code InputParameter} to change its visibility
     * @param visible {@code true} to make the parameter visible and
     *        {@code false} to make it invisible
     */
	public void setVisible(InputParameter parameter, boolean visible) {
		if (this.parameters.contains(parameter)) {
			this.descriptionLabels.get(parameter).setVisible(visible);
			this.inputComponents.get(parameter).setVisible(visible);
			this.helpLabels.get(parameter).setVisible(visible);
		}
	}
}
