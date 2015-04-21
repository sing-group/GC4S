package es.uvigo.ei.sing.hlfernandez.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * Abstract class for creating input dialogs.
 * 
 * @author hlfernandez
 *
 */
public abstract class AbstractInputJDialog extends JDialog {
private static final long serialVersionUID = 1L;
	
	protected static final ImageIcon ICON_ACCEPT = new ImageIcon(
			AbstractInputJDialog.class.getResource("icons/ok.png"));
	protected static final ImageIcon ICON_CANCEL = new ImageIcon(
			AbstractInputJDialog.class.getResource("icons/cancel.png"));
	
	protected boolean canceled = true;
	protected JButton okButton;
	protected JFrame parent;
	private static final JFileChooser fileChooser = new JFileChooser();
	
	private JTextArea textArea;
	
	/**
	 * Constructs an AbstractInputJDialog
	 */
	protected AbstractInputJDialog(JFrame parent) {
		super(parent);
		this.parent = parent;
		initComponent();
	}
	
	private void initComponent() {
		this.setTitle(getDialogTitle());
		this.setLayout(new BorderLayout());
		this.add(getDescriptionPane(), BorderLayout.NORTH);
		JPanel inputComponents = getInputComponentsPane();
		JScrollPane scroll = new JScrollPane(inputComponents);
		this.add(scroll, BorderLayout.CENTER);
		this.add(getButtonsPane(), BorderLayout.SOUTH);
		this.setResizable(false);
		centerDialogOnScreen();
		this.setModal(true);
		addKeyBindings();
	}
	
	private JTextArea getDescriptionPane() {
		if (this.textArea == null) {
			this.textArea = new JTextArea(
				getDescription());
			this.textArea.setMargin(new Insets(10, 10, 10, 10));

			this.textArea.setWrapStyleWord(true);
			this.textArea.setLineWrap(true);
			this.textArea.setEditable(false);
			this.textArea.setBackground(Color.WHITE);
			this.textArea.setOpaque(true);
		}
		
		return this.textArea;
	}

	/**
	 * Returns the buttons panel of the dialog.
	 * 
	 * @return the buttons panel of the dialog.
	 */
	protected Component getButtonsPane() {
		final JPanel buttonsPanel = new JPanel(new FlowLayout());
		
		okButton = new JButton("Ok", ICON_ACCEPT);
		okButton.setEnabled(false);
		okButton.setToolTipText("Accept");
		okButton.addActionListener(event -> {
			canceled = false;
			dispose();
		});
		
		final JButton cancelButton = new JButton("Cancel", ICON_CANCEL);
		cancelButton.setToolTipText("Cancel");
		cancelButton.addActionListener(event -> {
			canceled = true;
			dispose();
		});

		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		getRootPane().setDefaultButton(okButton);
		InputMap im = okButton.getInputMap();
		im.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		im.put(KeyStroke.getKeyStroke("released ENTER"), "released");
		
		return buttonsPanel;
	}
	
	/**
	 * Establishes the dialog key bindings.
	 */
	protected void addKeyBindings() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "closeTheDialog");
		getRootPane().getActionMap().put("closeTheDialog",
				new AbstractAction() {

					private static final long serialVersionUID = 8360999630557775801L;

					@Override
					public void actionPerformed(ActionEvent e) {
						canceled = true;
						dispose();
					}
				});
	}
	
	/**
	 * Centers the dialog in the parent component.
	 */
	protected void centerDialogOnScreen() {
		this.pack();
		this.setLocationRelativeTo(parent);
	}

	/**
	 * Returns the dialog filechooser.
	 * 
	 * @return the dialog filechooser.
	 */
	protected JFileChooser getFileChooser() {
		return fileChooser;
	}
	
	protected abstract String getDialogTitle();

	protected abstract String getDescription();

	protected abstract JPanel getInputComponentsPane();
}