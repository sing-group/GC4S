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
package org.sing_group.gc4s.dialog;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * A {@code WorkingDialog} shows a progress in work dialog with an indeterminate
 * progress bar and a label. The indeterminate progress bar may be replaced by 
 * a user image.
 *
 * @author hlfernandez
 *
 */
public class WorkingDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private Window parent;
	private JLabel taskLabel;
	private JProgressBar progressBar;
	private JPanel content;
	private String task;
	private ImageIcon image;

	/**
	 * Constructs a new {@code WorkingDialog} instance with the specified
	 * parameters.
	 *
	 * @param parent the parent window to show the dialog
	 * @param title the dialog title
	 * @param task the title of the working task
	 */
	public WorkingDialog(Window parent, String title, String task) {
		this(parent, title, task, null);
	}

	/**
	 * Constructs a new {@code WorkingDialog} instance with the specified
	 * parameters.
	 *
	 * @param parent the parent window to show the dialog
	 * @param title the dialog title
	 * @param task the title of the working task
	 * @param image the working in progress image
	 */
	public WorkingDialog(Window parent, String title, String task,
		ImageIcon image
	) {
		super(parent, title);
		this.parent = parent;
		this.task = task;
		this.image = image;

		this.initComponent();
	}

	private void initComponent() {
		this.content = new JPanel();
		this.content.setBorder(createEmptyBorder(10, 10, 10, 10));
		this.content.setLayout(new GridLayout(2, 1));

		taskLabel = new JLabel(this.task);
		taskLabel.setBorder(createEmptyBorder(0, 0, 5, 0));

		this.content.add(taskLabel);
		this.content.add(getProgressComponent());

		this.add(this.content);
		this.setResizable(false);
		this.setModal(false);
		centerDialogOnScreen();
	}

	private Component getProgressComponent() {
		if (this.image == null) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(false);
			progressBar.setIndeterminate(true);

			return progressBar;
		} else {
			return new JLabel(this.image);
		}
	}

	/**
	 * Sets the progress finished.
	 *
	 * @param message the message to show in the label.
	 */
	public void finished(String message) {
		if (progressBar != null) {
			this.progressBar.setIndeterminate(false);
			this.progressBar.setValue(100);
		}
		this.taskLabel.setText(message);
	}

	protected void centerDialogOnScreen() {
		this.pack();
		this.setLocationRelativeTo(parent);
	}
}
