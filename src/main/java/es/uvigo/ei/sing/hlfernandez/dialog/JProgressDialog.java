package es.uvigo.ei.sing.hlfernandez.dialog;

import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
	private JFrame parent;
	private List<String> tasks;
	private JLabel taskLabel;
	private JProgressBar progressBar;
	private Iterator<String> tasksIterator;
	private int increment;
	private JPanel content;
	
	/**
	 * Constructs a new {@code ProgressDialog} instance.
	 * 
	 * @param parent the parent frame to show the dialog.
	 * @param tasks the list of tasks.
	 */
	public JProgressDialog(JFrame parent, String title, List<String> tasks) {
		super(parent, title);
		this.parent = parent;
		this.tasks = tasks;
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

		this.content.add(new JLabel("Current task:"));
		this.content.add(taskLabel);
		this.content.add(new JLabel("Total progress:"));
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
		if(tasksIterator.hasNext()) {
			this.progressBar.setValue(this.progressBar.getValue()+this.increment);
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
