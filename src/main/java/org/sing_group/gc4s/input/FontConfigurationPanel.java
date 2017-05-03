package org.sing_group.gc4s.input;

import static java.awt.BorderLayout.CENTER;
import static org.sing_group.gc4s.utilities.FontUtils.getAvailableFonts;
import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.sing_group.gc4s.combobox.ComboBoxItem;

/**
 * A panel that allows user to configure a font. The configured font may be
 * obtained by calling {@link FontConfigurationPanel#getSelectedFont()}.
 * 
 * @author hlfernandez
 *
 */
public class FontConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<ComboBoxItem<Font>> fontsCmb;
	private JSpinner fontSize;
	private Map<String, ComboBoxItem<Font>> fontsMap = new HashMap<>();

	/**
	 * Creates a new {@code FontConfigurationPanel}.
	 */
	public FontConfigurationPanel() {
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(new InputParametersPanel(getParameters()), CENTER);
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[2];
		parameters[0] = getFontParameter();
		parameters[1] = getFontSizeParameter();
		return parameters;
	}

	private InputParameter getFontParameter() {
		fontsCmb = new JComboBox<ComboBoxItem<Font>>();
		getFontItems().forEach(fontsCmb::addItem);
		
		return new InputParameter("Font type", fontsCmb, "The font type.");
	}

	private List<ComboBoxItem<Font>> getFontItems() {
		return 	Stream.of(getAvailableFonts())
				.map(f -> new ComboBoxItem<Font>(f, f.getName()))
				.peek(i -> this.fontsMap.put(i.toString(), i))
				.collect(toList());
	}

	private InputParameter getFontSizeParameter() {
		fontSize = new JSpinner(new SpinnerNumberModel(12, 1, 40, 1));
		
		return new InputParameter("Font size", fontSize, "The font size.");
	}
	
	/**
	 * Returns the {@code Font} configured by the user.
	 * 
	 * @return the {@code Font} configured by the user.
	 */
	public Font getSelectedFont() {
		ComboBoxItem<?> selectedItem = 
			((ComboBoxItem<?>) fontsCmb.getSelectedItem());
		Font selected = (Font) selectedItem.getItem();

		return selected.deriveFont(getFontSize());
	}

	private float getFontSize() {
		return ((Integer) fontSize.getValue()).floatValue();
	}

	/**
	 * Establishes the selected {@code Font}.
	 * 
	 * @param font the selected {@code Font}.
	 */
	public void setSelectedFont(Font font) {
		if(this.fontsMap.containsKey(font.getName())) {
			this.fontsCmb.setSelectedItem(this.fontsMap.get(font.getName()));
			this.fontSize.setValue(font.getSize());
		}
	}
}
