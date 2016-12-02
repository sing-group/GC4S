package es.uvigo.ei.sing.hlfernandez.input.csv;

import static es.uvigo.ei.sing.hlfernandez.ui.icons.Icons.ICON_INFO_2_16;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import es.uvigo.ei.sing.hlfernandez.input.csv.CsvFormat.FileFormat;
import es.uvigo.ei.sing.hlfernandez.text.JLimitedTextField;
import es.uvigo.ei.sing.hlfernandez.utilities.TextFieldSelectionFocusListener;

/**
 * A panel that allows user to configure a CSV format. The configured format may
 * be obtained by calling {@link CsvPanel#getCsvFormat()}.
 * 
 * @author hlfernandez
 *
 */
public class CsvPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final TextFieldSelectionFocusListener FOCUS_LISTENER = 
		new TextFieldSelectionFocusListener();

	private static final FileFormat DEFAULT_FILE_FORMAT = FileFormat.LIBRE_OFFICE;
	
	private final DecimalFormatSymbols symbols =
		new DecimalFormatSymbols(Locale.getDefault());
	
	private JComboBox<FileFormat> fileFormatCB;
	private JXTaskPane customFormatOptionsTaskPane;
	private JCheckBox quoteFields;
	private JRadioButton rbtnSepTab;
	private AbstractButton rbtnSepCustom;
	private JTextComponent txtFieldCustomColumnSeparator;
	private AbstractButton rbtnDecimalSepPoint;
	private AbstractButton rbtnDecimalSepComma;
	private AbstractButton rbtnDecimalSepCustom;
	private JTextComponent txtFieldCustomDecimalPoint;
	private JRadioButton rbtnLineSepWindows;
	private JRadioButton rbtnLineSepLinux;
	private AbstractButton rbtnLineSepMacOS;

	private boolean updateWithoutEvents = false;

	/**
	 * Creates a new {@code CsvPanel} instance.
	 */
	public CsvPanel() {
		this.initComponent();
	}

	private void initComponent() {
		UIManager.put("TaskPane.animate", Boolean.FALSE);

		this.setLayout(new BorderLayout());
		this.add(getNorthPane(), BorderLayout.NORTH);
		this.add(getCenterPane(), BorderLayout.CENTER);
	}
	
	private Component getNorthPane() {
		JPanel northPane = new JPanel();
		final GroupLayout groupLayout = new GroupLayout(northPane);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		northPane.setLayout(groupLayout);
		
		final JLabel lblFormat = new JLabel("Format");
		JLabel lblFormatHelp = new JLabel(ICON_INFO_2_16);
		lblFormatHelp.setToolTipText("The file format");
		fileFormatCB = new JComboBox<FileFormat>();
		fileFormatCB.setModel(new DefaultComboBoxModel<>(FileFormat.values()));
		fileFormatCB.setSelectedItem(DEFAULT_FILE_FORMAT);
		fileFormatCB.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.DESELECTED)
				fileFormatSelectionChanged();
		});

		groupLayout.setHorizontalGroup(groupLayout
			.createSequentialGroup()
			.addGroup(groupLayout.createParallelGroup()
				.addComponent(lblFormat, Alignment.TRAILING))
			.addGroup(groupLayout.createParallelGroup()
				.addComponent(fileFormatCB))
			.addGroup(groupLayout.createParallelGroup()
				.addComponent(lblFormatHelp))
		);
		groupLayout.setVerticalGroup(groupLayout
			.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup()
					.addComponent(lblFormat, Alignment.CENTER)
					.addComponent(fileFormatCB, Alignment.CENTER)
					.addComponent(lblFormatHelp, Alignment.CENTER))
		);
		return northPane;
	}
	
	private Component getCenterPane() {
	final JXTaskPaneContainer customOptionsTaskPaneContainer =
			new JXTaskPaneContainer();
		customOptionsTaskPaneContainer.setOpaque(false);
		customOptionsTaskPaneContainer.setBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		customFormatOptionsTaskPane = new JXTaskPane();
		customFormatOptionsTaskPane.setTitle("Custom format");
		customFormatOptionsTaskPane.setCollapsed(true);
		customFormatOptionsTaskPane.addPropertyChangeListener("collapsed", e -> {
			formatCustomized();
		});
		
		JPanel customOptionsPane = new JPanel();
		final GroupLayout customOptionsGroupLayout =
			new GroupLayout(customOptionsPane);
		customOptionsGroupLayout.setAutoCreateContainerGaps(true);
		customOptionsGroupLayout.setAutoCreateGaps(true);
		customOptionsPane.setLayout(customOptionsGroupLayout);
		
		customFormatOptionsTaskPane.add(customOptionsPane,
			BorderLayout.CENTER);
		customOptionsTaskPaneContainer.add(customFormatOptionsTaskPane);
		
		final ItemListener columnSeparatorListener = e -> {
			if (e.getStateChange() == ItemEvent.SELECTED
				&& !this.updateWithoutEvents
			) {
				checkCustomSeparatorButtons();
			}
		};
		
		final ItemListener itemListener = e -> {
			if (e.getStateChange() == ItemEvent.SELECTED
				&& !this.updateWithoutEvents
			) {
				formatCustomized();				
			}
		};
		
		final JLabel lblQuoteFields = new JLabel("Quote fields");
		final JLabel lblQuoteFieldsHelp = new JLabel(ICON_INFO_2_16);
		lblQuoteFieldsHelp.setToolTipText(
			"Sets whether fields must be delimited by double quotes.");
		JPanel quotePanel = new JPanel(new GridLayout(1,1));
		quoteFields = new JCheckBox("", false);
		quoteFields.addItemListener(itemListener);
		quotePanel.add(quoteFields);
		
		final DocumentListener documentListener = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {	}

			@Override
			public void insertUpdate(DocumentEvent e) {
				notifyUpdate();
			}

			@Override
			public void changedUpdate(DocumentEvent e) { }
			
			private void notifyUpdate() {
				if (!updateWithoutEvents) {
					textFieldChanged();
				}
			}
		};

		final JLabel lblColumnSeparator = new JLabel("Column separator");
		final JLabel lblColumnSeparatorHelp = new JLabel(ICON_INFO_2_16);
		lblColumnSeparatorHelp.setToolTipText(
			"Sets the column separator");
		final JPanel columnSeparatorPanel = new JPanel(new GridLayout(1,3));
		rbtnSepTab = new JRadioButton("Tabulator");
		rbtnSepTab.setSelected(false);
		rbtnSepCustom = new JRadioButton("CUSTOM");
		rbtnSepCustom.setSelected(true);
		txtFieldCustomColumnSeparator = new JLimitedTextField(",",1,3);
		txtFieldCustomColumnSeparator.getDocument().addDocumentListener(
			documentListener);
		final Dimension size = new Dimension(25,25);
		txtFieldCustomColumnSeparator.setMaximumSize(size);
		txtFieldCustomColumnSeparator.setSize(size);
		txtFieldCustomColumnSeparator.addFocusListener(FOCUS_LISTENER);
		final JPanel customColumnSeparatorTFPanel = new JPanel();
		final BoxLayout cCSPanelLayout = new BoxLayout(
			customColumnSeparatorTFPanel, BoxLayout.X_AXIS);
		customColumnSeparatorTFPanel.setLayout(cCSPanelLayout);
		customColumnSeparatorTFPanel.add(Box.createHorizontalGlue(),
			BorderLayout.EAST);
		customColumnSeparatorTFPanel.add(txtFieldCustomColumnSeparator);
		columnSeparatorPanel.add(rbtnSepTab);
		columnSeparatorPanel.add(rbtnSepCustom);
		columnSeparatorPanel.add(customColumnSeparatorTFPanel);
		
		final ButtonGroup separatorGroup = new ButtonGroup();
		separatorGroup.add(rbtnSepTab);
		separatorGroup.add(rbtnSepCustom);
		
		rbtnSepTab.addItemListener(columnSeparatorListener);
		rbtnSepCustom.addItemListener(columnSeparatorListener);
		rbtnSepTab.addItemListener(itemListener);
		rbtnSepCustom.addItemListener(itemListener);
		
		final JLabel lblDecimalSeparator = new JLabel("Decimal separator");
		final JLabel lblDecimalSeparatorHelp = new JLabel(ICON_INFO_2_16);
		lblDecimalSeparatorHelp.setToolTipText(
			"Sets the decimal separator");
		final JPanel decimalSeparatorPanel = new JPanel(new GridLayout(1,4));
		rbtnDecimalSepPoint = new JRadioButton("Point");
		rbtnDecimalSepPoint.setSelected(true);
		rbtnDecimalSepPoint.addItemListener(itemListener);
		rbtnDecimalSepComma = new JRadioButton("Comma");
		rbtnDecimalSepComma.setSelected(false);
		rbtnDecimalSepComma.addItemListener(itemListener);
		rbtnDecimalSepCustom = new JRadioButton("CUSTOM");
		rbtnDecimalSepCustom.setSelected(false);
		rbtnDecimalSepCustom.addItemListener(itemListener);
		txtFieldCustomDecimalPoint = new JLimitedTextField(".", 1, 3);
		txtFieldCustomDecimalPoint.getDocument().addDocumentListener(
			documentListener);
		txtFieldCustomDecimalPoint.setMaximumSize(size);
		txtFieldCustomDecimalPoint.setSize(size);
		txtFieldCustomDecimalPoint.addFocusListener(FOCUS_LISTENER);
		final JPanel customDecimalPointTFPanel  = new JPanel();
		final BoxLayout cDPPanelLayout = new BoxLayout(
			customDecimalPointTFPanel, BoxLayout.X_AXIS);
		customDecimalPointTFPanel.setLayout(cDPPanelLayout);
		customDecimalPointTFPanel.add(Box.createHorizontalGlue(),
			BorderLayout.EAST);
		customDecimalPointTFPanel.add(txtFieldCustomDecimalPoint,
			BorderLayout.EAST);
		decimalSeparatorPanel.add(rbtnDecimalSepPoint);
		decimalSeparatorPanel.add(rbtnDecimalSepComma);
		decimalSeparatorPanel.add(rbtnDecimalSepCustom);
		decimalSeparatorPanel.add(customDecimalPointTFPanel);
		
		final ButtonGroup decimalPointGroup = new ButtonGroup();
		decimalPointGroup.add(rbtnDecimalSepPoint);
		decimalPointGroup.add(rbtnDecimalSepComma);
		decimalPointGroup.add(rbtnDecimalSepCustom);
		
		final ItemListener decimalPointListener = e -> {
			if (e.getStateChange() == ItemEvent.SELECTED
				&& !this.updateWithoutEvents
			) {
				checkDecimalPointButtons();				
			}
		};
		rbtnDecimalSepPoint.addItemListener(decimalPointListener);
		rbtnDecimalSepComma.addItemListener(decimalPointListener);
		rbtnDecimalSepCustom.addItemListener(decimalPointListener);
		
		final JLabel lblLineSeparator = new JLabel("Line break");
		final JLabel lblLineSeparatorHelp = new JLabel(ICON_INFO_2_16);
		lblLineSeparatorHelp.setToolTipText("Sets the line break");
		final JPanel lineSeparatorPanel = new JPanel(new GridLayout(1,3));
		rbtnLineSepWindows = new JRadioButton("Windows");
		rbtnLineSepWindows.setSelected(true);
		rbtnLineSepWindows.addItemListener(itemListener);
		rbtnLineSepLinux = new JRadioButton("Linux");
		rbtnLineSepLinux.setSelected(false);
		rbtnLineSepLinux.addItemListener(itemListener);
		rbtnLineSepMacOS = new JRadioButton("Mac OS X");
		rbtnLineSepMacOS.setSelected(false);
		rbtnLineSepMacOS.addItemListener(itemListener);
		lineSeparatorPanel.add(rbtnLineSepWindows);
		lineSeparatorPanel.add(rbtnLineSepLinux);
		lineSeparatorPanel.add(rbtnLineSepMacOS);
		
		final ButtonGroup lineBreakGroup = new ButtonGroup();
		lineBreakGroup.add(rbtnLineSepWindows);
		lineBreakGroup.add(rbtnLineSepLinux);
		lineBreakGroup.add(rbtnLineSepMacOS);
		
		customOptionsGroupLayout.setHorizontalGroup(customOptionsGroupLayout
			.createSequentialGroup()
			.addGroup(customOptionsGroupLayout.createParallelGroup()
				.addComponent(lblQuoteFields, Alignment.TRAILING)
				.addComponent(lblColumnSeparator, Alignment.TRAILING)
				.addComponent(lblDecimalSeparator, Alignment.TRAILING)
				.addComponent(lblLineSeparator, Alignment.TRAILING)
			)
			.addGroup(customOptionsGroupLayout.createParallelGroup()
				.addComponent(quotePanel)
				.addComponent(columnSeparatorPanel)
				.addComponent(decimalSeparatorPanel)
				.addComponent(lineSeparatorPanel)
			)
			.addGroup(customOptionsGroupLayout.createParallelGroup()
				.addComponent(lblQuoteFieldsHelp)
				.addComponent(lblColumnSeparatorHelp)
				.addComponent(lblDecimalSeparatorHelp)
				.addComponent(lblLineSeparatorHelp)
			)			
		);
		customOptionsGroupLayout.setVerticalGroup(customOptionsGroupLayout
			.createSequentialGroup()
			.addGroup(customOptionsGroupLayout.createParallelGroup()
				.addComponent(lblQuoteFields, Alignment.CENTER)
				.addComponent(quotePanel, Alignment.CENTER)
				.addComponent(lblQuoteFieldsHelp, Alignment.CENTER)
			)
			.addGroup(customOptionsGroupLayout.createParallelGroup()
				.addComponent(lblColumnSeparator, Alignment.CENTER)
				.addComponent(columnSeparatorPanel, Alignment.CENTER)
				.addComponent(lblColumnSeparatorHelp, Alignment.CENTER)
			)
			.addGroup(customOptionsGroupLayout
				.createParallelGroup()
				.addComponent(lblDecimalSeparator, Alignment.CENTER)
				.addComponent(decimalSeparatorPanel, Alignment.CENTER)
				.addComponent(lblDecimalSeparatorHelp, Alignment.CENTER)
			)
			.addGroup(customOptionsGroupLayout.createParallelGroup()
				.addComponent(lblLineSeparator, Alignment.CENTER)
				.addComponent(lineSeparatorPanel, Alignment.CENTER)
				.addComponent(lblLineSeparatorHelp, Alignment.CENTER)
			)
		);
		return customOptionsTaskPaneContainer;
	}

	private void formatCustomized() {
		if (!this.fileFormatCB.getSelectedItem().equals(FileFormat.CUSTOM)) {
			this.fileFormatCB.setSelectedItem(FileFormat.CUSTOM);
		} else {
			checkSeparators(true);
		}
		csvFormatEdited();
	}

	private void fileFormatSelectionChanged() {
		final FileFormat selectedItem = (FileFormat) 
			this.fileFormatCB.getSelectedItem();

		this.customFormatOptionsTaskPane.setCollapsed(
			!selectedItem.equals(FileFormat.CUSTOM));

		if (!selectedItem.equals(FileFormat.CUSTOM)) {
			this.updateWithoutEvents = true;
			updateCustomValues(selectedItem);
			this.updateWithoutEvents = false;
		}
		csvFormatEdited();
	}

	private void updateCustomValues(FileFormat format) {
		this.rbtnDecimalSepCustom.setSelected(true);
		this.rbtnSepCustom.setSelected(true);
		this.quoteFields.setSelected(false);
		this.txtFieldCustomDecimalPoint.setText(
			String.valueOf(symbols.getDecimalSeparator()));
		if (format.equals(FileFormat.EXCEL)) {
			this.rbtnLineSepWindows.setSelected(true);
			this.txtFieldCustomColumnSeparator.setText(";");
		} else if (format.equals(FileFormat.LIBRE_OFFICE)) {
			this.rbtnLineSepLinux.setSelected(true);
			if (symbols.getDecimalSeparator() == ',') {
				this.txtFieldCustomColumnSeparator.setText(";");
			} else {
				this.txtFieldCustomColumnSeparator.setText(",");
			}
		}
	}

	private void checkCustomSeparatorButtons() {
		txtFieldCustomColumnSeparator.setEditable(rbtnSepCustom.isSelected());
	}

	private void checkDecimalPointButtons() {
		txtFieldCustomDecimalPoint.setEditable(
			rbtnDecimalSepCustom.isSelected());
	}
	
	private void textFieldChanged() {
		SwingUtilities.invokeLater(() -> {
			checkSeparators(true);
			csvFormatEdited();
		});
	}

	private boolean checkSeparators(boolean gui) {
		if (fileFormatCB.getSelectedItem().equals(FileFormat.CUSTOM)
			&& !getColumnSeparator().equals("")
			&& getColumnSeparator().charAt(0) == getDecimalSeparator()) {
			if(gui) {
				JOptionPane.showMessageDialog(
					this,
					"You have selected the same column and decimal separator, "
					+ "which may result in an unreadable file.",
					"Invalid configuration",
					JOptionPane.WARNING_MESSAGE
				);
			}
			return false;
		}
		return true;
	}
	
	private void csvFormatEdited() {
		fireOnCsvFormatEditedEvent();
	}

	private void fireOnCsvFormatEditedEvent() {
		Arrays.asList(getCsvListeners()).forEach(l -> {
			l.onFormatEdited(new ChangeEvent(this));
		});
	}

	private String getColumnSeparator() {
		final String columnSeparator;
		
		if (rbtnSepTab.isSelected()) {
			columnSeparator = "\t";
		} else if (rbtnSepCustom.isSelected()) {
			columnSeparator = txtFieldCustomColumnSeparator.getText();
		} else {
			columnSeparator = null;
		}
		
		return columnSeparator;
	}

	private char getDecimalSeparator() {
		final char decimalSeparator;
		
		if (rbtnDecimalSepComma.isSelected()) {
			decimalSeparator = ',';
		} else if (rbtnDecimalSepPoint.isSelected()) {
			decimalSeparator = '.';
		} else if (rbtnDecimalSepCustom.isSelected()) {
			decimalSeparator = txtFieldCustomDecimalPoint.getText().charAt(0);
		} else {
			decimalSeparator = 0;
		}
		
		return decimalSeparator;
	}

	/**
	 * Returns {@code true} if the format introduced is valid and {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if the format introduced is valid and {@code false}
	 *         otherwise.
	 */
	public boolean isValidFormat() {
		return checkSeparators(false);
	}
	
	/**
	 * Establishes the selected {@code CsvFormat.FileFormat}.
	 * 
	 * @param format a {@code CsvFormat.FileFormat}.
	 */
	public void setSelectedCsvFileFormat(CsvFormat.FileFormat format) {
		this.fileFormatCB.setSelectedItem(format);
	}

	/**
	 * Return the {@code CsvFormat} based on the configuration selected by the
	 * user.
	 * 
	 * @return Return the {@code CsvFormat} based on the configuration selected
	 *         by the user.
	 */
	public CsvFormat getCsvFormat() {
		CsvFormat toret;

		final FileFormat selectedFormat = (FileFormat) 
			fileFormatCB.getSelectedItem();

		if (	selectedFormat.equals(FileFormat.EXCEL)	|| 
				selectedFormat.equals(FileFormat.LIBRE_OFFICE)
		) {
			try {
				toret = new CsvFormat(selectedFormat);
			} catch (Exception e) {
				return null;
			}
		} else {
			toret = new CsvFormat(
				getColumnSeparator(), 
				getDecimalSeparator(),
				quoteFields.isSelected(), 
				getLineBreak()
			);
		}

		return toret;
	}

	private String getLineBreak() {
		final String lineBreak;
		
		if (rbtnLineSepWindows.isSelected()) {
			lineBreak = "\r\n";
		} else {
			lineBreak = "\n";
		}
		
		return lineBreak;
	}

	/**
	 * Adds the specified CSV listener to receive component events from this
	 * component. If listener {@code l} is {@code null}, no exception is thrown
	 * and no action is performed.
	 *
	 * @param l
	 *            the {@code CsvListener}.
	 */
	public synchronized void addCsvListener(CsvListener l) {
		this.listenerList.add(CsvListener.class, l);
	}
	
	/**
	 * Returns an array of all the CSV listeners registered on this component.
	 *
	 * @return all {@code CsvListener}s of this component or an empty array if
	 *         no component listeners are currently registered
	 */
	public synchronized CsvListener[] getCsvListeners() {
		return this.listenerList.getListeners(CsvListener.class);
	}
}
