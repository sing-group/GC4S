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
package org.sing_group.gc4s.visualization;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.sing_group.gc4s.ui.CenteredJPanel;

/**
 * Utility methods for showing components.
 * 
 * @author hlfernandez
 *
 */
public class VisualizationUtils {

	/**
	 * Shows a {@code JFrame} containing the specified {@code component} and 
	 * frame extended {@code state}.
	 * 
	 * @param component the {@code Component} to show
	 * @param state the extended state of the frame
	 */
	public static final void showComponent(Component component, int state) {
		showComponent(component, "", state);
	}

	/**
	 * Shows a {@code JFrame} containing the specified {@code component}, 
	 * {@code title} and frame extended {@code state}.
	 * 
	 * @param component the {@code Component} to show
	 * @param title the frame title
	 * @param state the extended state of the frame
	 */
	public static final void showComponent(Component component, String title,
		int state) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
		frame.setExtendedState(state);
	}

	/**
	 * Shows a {@code JFrame} containing the specified {@code component} and 
	 * {@code size}
	 * 
	 * @param component the {@code Component} to show
	 * @param size the frame size
	 */
	public static final void showComponent(Component component,
		Dimension size) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
		frame.setMinimumSize(size);
	}

	/**
	 * Shows a {@code JFrame} containing the specified {@code component} and 
	 * {@code title}.
	 * 
	 * @param component the {@code Component} to show
	 * @param title the title for the window
	 */
	public static final void showComponent(Component component, String title) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Shows a {@code JFrame} containing the specified {@code component}.
	 * 
	 * @param component the {@code Component} to show
	 */
	public static final void showComponent(Component component) {
		showComponent(component, "");
	}

	/**
	 * Returns a new {@code JPanel} with {@code component} in the center.
	 * 
	 * @param component the component to add to the {@code JPanel}.
	 * 
	 * @return a new {@code JPanel} with {@code component} in the center.
	 */
	public static final JPanel createPanelAndCenterComponent(
		Component component
	) {
		return new CenteredJPanel(component);
	}

	/**
	 * Shows {@code dialog} and ends the application after it is closed.
	 * 
	 * @param dialog the {@code JDialog} to show.
	 */
	public static final void showDialog(JDialog dialog) {
		dialog.setVisible(true);
		System.exit(0);
	}

	/**
	 * Sets the Nimbus look and feel.
	 */
	public static final void setNimbusLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    /**
		     *  If Nimbus is not available, you can set the GUI to another look 
		     *  and feel.
		     */
		}
	}

	/**
	 * Sets the value of the property {@code Nimbus.keepAlternateRowColor} in
	 * {@code UIManager} to {@code true}.
	 * 
	 */
	public static void setNimbusKeepAlternateRowColor() {
		setNimbusKeepAlternateRowColor(Boolean.TRUE);
	}

	/**
	 * Sets the value of the property {@code Nimbus.keepAlternateRowColor} in
	 * {@code UIManager}.
	 * 
	 * @param keep the value of the property {@code Nimbus.keepAlternateRowColor}
	 */
	public static void setNimbusKeepAlternateRowColor(boolean keep) {
		UIManager.put("Nimbus.keepAlternateRowColor", keep);
	}
}
