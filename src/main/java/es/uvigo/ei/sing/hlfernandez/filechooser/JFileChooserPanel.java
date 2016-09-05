package es.uvigo.ei.sing.hlfernandez.filechooser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.uvigo.ei.sing.hlfernandez.ComponentFactory;
import es.uvigo.ei.sing.hlfernandez.utilities.FileDrop;

public class JFileChooserPanel extends JPanel {
	
	public static enum Mode {
		OPEN, SAVE;
	};
	
	private static final long serialVersionUID = 1L;
	private static final ImageIcon ICON_BROWSE = new ImageIcon(
		JFileChooserPanel.class.getResource("icons/browse.png"));
	
	private JFileChooser filechooser;
	private Mode mode;
	private AbstractAction browseAction;
	private JButton btnBrowse;
	private JLabel lblFile;
	private String lblFileText;
	private JTextField fileName;
	private File selectedFile = null;
	private String requiredFileExtension = null;

	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}.
	 * For the rest, the default configuration (default browse icon, file label
	 * text and a new {@code JFileChooser} and no required extension) is taken.
	 * 
	 * @param mode
	 *            the {@code JFileChooser} mode.
	 */
	public JFileChooserPanel(Mode mode) {
		this(mode, new JFileChooser(), ICON_BROWSE, "File: ", null);
	}

	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}
	 * and {@code requiredExtension}. For the rest, the default configuration
	 * (default browse icon, file label text and a new {@code JFileChooser}) is
	 * taken.
	 * 
	 * @param mode
	 *            the {@code JFileChooser} mode.
	 * @param requiredFileExtension
	 *            only for Mode.SAVE, the extension that file must have (e.g.:
	 *            "txt")
	 */
	public JFileChooserPanel(Mode mode, String requiredFileExtension) {
		this(mode, new JFileChooser(), ICON_BROWSE, "File: ",
				requiredFileExtension);
	}
	
	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode}
	 * and {@code filechooser}. For the rest, the default configuration (default
	 * browse icon, file label text and no required extension) is taken.
	 * 
	 * @param mode
	 *            the {@code JFileChooser} mode.
	 * @param filechooser
	 *            the {@link JFileChooser} that will be opened.
	 */
	public JFileChooserPanel(Mode  mode, JFileChooser filechooser){
		this(mode, filechooser, ICON_BROWSE, "File: ", null);
	}

	/**
	 * Constructs a {@link JFileChooserPanel} with the specified {@code} mode},
	 * {@code filechooser}, {@code browseIcon}, {@code labelFiletext} and
	 * {@code requiredFileExtension}.
	 * 
	 * @param mode
	 *            the {@code JFileChooser} mode.
	 * @param filechooser
	 *            the {@link JFileChooser} that will be opened.
	 * @param browseIcon
	 *            the {@link ImageIcon} for the browse button.
	 * @param labelFileText
	 *            the text for the label file.
	 * @param requiredFileExtension
	 *            only for Mode.SAVE, the extension that file must have (e.g.:
	 *            "txt")
	 */
	public JFileChooserPanel(Mode mode, JFileChooser filechooser, ImageIcon browseIcon,
			String labelFileText, String requiredFileExtension) {
		super();
		this.filechooser = filechooser;
		this.lblFileText = labelFileText;
		if(mode.equals(Mode.SAVE)){
			this.requiredFileExtension = requiredFileExtension;
		}
		this.mode = mode;
		initComponent();

	}
	
	private void initComponent() {
		this.setLayout(new BorderLayout());
		lblFile = new JLabel(this.lblFileText);
		fileName = new JTextField("", 20);
		fileName.setEditable(false);
		fileName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON3) {
					onBrowse();
				}
			}
		});
		new FileDrop(this.fileName, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				if (files.length > 0) {
					setSelectedFile(files[0]);
				}
			}
		});
		browseAction = new AbstractAction("Browse", ICON_BROWSE) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				onBrowse();
			}
		};
		btnBrowse = ComponentFactory.createButton(browseAction,
			true, "Browse file", false);

		this.add(lblFile, BorderLayout.WEST);
		this.add(fileName, BorderLayout.CENTER);
		this.add(btnBrowse, BorderLayout.EAST);
	}

	private void onBrowse() {
		JFileChooser fileChooser = getFilechooser();
		int returnVal = mode.equals(Mode.SAVE) ? 
			fileChooser.showSaveDialog(JFileChooserPanel.this) : 
			fileChooser.showOpenDialog(JFileChooserPanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			setSelectedFile(fileChooser.getSelectedFile());
		}
	}	

	private void setSelectedFile(File newFile){
		selectedFile = newFile;
		if (requiredFileExtension != null
				&& !newFile.getName().toLowerCase()
						.endsWith("." + requiredFileExtension)) {
			selectedFile = new File(newFile.getAbsolutePath()
					+ "." + requiredFileExtension);
		}
		fileName.setText(selectedFile.getAbsolutePath());
		fileName.setToolTipText(selectedFile.getAbsolutePath());
		filechooser.setSelectedFile(newFile);
	}
	
	/**
	 * Returns the browse action.
	 * @return the browse action.
	 */
	public JFileChooser getFilechooser() {
		return filechooser;
	}
	
	/**
	 * Returns the browse action.
	 * @return the browse action.
	 */
	public AbstractAction getBrowseAction() {
		return browseAction;
	}
	
	/**
	 * Returns the file label.
	 * @return the file label.
	 */
	public JLabel getComponentLabelFile() {
		return lblFile;
	}
	
	/**
	 * Returns the file name text field component.
	 * @return the file name text field component.
	 */
	public JTextField getComponentFileName() {
		return fileName;
	}
	
	/**
	 * Returns the browse button component.
	 * @return the browse button component.
	 */
	public JButton getComponentButtonBrowse() {
		return btnBrowse;
	}

	/**
	 * Returns the selected file.
	 * @return the selected file.
	 */
	public File getSelectedFile() {
		return selectedFile;
	}
}
