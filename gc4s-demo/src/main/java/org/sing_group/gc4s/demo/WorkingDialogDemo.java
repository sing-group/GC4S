package org.sing_group.gc4s.demo;

import javax.swing.JFrame;

import org.sing_group.gc4s.dialog.WorkingDialog;

/**
 * An example showing the use of {@link WorkingDialog}.
 * 
 * @author hlfernandez
 *
 */
public class WorkingDialogDemo {
	public static void main(String[] args) throws InterruptedException {
		WorkingDialog dialog = new WorkingDialog(new JFrame(),
			"WorkingDialog demo", "Doing something...");
		dialog.setVisible(true);
		sleep(2000);
		dialog.finished("Finished!");
		sleep(2000);
	}
	
	private static final void sleep(int milis) throws InterruptedException {
		Thread.currentThread();
		Thread.sleep(milis);
	}
}
