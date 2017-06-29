package org.sing_group.gc4s.wizard;

import static java.awt.BorderLayout.*;
import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXLabel;

import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.wizard.event.WizardStepEvent;
import org.sing_group.gc4s.wizard.event.WizardStepListener;

/**
 * A wizard dialog that allows showing a list of {@code WizardStep}.
 * 
 * @author hlfernandez
 * @see WizardStep
 */
public class Wizard extends JDialog implements WizardStepListener {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_INITIAL_HEIGHT = 500;
	public static final int DEFAULT_INITIAL_WIDTH = 700;
	
	private List<WizardStep> steps;

	private Window parent;
	private JPanel wizardMainPanel;
	private CardLayout wizardMainPanelLayout;
	private JButton nextStepButton;
	private JButton previousStepButton;
	private JButton cancelStepButton;
	private List<JLabel> wizardStepsSidebarLabels = new LinkedList<>();

	private int initialHeight = DEFAULT_INITIAL_HEIGHT;
	private int initialWidth = DEFAULT_INITIAL_WIDTH;
	
	private boolean canceled = false;
	private int currentStep = 0;

	/**
	 * Creates a new {@code Wizard} with the specified title and
	 * {@code WizardStep}s.
	 * 
	 * @param parent the parent {@code Window}
	 * @param wizardTitle the wizard title
	 * @param steps the list of {@code WizardStep}
	 */
	public Wizard(Window parent, String wizardTitle, List<WizardStep> steps) {
		super(parent);

		this.parent = parent;
		this.setTitle(wizardTitle);
		this.steps = steps;

		this.initComponent();
	}

	private void initComponent() {
		this.setModal(true);
		this.setResizable(false);
		this.setContentPane(getDialogContentPane());
		this.setSize(initialWidth, initialHeight);
		this.setPreferredSize(new Dimension(initialWidth, initialHeight));
		this.centerDialogOnScreen();
		this.pack();
	}

	private Container getDialogContentPane() {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(getWizardStepsSidebar(), WEST);
		contentPane.add(getWizardMainPanel(), CENTER);
		contentPane.add(getWizardButtonsPanel(), SOUTH);
		return contentPane;
	}

	private JComponent getWizardStepsSidebar() {
		JPanel wizardStepsSidebar = new JPanel();
		wizardStepsSidebar
			.setLayout(new BoxLayout(wizardStepsSidebar, BoxLayout.Y_AXIS));

		wizardStepsSidebar.add(Box.createVerticalGlue());
		this.steps.forEach(s -> {
			JXLabel label = new JXLabel(s.getStepTitle());
			label.setLineWrap(true);
			label.setMaximumSize(new Dimension(150, 1000));
			label.setBorder(createEmptyBorder(2, 0, 2, 0));
			wizardStepsSidebar.add(label);
			wizardStepsSidebarLabels.add(label);
		});
		wizardStepsSidebar.add(Box.createVerticalGlue());

		CenteredJPanel component = new CenteredJPanel(wizardStepsSidebar);
		component.setBorder(createEmptyBorder(5, 5, 5, 20));

		updateWizardStepSidebarLabelsStyle();
		return component;
	}

	private void updateWizardStepSidebarLabelsStyle() {
		for(int i = 0; i < wizardStepsSidebarLabels.size(); i++) {
			if(i == this.currentStep) {
				wizardStepsSidebarLabels.get(i).setForeground(Color.BLACK);
			} else {
				wizardStepsSidebarLabels.get(i).setForeground(Color.GRAY);
			}
		}
	}

	private JComponent getWizardMainPanel() {
		wizardMainPanel = new JPanel();
		wizardMainPanelLayout = new CardLayout();
		wizardMainPanel.setLayout(wizardMainPanelLayout);
		this.steps.forEach(s -> {
			wizardMainPanel.add(new WizardStepPanel(s));
		});
		this.wizardMainPanelLayout.show(wizardMainPanel, steps.get(0).getStepTitle());
		return wizardMainPanel;
	}
	
	private class WizardStepPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private WizardStep step;

		public WizardStepPanel(WizardStep step) {
			this.step = step;
			
			this.init();
		}

		private void init() {
			this.setLayout(new BorderLayout());
			this.setOpaque(true);
			this.add(getStepLabel(), NORTH);
			this.add(step.getStepComponent(), CENTER);
			this.step.addWizardStepListener(Wizard.this);
		}

		private JLabel getStepLabel() {
			JXLabel stepLabel = new JXLabel(step.getStepTitle());
			stepLabel.setFont(stepLabel.getFont().deriveFont(Font.BOLD, 20));
			stepLabel.setLineWrap(true);
			stepLabel.setBorder(createEmptyBorder(0, 0, 5, 0));
			return stepLabel;
		}
	}

	private JComponent getWizardButtonsPanel() {
		JPanel wizardButtonsPanel = new JPanel();
		wizardButtonsPanel.setLayout(new BoxLayout(wizardButtonsPanel , BoxLayout.X_AXIS));
		wizardButtonsPanel.setBorder(createEmptyBorder(10, 5, 5, 5));
		wizardButtonsPanel.add(Box.createHorizontalGlue());
		wizardButtonsPanel.add(getPreviousButton());
		wizardButtonsPanel.add(Box.createHorizontalStrut(5));
		wizardButtonsPanel.add(getNextButton());
		wizardButtonsPanel.add(Box.createHorizontalStrut(5));
		wizardButtonsPanel.add(getCancelButton());
		return wizardButtonsPanel;
	}
	
	private JButton getPreviousButton() {
		if(this.previousStepButton == null){
			this.previousStepButton = new JButton("Back");
			this.previousStepButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					showPrevioustStep();
				}
			});
			this.previousStepButton.setEnabled(canMoveToPreviousStep());
		}
		return this.previousStepButton;
	}
	
	private JButton getNextButton() {
		if(this.nextStepButton == null){
			this.nextStepButton = new JButton("Next");
			this.nextStepButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					showNextStep();
				}
			});
			this.nextStepButton.setEnabled(canMoveToNextStep());
		}
		return this.nextStepButton;
	}
	
	private JButton getCancelButton() {
		if(this.cancelStepButton == null){
			this.cancelStepButton = new JButton("Cancel");
			this.cancelStepButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelWizard();
				}
			});
		}
		return this.cancelStepButton;
	}

	private void cancelWizard() {
		this.canceled = true;
		dispose();
	}

	private void showNextStep() {
		if(isFinalStep()) {
			wizardFinished();
		}
		else if(canMoveToNextStep()) {
			this.wizardMainPanelLayout.next(this.wizardMainPanel);
			this.currentStep++;
			this.stepChanged();
		}
	}
	
	protected void wizardFinished() {
		this.canceled = false;
		dispose();
	}

	private void showPrevioustStep() {
		if(canMoveToPreviousStep()) {
			this.wizardMainPanelLayout.previous(this.wizardMainPanel);
			this.currentStep--;
			this.stepChanged();
		}
	}
	
	private boolean canMoveToPreviousStep() {
		return this.currentStep > 0;
	}

	private boolean canMoveToNextStep() {
		return this.steps.get(currentStep).isStepCompleted();
	}

	private void stepChanged() {
		this.checkButtonsStatus();
		this.updateWizardStepSidebarLabelsStyle();
		this.checkStep();
		this.notifyStepEntered();
		this.nextStepButton.setToolTipText(null);
	}

	private void checkStep() {
		if (isFinalStep()) {
			this.nextStepButton.setText("Finish");
		} else {
			this.nextStepButton.setText("Next");
		}
	}

	private void notifyStepEntered() {
		this.steps.get(this.currentStep).stepEntered();
	}

	private boolean isFinalStep() {
		return this.currentStep == (this.steps.size() - 1);
	}

	/**
	 * Centers the dialog in the parent component.
	 */
	protected void centerDialogOnScreen() {
		this.pack();
		this.setLocationRelativeTo(parent);
	}

	@Override
	public void wizardStepCompleted(WizardStepEvent event) {
		this.checkButtonsStatus();
	}

	@Override
	public void wizardStepUncompleted(WizardStepEvent event) {
		this.checkButtonsStatus();
	}

	@Override
	public void wizardStepNextButtonTooltipChanged(String nextButtonTooltip) {
		if (nextButtonTooltip.isEmpty()) {
			setNextButtonTooltip(null);
		} else {
			setNextButtonTooltip(nextButtonTooltip);
		}
	}

	private void setNextButtonTooltip(String nextButtonTooltip) {
		SwingUtilities.invokeLater(() -> {
			this.nextStepButton.setToolTipText(nextButtonTooltip);
		});
	}

	private void checkButtonsStatus() {
		this.nextStepButton.setEnabled(canMoveToNextStep());
		this.previousStepButton.setEnabled(canMoveToPreviousStep());
	}
	
	/**
	 * Returns {@code true} if the dialog has been canceled and {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if the dialog has been canceled and {@code false}
	 *         otherwise
	 */
	public boolean isCanceled() {
		return canceled;
	}
	
	protected List<WizardStep> getSteps() {
		return steps;
	}
}
