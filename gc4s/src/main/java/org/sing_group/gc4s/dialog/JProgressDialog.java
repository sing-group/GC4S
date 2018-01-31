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

import java.awt.GridLayout;
import java.awt.Window;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * A {@code JProgressDialog} facilitates the creation of progress dialogs based
 * on fixed task lists.
 *
 * @author hlfernandez
 *
 */
public class JProgressDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_LABEL_CURRENT_TASK = "Current task:";
	private static final String DEFAULT_LABEL_TOTAL_PROGRESS = "Total progress:";

	private Window parent;
	private List<String> tasks;
	private JLabel taskLabel;
	private JProgressBar progressBar;
	private Iterator<String> tasksIterator;
	private int increment;
	private JPanel content;
	private String currentTaskLabel;
	private String totalProgressLabel;

	/**
	 * Constructs a new {@code ProgressDialog} instance with the specified
	 * parameters.
	 *
	 * @param parent the parent window to show the dialog
	 * @param title the dialog title
	 * @param tasks the list of tasks
	 */
	public JProgressDialog(Window parent, String title, List<String> tasks) {
		this(parent, title, tasks, DEFAULT_LABEL_CURRENT_TASK,
			DEFAULT_LABEL_TOTAL_PROGRESS);
	}

	/**
	 * Constructs a new {@code ProgressDialog} instance with the specified
	 * parameters.
	 *
	 * @param parent the parent window to show the dialog
	 * @param title the dialog title
	 * @param tasks the list of tasks
	 * @param tasks the list of tasks
	 * @param currentTaskLabel the label of the current task
	 */
	public JProgressDialog(Window parent, String title, List<String> tasks,
		String currentTaskLabel) {
		this(parent, title, tasks, currentTaskLabel,
			DEFAULT_LABEL_TOTAL_PROGRESS);
	}

	/**
	 * Constructs a new {@code ProgressDialog} instance with the specified
	 * parameters.
	 *
	 * @param parent the parent window to show the dialog
	 * @param title the dialog title
	 * @param tasks the list of tasks
	 * @param tasks the list of tasks
	 * @param currentTaskLabel the label of the current task
	 * @param totalProgressLabel the label of the total progress bar
	 */
	public JProgressDialog(Window parent, String title, List<String> tasks,
		String currentTaskLabel, String totalProgressLabel) {
		super(parent, title);

		this.parent = parent;
		this.tasks = tasks;
		this.currentTaskLabel = currentTaskLabel;
		this.totalProgressLabel = totalProgressLabel;
		this.tasksIterator = this.tasks.iterator();
		this.increment = 100 / tasks.size();
		this.initComponent();
	}

	private void initComponent() {
		this.content = new JPanel();
		this.content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.content.setLayout(new GridLayout(2, 2));

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);

		taskLabel = new JLabel(tasksIterator.next());

		this.content.add(new JLabel(currentTaskLabel));
		this.content.add(taskLabel);
		this.content.add(new JLabel(totalProgressLabel));
		this.content.add(progressBar);

		this.add(this.content);
		this.setResizable(false);
		this.setModal(false);
		centerDialogOnScreen();
	}

	/**
	 * Sets the progress finished.
	 *
	 * @param message the message to show in the label.
	 */
	public void finished(String message) {
		this.progressBar.setValue(100);
		this.taskLabel.setText(message);
	}

	/**
	 * Sets next task in the label and increments the progress.
	 */
	public void nextTask() {
		if (tasksIterator.hasNext()) {
			this.progressBar
				.setValue(this.progressBar.getValue() + this.increment);
			this.taskLabel.setText(tasksIterator.next());
		} else {
			this.progressBar.setValue(100);
		}
	}

	protected void centerDialogOnScreen() {
		this.pack();
		this.setLocationRelativeTo(parent);
	}
}
