package org.sing_group.gc4s.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * An abstract implementation of {@code KeyListener} that runs {@code action} 
 * when key code returned by {@code getKeyCode} is pressed.
 * 
 * @author hlfernandez
 *
 */
public abstract class AbstractKeyPressedListener implements KeyListener {

	private Runnable action;

	/**
	 * Constructs an {@code AbstractKeyPressedListener} with the specified
	 * {@code action}.
	 * 
	 * @param action a {@code Runnable} object to invoke when key is pressed.
	 */
	public AbstractKeyPressedListener(Runnable action) {
		this.action = action;
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) { 
		if (e.getKeyCode() == getKeyCode()) {
			action.run();
		}
	}

	protected abstract int getKeyCode();

	@Override
	public void keyReleased(KeyEvent e) {}
}
