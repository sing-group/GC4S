package org.sing_group.gc4s.input;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 * A button that allows users selecting a color by using a
 * {@code JColorChooser}.
 * 
 * @author hlfernandez
 *
 */
public class JColorChooserButton extends JButton {
	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_COLOR = Color.BLUE;
	private Color currentColor;

	/**
	 * Constructs a new {@code JColorChooserButton} with the default color.
	 */
	public JColorChooserButton() {
		this(DEFAULT_COLOR);
	}
	
	/**
	 * Constructs a new {@code JColorChooserButton} with the specified {@code
	 * color}.
	 * 
	 * @param color the selected color.
	 */
	public JColorChooserButton(Color color) {
		this.currentColor = color;
		init();
	}

	private void init() {
		this.setBackground(currentColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDialog();
			}
		});
	}

	private void showDialog() {
		JColorChooser colorChooser = getColorChooser();
		int response = JOptionPane.showConfirmDialog(
			getParent(), colorChooser, 
			"Choose a color", JOptionPane.YES_NO_OPTION
		);
		if (response == JOptionPane.YES_OPTION) {
			updateCurrentColor(colorChooser.getSelectionModel()
					.getSelectedColor());
		}
	}
	
	private JColorChooser getColorChooser() {
		return new JColorChooser(currentColor);
	}
	
	/**
	 * Sets the component selected color.
	 * 
	 * @param color
	 *            the new color.
	 */
	public void setColor(Color color){
		updateCurrentColor(color);
	}

	private void updateCurrentColor(Color newColor) {
		this.currentColor = newColor;
		this.setBackground(currentColor);
	}

	/**
	 * Returns the current selected color.
	 * 
	 * @return the current selected color.
	 */
	public Color getColor() {
		return this.currentColor;
	}
}
