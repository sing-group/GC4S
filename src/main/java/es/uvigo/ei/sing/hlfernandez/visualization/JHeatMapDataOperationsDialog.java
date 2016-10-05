package es.uvigo.ei.sing.hlfernandez.visualization;

import java.awt.Window;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapOperations.Centering;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapOperations.Transform;

public class JHeatMapDataOperationsDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = 
		"This dialog allows you to apply different operations to the current "
		+ "data matrix. Operations are performed in the order they appear.";
	
	private JPanel inputComponents;
	private JComboBox<Centering> centerMethod;
	private JComboBox<Transform> transformMethod;

	protected JHeatMapDataOperationsDialog(Window parent) {
		super(parent);
	}
	
	@Override
	protected String getDialogTitle() {
		return "Operations dialog";
	}

	@Override
	protected String getDescription() {
		return DESCRIPTION;
	}

	@Override
	protected JPanel getInputComponentsPane() {
		if(this.inputComponents == null) {
			this.inputComponents = new InputParametersPanel(getParameters());
		}
		return this.inputComponents;
	}

	private InputParameter[] getParameters() {
		List<InputParameter> toret = new LinkedList<>();
		toret.add(getLogTransformationParameter());
		toret.add(getCenteringParameter());
		return toret.toArray(new InputParameter[toret.size()]);
	}
	
	private InputParameter getLogTransformationParameter() {
		return 	new InputParameter(
				"Transform", getTransformationComponent(), 
				"The transformation method. It is applied to each "
				+ "individual value of the data matrix.");
	}

	private JComponent getTransformationComponent() {
		this.transformMethod = 
			new JComboBox<>(Transform.values());
		return transformMethod;
	}

	public Transform getTransform() {
		return (Transform) this.transformMethod.getSelectedItem();
	}

	private InputParameter getCenteringParameter() {
		return 	new InputParameter(
				"Row centering", getCenteringComponent(), 
				"The row centering method.");
	}

	private JComponent getCenteringComponent() {
		this.centerMethod = 
			new JComboBox<>(Centering.values());
		return centerMethod;
	}

	public Centering getCentering() {
		return (Centering) this.centerMethod.getSelectedItem();
	}
	
	@Override
	public void setVisible(boolean b) {
		this.okButton.setEnabled(true);
		this.pack();
		super.setVisible(b);
	}
}
