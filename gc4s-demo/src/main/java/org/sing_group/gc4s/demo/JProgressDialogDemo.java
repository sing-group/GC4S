/*
 * #%L
 * GC4S demo
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
