package org.sing_group.gc4s.tabbedpane;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * An extension of {@code JTabbedPane} that adds useful features such as the 
 * possibility of tell the tabbed pane to hide the tab bar when there is
 * only one tab.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private boolean hideTabBarWhenSingleTab = false;

	private LayoutManager defaultLayout;

	/**
	 * Sets whether the tab bar should be shown when there is only one tab.
	 * 
	 * @param hide {@code true} if the tab bar should be hidden when there is
	 * only one tab and {@code false} otherwise.
	 */
	public void setHideTabBarWhenSingleTab(boolean hide) {
		if (hideTabBarWhenSingleTab != hide) {
			this.hideTabBarWhenSingleTab = hide;
			defaultLayout = this.getLayout();
			this.updateLayout();
		}
	}

	private void updateLayout() {
		this.setLayout(getProperLayout());
	}

	private LayoutManager getProperLayout() {
		if (getTabCount() == 1 && hideTabBarWhenSingleTab) {
			return new CardLayout();
		} else {
			return defaultLayout;
		}
	}
	
	@Override
	public void addTab(String title, Component component) {
		super.addTab(title, component);
		updateLayout();
	}
	
	@Override
	public void addTab(String title, Icon icon, Component component) {
		super.addTab(title, icon, component);
		updateLayout();
	}
	
	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		updateLayout();
	}
	
	@Override
	public void removeTabAt(int index) {
		super.removeTabAt(index);
		updateLayout();
	}
}