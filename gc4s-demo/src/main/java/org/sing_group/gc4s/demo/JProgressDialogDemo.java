package org.sing_group.gc4s.demo;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.sing_group.gc4s.dialog.JProgressDialog;

/**
 * An example showing the use of {@link JProgressDialog}.
 * 
 * @author hlfernandez
 *
 */
public class JProgressDialogDemo {
	public static void main(String[] args) {
		List<String> tasks = Arrays.asList(new String[]{
			"Task 1", "Task 2", "Task 3", "Task 4"	
		});
		JProgressDialog dialog = new JProgressDialog(
			new JFrame(), "JProgressDialog demo", tasks);
		dialog.setVisible(true);
		tasks.stream().forEach(t -> {
			try {
				sleep();
			} catch (Exception e) {
			}
			dialog.nextTask();
		});
		dialog.finished("Finished!");
	}
	
	private static final void sleep() throws InterruptedException {
		Thread.currentThread();
		Thread.sleep(15000);
	}
}
