package org.sing_group.gc4s.tabbedpane;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * This class extends {@code JTabbedPane} to add close buttons to tabs.
 * 
 * @author hlfernandez
 *
 */
public class CloseableJTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	@Override
	public void addTab(String title, Component component) {
		super.addTab(title, component);
		addCloseButton(component);
	}
	
	@Override
	public void addTab(String title, Icon icon, Component component) {
		super.addTab(title, icon, component);
		addCloseButton(component);
	}
	
	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		addCloseButton(component);
	}
	
	private void addCloseButton(Component component) {
		super.setTabComponentAt(
				super.indexOfComponent(component), 
				new ButtonTabComponent(this)
			);
	}
}
