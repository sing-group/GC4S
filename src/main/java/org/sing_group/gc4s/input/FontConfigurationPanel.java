package org.sing_group.gc4s.input;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.util.stream.Collectors.toList;
import static javax.swing.BorderFactory.createLoweredBevelBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import static org.sing_group.gc4s.utilities.FontUtils.getAvailableFonts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import org.sing_group.gc4s.input.combobox.ComboBoxItem;

/**
 * <p>
 * A panel that allows user to configure a font. The configured font may be
 * obtained by calling {@link FontConfigurationPanel#getSelectedFont()}.
 * </p>
 *
 * <p>
 * To listen for changes in the configured font, it is possible to add a
 * {@code PropertyChangeListener} for property
 * {@link FontConfigurationPanel#SELECTED_FONT}.
 * </p>
 *
 * @author hlfernandez
 *
 */
public class FontConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String SELECTED_FONT = "gc4s.fontconfig.selection";

	private enum FontStyle {
		PLAIN("Normal", Font.PLAIN),
		BOLD("Bold", Font.BOLD),
		ITALIC("Italic", Font.ITALIC),
		BOLD_ITALIC("Bold Italic", Font.BOLD | Font.ITALIC);

		private String description;
		private int font;

		FontStyle(String description, int font) {
			this.description = description;
			this.font = font;
		};

		@Override
		public String toString() {
			return this.description;
		}

		public int getStyle() {
			return this.font;
		}

		public static FontStyle getFontStyle(int fontStyle) {
			switch (fontStyle) {
			case Font.PLAIN:
				return PLAIN;
			case Font.BOLD:
				return BOLD;
			case Font.ITALIC:
				return ITALIC;
			case Font.ITALIC | Font.BOLD:
				return BOLD_ITALIC;
			default:
				throw new IllegalArgumentException("");
			}
		}
	}

	private Font oldValue;
	private JSpinner fontSize;
	private JScrollPane previewFontPanel;
	private JComboBox<FontStyle> fontStylesCmb;
	private JComboBox<ComboBoxItem<Font>> fontsCmb;
	private JTextArea sampleText = new JTextArea("Sample text");
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
		this.add(getPreviewFontPanel(), SOUTH);

		updateFontPreview();
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[3];
		parameters[0] = getFontParameter();
		parameters[1] = getFontSizeParameter();
		parameters[2] = getFontStyleParameter();

		return parameters;
	}

	private InputParameter getFontParameter() {
		fontsCmb = new JComboBox<ComboBoxItem<Font>>();
		getFontItems().forEach(fontsCmb::addItem);
		fontsCmb.addItemListener(this::comboItemChanged);

		return new InputParameter("Font type", fontsCmb, "The font type.");
	}

	private void comboItemChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			notifySelectedFontPropertyChange();
			this.updateFontPreview();
		}
	}

	private List<ComboBoxItem<Font>> getFontItems() {
		return 	Stream.of(getAvailableFonts())
				.map(f -> new ComboBoxItem<Font>(f, f.getName()))
				.peek(i -> this.fontsMap.put(i.toString(), i))
				.collect(toList());
	}

	private InputParameter getFontSizeParameter() {
		fontSize = new JSpinner(new SpinnerNumberModel(12, 1, 128, 1));
		fontSize.addChangeListener((e) -> fontSizeChanged());

		return new InputParameter("Font size", fontSize, "The font size.");
	}

	private InputParameter getFontStyleParameter() {
		fontStylesCmb = new JComboBox<FontStyle>(FontStyle.values());
		fontStylesCmb.addItemListener(this::comboItemChanged);

		return new InputParameter("Font style", fontStylesCmb, "The font style.");
	}

	private void fontSizeChanged() {
		notifySelectedFontPropertyChange();
		updateFontPreview();
	}

	private void notifySelectedFontPropertyChange() {
		this.firePropertyChange(SELECTED_FONT, oldValue, getSelectedFont());
	}

	private void updateFontPreview() {
		oldValue = getSelectedFont();
		sampleText.setFont(oldValue);
	}

	private JComponent getPreviewFontPanel() {
		previewFontPanel = new JScrollPane();
		previewFontPanel.setBorder(createTitledBorder(
			createLoweredBevelBorder(), "Font preview"));
		previewFontPanel.setViewportView(sampleText);
		sampleText.setMinimumSize(new Dimension(100, 100));
		sampleText.setPreferredSize(new Dimension(100, 100));
		sampleText.setEditable(false);

		return previewFontPanel;
	}

	/**
	 * Sets the visibility of the font preview area.
	 *
	 * @param visible {@code true} to make the component visible; {@code false}
	 *        to make it invisible
	 */
	public void setFontPreviewVisible(boolean visible) {
		this.previewFontPanel.setVisible(visible);
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

		return selected.deriveFont(getFontStyle(), getFontSize());
	}

	private int getFontStyle() {
		return ((FontStyle)fontStylesCmb.getSelectedItem()).getStyle();
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
			this.fontStylesCmb.setSelectedItem(FontStyle.getFontStyle(font.getStyle()));
		}
	}
}
