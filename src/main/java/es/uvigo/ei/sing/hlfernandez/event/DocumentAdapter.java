package es.uvigo.ei.sing.hlfernandez.event;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An abstract adapter class for receiving text document events. The methods in
 * this class are empty. This class exists as convenience for creating listener
 * objects.
 * 
 * @author hlfernandez
 *
 */
public class DocumentAdapter implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}
