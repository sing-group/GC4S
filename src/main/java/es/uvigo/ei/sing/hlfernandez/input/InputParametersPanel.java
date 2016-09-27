package es.uvigo.ei.sing.hlfernandez.input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.utilities.Icons;

/**
 * An {@code InputParametersPanel} takes one or more {@link InputParameter} and
 * properly arranges them using a {@code GroupLayout}.
 * 
 * @author hlfernandez
 *
 */
public class InputParametersPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<InputParameter> parameters;
	
	private Map<InputParameter, JComponent> descriptionLabels;
	private Map<InputParameter, JComponent> inputComponents;
	private Map<InputParameter, JComponent> helpLabels;

	/**
	 * Creates a new {@code InputParametersPanel} using the list of
	 * {@code parameters}.
	 * 
	 * @param parameters
	 *            one or more {@code InputParemeter}.
	 */
	public InputParametersPanel(InputParameter...parameters) {
		this.parameters = Arrays.asList(parameters);
		this.initComponent();
	}

	private void initComponent() {
		this.initInputComponents();
		
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		this.setLayout(groupLayout);
		
		SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
		ParallelGroup first = groupLayout.createParallelGroup(Alignment.TRAILING);
		ParallelGroup second = groupLayout.createParallelGroup();
		ParallelGroup third = groupLayout.createParallelGroup();
		parameters.forEach(c -> {
			first.addComponent(descriptionLabels.get(c));
			second.addComponent(inputComponents.get(c));
			third.addComponent(helpLabels.get(c));
		});
		horizontalGroup
			.addGroup(first)
			.addGroup(second)
			.addGroup(third);
		groupLayout.setHorizontalGroup(horizontalGroup);
		
		SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
		parameters.forEach(c -> {
			ParallelGroup current = groupLayout.createParallelGroup(Alignment.CENTER);
			current.addComponent(descriptionLabels.get(c));
			current.addComponent(inputComponents.get(c));
			current.addComponent(helpLabels.get(c));
			verticalGroup.addGroup(current);
		});
		groupLayout.setVerticalGroup(verticalGroup);
	}

	private void initInputComponents() {
		descriptionLabels = new HashMap<InputParameter, JComponent>();
		inputComponents = new HashMap<InputParameter, JComponent>();
		helpLabels = new HashMap<InputParameter, JComponent>();
		parameters.forEach(c -> {
			descriptionLabels.put(c, new JLabel(c.getLabel()));
			inputComponents.put(c, c.getInput());
			
			JLabel helpLabel = new JLabel(Icons.ICON_HELP);
			helpLabel.setToolTipText(c.getHelpLabel());
			helpLabels.put(c, helpLabel);
		});
	}
}
