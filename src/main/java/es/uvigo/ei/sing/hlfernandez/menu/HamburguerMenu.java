package es.uvigo.ei.sing.hlfernandez.menu;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.PopupMenuEvent;

import es.uvigo.ei.sing.hlfernandez.event.PopupMenuAdapter;

/**
 * A hamburguer button to display a menu.
 * 
 * @author hlfernandez
 *
 */
public class HamburguerMenu extends JToggleButton {
	private static final long serialVersionUID = 1L;
	
	private static final ImageIcon ICON_32 = new ImageIcon(
		HamburguerMenu.class.getResource("icons/hamburguer32.png"));
	private static final ImageIcon ICON_24 = new ImageIcon(
		HamburguerMenu.class.getResource("icons/hamburguer24.png"));
	private static final ImageIcon ICON_16 = new ImageIcon(
		HamburguerMenu.class.getResource("icons/hamburguer16.png"));
	
	/**
	 * The possible sizes of the hamburguer icon.
	 * 
	 * @author hlfernandez
	 *
	 */
	public static enum Size{
		SIZE32(ICON_32), 
		SIZE24(ICON_24), 
		SIZE16(ICON_16);
		
		private ImageIcon icon;

		Size(ImageIcon icon){
			this.icon = icon;
		};
		
		public ImageIcon getIcon() {
			return icon;
		}
	}
	
	private JPopupMenu popup = new JPopupMenu();
	
	/**
	 * Creates a new {@code HamburgerMenu} using the 24 pixel icon.
	 */
	public HamburguerMenu() {
		this(Size.SIZE24);
	}

	/**
	 * Creates a new {@code HamburgerMenu} using the specified size icon.
	 * 
	 * @param size
	 *            the {@code Size} of the icon.
	 */
	public HamburguerMenu(Size size) {
		super();
		this.setIcon(size.getIcon());

		this.addActionListener(ev -> {
			JToggleButton b = HamburguerMenu.this;
			if (b.isSelected()) {
				popup.show(b, 0, b.getBounds().height);
			} else {
				popup.setVisible(false);
			}
		});
        
		this.popup.addPopupMenuListener(new PopupMenuAdapter() {
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				HamburguerMenu.this.setSelected(false);
			}
		});
	}
	
	/**
	 * Appends a new menu item to the end of the menu which dispatches the
	 * specified {@code Action} object.
	 *
	 * @param a
	 *            the {@code Action} to add to the menu.
	 * @return the new menu item.
	 * @see Action
	 */
	public JMenuItem add(Action a) {
		return popup.add(a);
	}

	/**
	 * Appends the specified menu item to the end of this menu.
	 *
	 * @param menuItem
	 *            the {@code JMenuItem} to add.
	 * @return the {@code JMenuItem} added.
	 */
	public JMenuItem add(JMenuItem menuItem) {
		return popup.add(menuItem);
	}

	/**
	 * Appends the specified component to the end of this menu.
	 * 
	 * @param comp
	 *            the {@code Component} to add.
	 * @return the {@code Component} added.
	 */
	public Component add(Component comp) {
		return popup.add(comp);
	}
}
