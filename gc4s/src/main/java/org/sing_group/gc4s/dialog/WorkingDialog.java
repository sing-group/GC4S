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
