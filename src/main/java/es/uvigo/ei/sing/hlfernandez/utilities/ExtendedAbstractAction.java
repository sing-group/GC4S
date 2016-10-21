package es.uvigo.ei.sing.hlfernandez.utilities;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * <p>
 * An extension of {@code AbstractAction} to reduce verbosity when creating
 * actions by allowing to construct actions by passing references to methods
 * without parameters.
 * </p>
 * 
 * <p>
 * For example:
 * </p>
 * <code>
 * class MyClass {
 * 
 * 	Action myAction = new ExtendedAbstractAction("An action", this::performAction);
 * 
 * 	public void performAction() {
 * 	}
 * 
 * }
 * </code>
 * 
 * @author hlfernandez
 *
 */
public class ExtendedAbstractAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private Action action;

	/**
	 * Creates a new {@code ExtendedAbstractAction} that invokes {@code action} 
	 * on action performed.
	 * 
	 * @param action the {@code Action} to invoke.
	 */
	public ExtendedAbstractAction(Action action) {
		super();
		this.action = action;
	}

	public ExtendedAbstractAction(String name, Action action) {
		super(name);
		this.action = action;
	}

	public ExtendedAbstractAction(String name, Icon icon, Action action) {
		super(name, icon);
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action.doAction();
	}
	
	@FunctionalInterface
	public interface Action {
		void doAction();
	}
}
