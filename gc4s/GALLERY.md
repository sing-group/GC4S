Gallery of GC4S components
==========================

In this section you can find different examples of GC4S components. Code for all examples is provided at the `gc4s-demo` module.

Gallery contents:

  * [AbstractInputJDialog](#abstractinputjdialog)
  * [CardsPanel](#cardspanel)
  * [CloseableJTabbedPane](#closeablejtabbedpane)
  * [ColorKeyLegend](#colorkeylegend)
  * [ColorLegend](#colorlegend)
  * [ColorsListPanel](#colorslistpanel)
  * [ColumnSummaryTabeCellRenderer](#columnsummarytabecellrenderer)
  * [ComponentsListPanel](#componentslistpanel)
  * [CsvPanel](#csvpanel)
  * [DoubleRangeInputPanel](#doublerangeinputpanel)
  * [DoubleRangeSpinnerInputPanel](#doublerangespinnerinputpanel)
  * [ExtendedJComboBox](#extendedjcombobox)
  * [ExtendedJTabbedPane](#extendedjtabbedpane)
  * [ExtendedJXTable](#extendedjxtable)
  * [FilterableJXTable](#filterablejxtable)
  * [FontConfigurationPanel](#fontconfigurationpanel)
  * [Icons](#icons)
  * [IntegerRangeInputPanel](#integerrangeinputpanel)
  * [ItemSelectionPanel](#itemselectionpanel)
  * [JFileChooserPanel](#jfilechooserpanel)
  * [JListPanel](#jlistpanel)
  * [JInputList](#jinputlist)
  * [JLimitedTextField](#jlimitedtextfield)
  * [JMultipleFileChooserPanel](#jmultiplefilechooserpanel)
  * [JParallelListsPanel](#jparallellistspanel)
  * [JProgressDialog](#jprogressdialog)
  * [RadioButtonsPanel](#radiobuttonspanel)
  * [RangeInputPanel](#rangeinputpanel)
  * [RowHeaderExtendedJXTable](#rowheaderextendedjxtable)
  * [Wizard](#wizard)
  * [WorkingDialog](#workingdialog)


AbstractInputJDialog
--------------------
An extension of `JDialog` to ease the creation of new input dialogs by providing common functionalities such as buttons pane, description or key bindings.

![AbstractInputJDialog](screenshots/AbstractInputJDialog.png)

CardsPanel
----------
A component that displays different components using a `CardLayout` and creates a combo box to control which one should be visible.

![CardsPanel](screenshots/CardsPanel.gif)

CloseableJTabbedPane
--------------------
An extension of `JTabbedPane` that adds a close button to tabs.

![CloseableJTabbedPane](screenshots/CloseableJTabbedPane.gif)

ColorKeyLegend
--------------
A component for displaying color key legends.

![ColorKeyLegend](screenshots/ColorKeyLegend.png)

ColorLegend
-----------
A component for displaying color legends.

![ColorLegend](screenshots/ColorLegend.png)

ColorsListPanel
---------------
A panel that allows the selection of several colors.

![ColorsListPanel](screenshots/ColorsListPanel.png)

ColumnSummaryTabeCellRenderer
-----------------------------
A table cell renderer that displays a column summary.

![ColumnSummaryTabeCellRenderer](screenshots/ColumnSummaryTabeCellRenderer.png)

ComponentsListPanel
-------------------
A component that allows showing a list of generic components with control buttons to add and remove them.

![ComponentsListPanel](screenshots/ComponentsListPanel.png)

CsvPanel
--------
A panel that allows user to configure a CSV format.

![CsvPanel](screenshots/CsvPanel.png)

DoubleRangeInputPanel
---------------------
A panel that allows user to type a range of double values, checking that the minimum value is equal or lower than the maximum value.

![DoubleRangeInputPanel](screenshots/DoubleRangeInputPanel.png)

DoubleRangeSpinnerInputPanel
----------------------------
A panel that allows user to type a range of double values by using spinners.

![DoubleRangeSpinnerInputPanel](screenshots/DoubleRangeSpinnerInputPanel.png)

ExtendedJComboBox
-----------------
An extension of `JComboBox` that adjust its width to the maximum item width when the drop down list is being displayed.

![ExtendedJComboBox](screenshots/ExtendedJComboBox.png)

ExtendedJTabbedPane
-------------------
An extension of `JTabbedPane` that allows setting wether the tab bar must be hidden if there is only one tab.

![JProgressDialog](screenshots/ExtendedJTabbedPaneDemo.gif)

ExtendedJXTable
---------------
An extension of `JXTable` that allows to hide/show the column visibility actions and also facilitates adding own actions to the `ColumnControlButton`.

![ExtendedJXTable](screenshots/ExtendedJXTable.png)

FilterableJXTable
-----------------
An extension of `JXTable` that allows to establish a row filter in an easy way.

![FilterableJXTable](screenshots/FilterableJXTable.png)

FontConfigurationPanel
----------------------
A panel that allows configuring a `Font`.

![FontConfigurationPanel](screenshots/FontConfigurationPanel.png)

Icons
-----
A class that provides a great variety of icons as static constants.

![Icons](screenshots/Icons.png)

IntegerRangeInputPanel
----------------------
A panel that allows user typing a range defined by values, taking into account that the minimum value should always be lower or equal to the maximum value.

![IntegerRangeInputPanel](screenshots/IntegerRangeInputPanel.png)

ItemSelectionPanel
------------------
A panel that allows users choosing a specified number of items from  different combo boxes.

![ItemSelectionPanel](screenshots/ItemSelectionPanel.png)

JFileChooserPanel
-----------------
A component with a button to select a file (using a `JFileChooser`) and a text field that show the selected file.

![JFileChooserPanel](screenshots/JFileChooserPanel.png)

JListPanel
----------
A component that wraps a `JList` to add common actions.

![JListPanel](screenshots/JListPanel.png)

This component can also show a text field that allows filtering the elements shown.

![JListPanel filtering demo](screenshots/JListPanel-FilterDemo.gif)

JInputList
----------
A class that encloses a `JListPanel<String>` to provide the ability of adding new elements to the list.
 
![JInputList](screenshots/JInputList.png)

JLimitedTextField
-----------------
An extension of `JTextField` to limit the length of the text.

![JLimitedTextField](screenshots/JLimitedTextField.png)

JMultipleFileChooserPanel
-------------------------
A component with a button to select multiple files (using a `JFileChooser`) and a list that shows the selected files.

![JMultipleFileChooserPanel](screenshots/JMultipleFileChooserPanel.png)

JParallelListsPanel
-------------------
A component that that wraps two `JListPanel` and allows moving elements between them.

![JParallelListsPanel](screenshots/JParallelLists.gif)

JProgressDialog
---------------
An extension of `JDialog` to ease the creation of simple progress dialogs based on a task list.

![JProgressDialog](screenshots/JProgressDialog.gif)

RadioButtonsPanel
-----------------
A panel that displays a list of elements using `JRadioButton`s. It is designed to be an alternative to `JComboBox` when you want all values visible to the user.

![RadioButtonsPanel](screenshots/RadioButtonsPanelDemo.png)

RangeInputPanel
---------------
A panel that allows user selecting a range of values using sliders, taking into account that the minimum value should always be lower or equal to the maximum value.

![RangeInputPanel](screenshots/RangeInputPanel.png)

RowHeaderExtendedJXTable
------------------------
An extension of `ExtendedJXTable` to allow users creating tables within a row names column.

![RowHeaderExtendedJXTable](screenshots/RowHeaderExtendedJXTable.png)

Wizard
------
A wizard dialog shows a list of wizard steps (implemented by `WizardStep` objects).

![Wizard](screenshots/Wizard.gif)

WorkingDialog
-------------
A dialog that shows a progress in work dialog with an indeterminate progress bar and a label. The indeterminate progress bar may be replaced by a user image.

![WorkingDialog](screenshots/WorkingDialog.gif)