package es.uvigo.ei.hlfernandez.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.uvigo.ei.sing.hlfernandez.tabbedpane.ExtendedJTabbedPane;

/**
 * An example showing the use of {@link ExtendedJTabbedPane}.
 * 
 * @author hlfernandez
 *
 */
public class ExtendedJTabbedPaneDemo {
	public static void main(String[] args) {
		ExtendedJTabbedPane tabbedPane = new ExtendedJTabbedPane();
		tabbedPane.setHideTabBarWhenSingleTab(true);
		tabbedPane.addTab("Tab 1", DemoUtils.createPanelAndCenterComponent(new JLabel("Tab 1")));
		tabbedPane.setPreferredSize(new Dimension(300,200));

		final JButton addTab = new JButton(new AbstractAction("Add tab") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String tabName = "Tab " + String.valueOf(tabbedPane.getTabCount()+1);
				tabbedPane.addTab(tabName, DemoUtils.createPanelAndCenterComponent(new JLabel(tabName)));
			}
		});
		final JButton removeLastTab = new JButton(new AbstractAction("Remove last tab") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeTabAt(tabbedPane.getTabCount()-1);
			}
		});
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				removeLastTab.setEnabled(tabbedPane.getTabCount() > 0);
			}
		});
		
		JPanel demoPanel = new JPanel(new BorderLayout());
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.add(addTab);
		toolbar.add(removeLastTab);
		demoPanel.add(toolbar, BorderLayout.NORTH);
		demoPanel.add(tabbedPane, BorderLayout.CENTER);
		
		demoPanel.setPreferredSize(new Dimension(300,200));
		DemoUtils.showComponent(demoPanel, "ExtendedJTabbedPane demo");
	}
}
