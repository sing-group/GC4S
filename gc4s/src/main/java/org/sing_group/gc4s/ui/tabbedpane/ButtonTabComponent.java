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
package org.sing_group.gc4s.ui.tabbedpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * A {@code ButtonTabComponent} is a component to be used as <i>tabComponent</i>.
 * 
 * Contains a JLabel to show the text and a JButton to close the tab it belongs to.
 * 
 * @author hlfernandez
 * @author lipido
 */ 
public class ButtonTabComponent extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTabbedPane pane;
	
	/**
	 * Constructs a {@code ButtonTabComponent}.
	 * 
	 * @param pane
	 *            the {@code JTabbedPane} containing this component.
	 */
    public ButtonTabComponent(final JTabbedPane pane) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
        this.pane = pane;
        setOpaque(false);
        
        /*
         * Make JLabel read titles from JTabbedPane.
         */
        JLabel label = new JLabel() {
			private static final long serialVersionUID = 1L;

			public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);

		/*
		 * Add more space between the label and the button.
		 */
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

		JButton button = new TabButton();
		add(button);
		/*
		 * Add more space to the top of the component
		 */
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	private class TabButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Close this tab");
            /*
             * Make the button looks the same for all Laf's
             */
            setUI(new BasicButtonUI());
            
            /*
             * Make it transparent
             */
            setContentAreaFilled(false);
            
            /*
             * No need to be focusable
             */
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            
            /*
             * Making nice rollover effect
             */
            setRolloverEnabled(true);
            
            /*
             * Use the same listener for all buttons
             */
            addMouseListener(buttonMouseListener);
            /*
             * Close the proper tab by clicking the button
             */
            addActionListener(this);
            setComponentPopupMenu(new JPopupMenu());
        }

        public void actionPerformed(ActionEvent e) {
            closeTab();
        }

		/*
		 *  We don't want to update UI for this button
		 */
		public void updateUI() {
		}

        /*
         * Paint the cross 
         */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			/*
			 *  Shift the image for pressed buttons
			 */
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			/*
			 * Rectangle2D background = g2.getClipBounds();
			 * g2.setColor(Color.RED);
			 * g2.fill(background);
			 */
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
					- delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
					- delta - 1);
			g2.dispose();
		}
		
		public JPopupMenu getComponentPopupMenu() {
			JPopupMenu toret = new JPopupMenu();
			toret.add(new AbstractAction("Close all tabs") {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					closeAllTabs();
				}
			});
			toret.add(new AbstractAction("Close this tab") {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					closeTab();
				}
			});
			toret.add(new AbstractAction("Close other tabs") {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					closeOtherTabs();
				}
				
				@Override
				public boolean isEnabled() {
					return pane.getTabCount() > 1;
				}
			});
			return toret;
		};
	}

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
	
	
	private void closeAllTabs() {
		pane.removeAll();
	}

	private void closeTab() {
		int index = pane.indexOfTabComponent(ButtonTabComponent.this);
		if (index != -1) {
			pane.remove(index);
		}
	}

	private void closeOtherTabs() {
		int index = pane.indexOfTabComponent(ButtonTabComponent.this);
		if (index != -1) {
			for (int toRemove = pane.getTabCount() - 1; toRemove >= 0; toRemove--) {
				if (toRemove != index) {
					pane.remove(toRemove);
				}
			}
		}
	}
}