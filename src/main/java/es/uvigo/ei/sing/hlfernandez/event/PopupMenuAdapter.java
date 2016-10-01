package es.uvigo.ei.sing.hlfernandez.event;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * An abstract adapter class for receiving popup menu events. The methods in
 * this class are empty. This class exists as convenience for creating listener
 * objects.
 * 
 * @author hlfernandez
 *
 */
public abstract class PopupMenuAdapter implements PopupMenuListener {

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {}
}
