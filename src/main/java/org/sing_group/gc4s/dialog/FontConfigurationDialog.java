package org.sing_group.gc4s.dialog;

import java.awt.Font;
import java.awt.Window;
import java.util.Optional;

import javax.swing.JPanel;

import org.sing_group.gc4s.input.FontConfigurationPanel;

/**
 * A dialog that allows a user to select and configure a {@code Font}.
 * 
 * @author hlfernandez
 * @see FontConfigurationPanel
 */
public class FontConfigurationDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private FontConfigurationPanel fontConfigurationPanel;
	private Optional<Font> selectedFont;
	
	/**
	 * Constructs an {@code FontConfigurationDialog}.
	 * 
	 * @param parent
	 *            the parent {@code Window}.
	 */
	public FontConfigurationDialog(Window parent) {
		super(parent);
	}

	
	/**
	 * Constructs an {@code FontConfigurationDialog} using {@code font} as
	 * initial value.
	 * 
	 * @param parent the parent {@code Window}.
	 * @param font the initial {@code Font}.
	 */
	public FontConfigurationDialog(Window parent, Font font) {
		super(parent);
		
		this.selectedFont = Optional.ofNullable(font);
		if(this.selectedFont.isPresent()) {
			this.fontConfigurationPanel.setSelectedFont(this.selectedFont.get());
		}
	}

	@Override
	protected String getDialogTitle() {
		return "Font configuration";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to select a font type and size.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		fontConfigurationPanel = new FontConfigurationPanel();
		
		return fontConfigurationPanel;
	}

	/**
	 * Returns the {@code Font} configured by the user.
	 * 
	 * @return the {@code Font} configured by the user.
	 */
	public Font getSelectedFont() {
		return fontConfigurationPanel.getSelectedFont();
	}
	
	@Override
	public void setVisible(boolean b) {
		this.okButton.setEnabled(true);
		this.pack();
		super.setVisible(b);
	}
}
