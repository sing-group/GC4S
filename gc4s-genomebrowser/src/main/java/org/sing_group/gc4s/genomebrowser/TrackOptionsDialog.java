package org.sing_group.gc4s.genomebrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sing_group.gc4s.input.JColorChooserButton;
import org.sing_group.gc4s.input.text.JIntegerTextField;
import org.sing_group.gc4s.ui.icons.Icons;

/**
 * A dialog to configure one or more {@code TrackOption}s.
 * 
 * @author hlfernandez
 *
 */
public class TrackOptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	public static final int OK = 1;
	public static final int CANCEL = 2;

	private static final ImageIcon DIALOG_OK = Icons.ICON_OK_24;
	private static final ImageIcon DIALOG_CANCEL = Icons.ICON_CANCEL_24;

	private int status = CANCEL;

	private Collection<TrackOption> options;
	private HashMap<TrackOption, JComponent> components = new HashMap<TrackOption, JComponent>();

	/**
	 * Creates a new {{@code OptionsDialog} for the specified track options.
	 * 
	 * @param options a collection of {@code TrackOption}.
	 */
	public TrackOptionsDialog(Collection<TrackOption> options) {
		this.options = options;
		this.initialize();
		this.configureDialog();
	}

	private void configureDialog() {
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Configure track");
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private void initialize() {
		JPanel contents = new JPanel();
		GridLayout contentsLayout = new GridLayout(options.size(), 2);
		contentsLayout.setVgap(2);
		contents.setLayout(contentsLayout);
		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);
		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(contents, BorderLayout.CENTER);

		if (options.size() > 0) {
			for (TrackOption option : options) {
				contents.add(new JLabel(option.getName()));
				JComponent c = getComponent(option);
				components.put(option, c);
				contents.add(c);
			}
		} else {

			contents.setLayout(new GridLayout(2, 2));
			contents.add(new JLabel("No options avaliable"));
			contents.add(new JPanel());
		}
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton ok = new JButton("OK");
		ok.setIcon(DIALOG_OK);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (TrackOption o : options) {
					setOptionValue(o, components.get(o));
				}
				TrackOptionsDialog.this.status = OK;
				TrackOptionsDialog.this.dispose();
			}

		});

		buttons.add(ok);
		JButton cancel = new JButton("Cancel");
		cancel.setIcon(DIALOG_CANCEL);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TrackOptionsDialog.this.dispose();
			}

		});
		buttons.add(cancel);

		this.add(buttons, BorderLayout.SOUTH);

	}

	private void setOptionValue(TrackOption option, JComponent component) {
		if (option.getType() == String.class) {
			option.setValue(((JTextField) component).getText());
		} else if (option.getType() == Integer.class) { 
			option.setValue(((JIntegerTextField) component).getValue());
		} else if (option.getType().isEnum()) {
			option.setValue(((JComboBox<?>) component).getSelectedItem());
		} else if (option.getType() == Boolean.class) {
			option.setValue(((JCheckBox) component).isSelected() ? Boolean.TRUE
				: Boolean.FALSE);
		} else if (option.getType() == Color.class) {
			option.setValue(((JColorChooserButton) component).getColor());
		}
	}

	private JComponent getComponent(TrackOption option) {
		if (option.getType() == String.class) {
			return new JTextField(option.getValue().toString());
		} else if (option.getType() == Integer.class) {
			return new JIntegerTextField((Integer) option.getValue());
		} else if (option.getType().isEnum()) {
			JComboBox<Object> toret = new JComboBox<>();
			for (Object value : option.getType().getEnumConstants()) {
				toret.addItem(value);
			}
			toret.setSelectedItem(option.getValue());
			return toret;
		} else if (option.getType() == Boolean.class) {
			JCheckBox toret = new JCheckBox(option.getName());
			if (option.getValue().equals(Boolean.TRUE)) {
				toret.setSelected(true);
			}
			return toret;
		} else if (option.getType() == Color.class) {
			JColorChooserButton toret = new JColorChooserButton();
			toret.setColor((Color) option.getValue());

			return toret;
		}
		throw new RuntimeException(
			"Component type not valid: " + option.getType());
	}

	public int getStatus() {
		return this.status;
	}
}
