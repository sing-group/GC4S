package org.sing_group.gc4s.ui;

import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * A {@code JPanel} that only accepts one {@code Component} and displays it
 * in the center.
 * 
 * @author hlfernandez
 *
 */
public class CenteredJPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code CenteredJPanel}. 
	 */
	public CenteredJPanel() {
		super(new GridBagLayout());
	}

	/**
	 * Creates a new {@code CenteredJPanel} with the specified {@code Component}.
	 * 
	 * @param aComponent the initial {@code Component}.
	 */
	public CenteredJPanel(Component aComponent) {
		this();
		this.add(aComponent);
	}
	
	@Override
	public Component add(Component comp) {
		this.removeAll();
		return super.add(comp);
	}
}
