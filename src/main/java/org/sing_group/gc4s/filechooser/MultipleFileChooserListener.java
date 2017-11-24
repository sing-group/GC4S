package org.sing_group.gc4s.filechooser;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving multiple file chooser panel events.
 * 
 * @author hlfernandez
 *
 */
public interface MultipleFileChooserListener extends FileChooserListener {
	void onFileRemoved(ChangeEvent event);
}
