package es.uvigo.ei.sing.hlfernandez.event;

import java.awt.event.KeyEvent;

/**
 * A concrete implementation of {@code AbstractKeyPressedListener} to listen
 * for enter key.
 * 
 * @author hlfernandez
 *
 */
public class EnterKeyPressedListener extends AbstractKeyPressedListener {

	/**
	 * Constructs an {@code EnterKeyPressedListener} with the specified
	 * {@code action}.
	 * 
	 * @param action a {@code Runnable} object to invoke when the enter key
	 * 	is pressed.
	 */
	public EnterKeyPressedListener(Runnable action) {
		super(action);
	}

	@Override
	protected int getKeyCode() {
		return KeyEvent.VK_ENTER;
	}

}