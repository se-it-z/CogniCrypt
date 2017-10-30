/**
 * 
 */
package crossing.e1.taskintegrator.wizard;

import java.util.ArrayList;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import crossing.e1.configurator.Constants;
import crossing.e1.configurator.beginer.question.Answer;
import crossing.e1.configurator.beginer.question.ClaferDependency;
import crossing.e1.configurator.beginer.question.CodeDependency;
import crossing.e1.configurator.beginer.question.Question;
import crossing.e1.taskintegrator.models.ClaferConstraint;
import crossing.e1.taskintegrator.models.ClaferFeature;
import crossing.e1.taskintegrator.models.FeatureProperty;
import crossing.e1.taskintegrator.widgets.CompositeChoiceForModeOfWizard;
import crossing.e1.taskintegrator.widgets.CompositeForXsl;
import crossing.e1.taskintegrator.widgets.CompositeToHoldGranularUIElements;

/**
 * @author rajiv
 *
 */
public class PageForTaskIntegratorWizard extends WizardPage {

	private CompositeChoiceForModeOfWizard compositeChoiceForModeOfWizard = null;
	private CompositeToHoldGranularUIElements compositeToHoldGranularUIElements = null;

	private CompositeForXsl compositeForXsl = null;

	int counter = 0;// TODO for testing only.

	/**
	 * Create the wizard.
	 */
	public PageForTaskIntegratorWizard(String name, String title, String description) {
		super(name);
		setTitle(title);
		setDescription(description);

		// TODO improve the next button selection functionality.
		//this.setPageComplete(false);		
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		container.setBounds(0, 0, 1200, 500);
		setControl(container);

		switch (this.getName()) {
			case Constants.PAGE_NAME_FOR_MODE_OF_WIZARD:
				container.setLayout(new FillLayout(SWT.HORIZONTAL));
				setCompositeChoiceForModeOfWizard(new CompositeChoiceForModeOfWizard(container, SWT.NONE));
				break;
			case Constants.PAGE_NAME_FOR_CLAFER_FILE_CREATION:
				setCompositeToHoldGranularUIElements(new CompositeToHoldGranularUIElements(container, SWT.NONE, this.getName()));
				compositeToHoldGranularUIElements.setBounds(Constants.RECTANGLE_FOR_COMPOSITES);

				Button btnAddFeature = new Button(container, SWT.NONE);
				btnAddFeature.setBounds(Constants.RECTANGLE_FOR_FIRST_BUTTON_FOR_NON_MODE_SELECTION_PAGES);
				btnAddFeature.setText("Add Feature");
				btnAddFeature.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {

						// TODO dummy Clafer feature for testing only. Keep until the pop up is complete.
						/*
						 * counter++; ClaferFeature tempFeature = getDummyClaferFeature(); // Update the array list.
						 * compositeToHoldGranularUIElements.getListOfAllClaferFeatures().add(tempFeature);
						 * compositeToHoldGranularUIElements.addGranularClaferUIElements(tempFeature);
						 */
						counter++;
						ClaferFeatureDialog cfrFeatureDialog = new ClaferFeatureDialog(getShell());
						if (cfrFeatureDialog.open() == 0) {
							ClaferFeature tempFeature = cfrFeatureDialog.getResult();

							// Update the array list.							
							compositeToHoldGranularUIElements.getListOfAllClaferFeatures().add(tempFeature);
							compositeToHoldGranularUIElements.addGranularClaferUIElements(tempFeature);
						}

					}

				});
				break;
			case Constants.PAGE_NAME_FOR_XSL_FILE_CREATION:
				
				this.setCompositeForXsl(new CompositeForXsl(container, SWT.NONE));
				
				Button btnAddXSLTag = new Button(container, SWT.PUSH);//Add button to add the xsl tag in the code
				btnAddXSLTag.setBounds(Constants.RECTANGLE_FOR_FIRST_BUTTON_FOR_NON_MODE_SELECTION_PAGES);
				btnAddXSLTag.setText("Add Xsl Tag");
				Button btnReadCode = new Button(container, SWT.PUSH);//Add button to add the xsl tag in the code
				btnReadCode.setBounds(Constants.RECTANGLE_FOR_SECOND_BUTTON_FOR_NON_MODE_SELECTION_PAGES);
				btnReadCode.setText("Get the code");
				
				btnReadCode.addSelectionListener(new SelectionAdapter(){
					/* (non-Javadoc)
					 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
					 */

					@Override
					public void widgetSelected(SelectionEvent e) {

						super.widgetSelected(e);
						
						FileDialog fileDialog = new FileDialog(getShell(),SWT.OPEN);
						
						fileDialog.setFilterExtensions(new String[] {"*.java","*.xsl"});
				        fileDialog.setText("Choose the code file:");
				        ((CompositeForXsl)getCompositeForXsl()).updateTheTextFieldWithFileData(fileDialog.open());  
					}

			        
				});

				btnAddXSLTag.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						
						
						XSLTagDialog dialog = new XSLTagDialog(getShell());
						if(dialog.open() == Window.OK){
							// To locate the position of the xsl tag to be introduce						
							Point selected = getCompositeForXsl().getXslTxtBox().getSelection();
							String xslTxtBoxContent = getCompositeForXsl().getXslTxtBox().getText();
							xslTxtBoxContent = xslTxtBoxContent.substring(0, selected.x) + dialog.getTag().toString() + xslTxtBoxContent.substring(selected.y, xslTxtBoxContent.length());
							getCompositeForXsl().getXslTxtBox().setText(xslTxtBoxContent);
						}
						
					}
				});				
				break;
			case Constants.PAGE_NAME_FOR_HIGH_LEVEL_QUESTIONS:
				setCompositeToHoldGranularUIElements(new CompositeToHoldGranularUIElements(container, SWT.NONE, this.getName()));
				//this.compositeToHoldGranularUIElements.setBounds(Constants.RECTANGLE_FOR_COMPOSITES);
				PageForTaskIntegratorWizard claferPage=(PageForTaskIntegratorWizard)(this.getPreviousPage().getPreviousPage());
				CompositeToHoldGranularUIElements claferPageComposite=(CompositeToHoldGranularUIElements) claferPage.getCompositeToHoldGranularUIElements();

				QuestionDialog questionDialog = new QuestionDialog(parent.getShell() /*compositeToHoldGranularUIElements.getListOfAllClaferFeatures()claferFeatures,claferPageComposite*/);
				Button qstnDialog = new Button(container, SWT.NONE);
				qstnDialog.setBounds(889, 10, 115, 29);
				qstnDialog.setText("Add Question");
				
				qstnDialog.addSelectionListener(new SelectionAdapter() {
				
					@Override
					public void widgetSelected(SelectionEvent e) {
						int response = questionDialog.open();
						if (response == Window.OK) {
							counter++;
							//Question questionDetails = getDummyQuestion(questionDialog.getQuestionText(),questionDialog.getquestionType(),questionDialog.getAnswerValue());
							Question questionDetails = questionDialog.getQuestionDetails();
							questionDetails.setId(counter);

							// Update the array list.
							compositeToHoldGranularUIElements.getListOfAllQuestions().add(questionDetails);
							compositeToHoldGranularUIElements.addQuestionUIElements(questionDetails, claferPageComposite.getListOfAllClaferFeatures());
						}
					}
				});
				break;
		}
	}
	
	/**
	 * For testing only. Remove later.
	 * @return
	 */
	private ClaferFeature getDummyClaferFeature() {
		/*
		 * ClaferFeature tempFeature = new ClaferFeature(Constants.FeatureType.ABSTRACT, Integer.toString(counter), // Counter as the name to make each addition identifiable. new
		 * FeatureProperty("Enum", "integer"), null);
		 */ClaferFeature tempFeature = new ClaferFeature(Constants.FeatureType.ABSTRACT, Integer.toString(counter), // Counter as the name to make each addition identifiable.
			"");

		// from symmetric encryption abstract Algorithm
		tempFeature.getfeatureProperties().add(new FeatureProperty("name", "string"));
		tempFeature.getfeatureProperties().add(new FeatureProperty("description", "string"));
		tempFeature.getfeatureProperties().add(new FeatureProperty("security", "Security"));
		tempFeature.getfeatureProperties().add(new FeatureProperty("performance", "Performance"));
		tempFeature.getfeatureProperties().add(new FeatureProperty("classPerformance", "Performance"));

		// from symmetric encryption concrete SHA: Digest

		tempFeature.getFeatureConstraints().add(new ClaferConstraint("outputSize = 160 || outputSize = 224 || outputSize = 256 || outputSize = 384 || outputSize = 512"));
		tempFeature.getFeatureConstraints().add(new ClaferConstraint("outputSize = 160 => performance = VeryFast && security = Weak"));
		tempFeature.getFeatureConstraints().add(new ClaferConstraint("outputSize = 224 => performance = Fast && security = Strong"));
		tempFeature.getFeatureConstraints().add(new ClaferConstraint("description = \"PBKDF2 key derivation\""));
		tempFeature.getFeatureConstraints().add(new ClaferConstraint("security = cipher.security"));

		return tempFeature;

	}
	
	/**
	 * For testing only. Remove later.
	 * @return
	 */
	private Question getDummyQuestion() {
		Question tempQuestion = new Question();
		tempQuestion.setId(counter);
		tempQuestion.setQuestionText("question?");
		
		Answer answer = new Answer();
		answer.setValue("answer");
		answer.setDefaultAnswer(false);
		answer.setNextID(counter);
		ClaferDependency claferDependency = new ClaferDependency();
		claferDependency.setAlgorithm("algoritm");
		claferDependency.setOperand("operand");
		claferDependency.setOperator(Constants.FeatureConstraintRelationship.AND.toString());
		claferDependency.setValue("value");
		ArrayList<ClaferDependency> claferDependencies = new ArrayList<ClaferDependency>();
		claferDependencies.add(claferDependency);		
		
		CodeDependency codeDependency = new CodeDependency();
		codeDependency.setOption("option");
		codeDependency.setValue("value");
		ArrayList<CodeDependency> codeDependencies = new ArrayList<CodeDependency>();
		codeDependencies.add(codeDependency);
		
		answer.setCodeDependencies(codeDependencies);
		answer.setClaferDependencies(claferDependencies);
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(answer);
		
		tempQuestion.setAnswers(answers);
		
		return tempQuestion;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {

		// each case needs to be handled separately. By default all cases will return false. 
		/*
		 * switch(this.getName()){ case Constants.PAGE_NAME_FOR_MODE_OF_WIZARD: if(((boolean)compositeChoiceForModeOfWizard.getData(Constants.WIDGET_DATA_IS_GUIDED_MODE_CHOSEN) ==
		 * true || (boolean)compositeChoiceForModeOfWizard.getData(Constants.WIDGET_DATA_IS_GUIDED_MODE_FORCED) == true) && !this.isPageComplete()){ return true; } case
		 * Constants.PAGE_NAME_FOR_CLAFER_FILE_CREATION: return false; case Constants.PAGE_NAME_FOR_XSL_FILE_CREATION: return false; case
		 * Constants.PAGE_NAME_FOR_HIGH_LEVEL_QUESTIONS: return false; default: return false; }
		 */
		return true;

	}

	/**
	 * @return the compositeChoiceForModeOfWizard
	 */
	public Composite getCompositeChoiceForModeOfWizard() {
		return compositeChoiceForModeOfWizard;
	}

	/**
	 * @param compositeChoiceForModeOfWizard
	 *        the compositeChoiceForModeOfWizard to set
	 */
	private void setCompositeChoiceForModeOfWizard(CompositeChoiceForModeOfWizard compositeChoiceForModeOfWizard) {
		this.compositeChoiceForModeOfWizard = compositeChoiceForModeOfWizard;
	}

	/**
	 * @return the compositeToHoldGranularUIElements
	 */
	public Composite getCompositeToHoldGranularUIElements() {
		return compositeToHoldGranularUIElements;
	}

	/**
	 * @param compositeToHoldGranularUIElements
	 *        the compositeToHoldGranularUIElements to set
	 */
	public void setCompositeToHoldGranularUIElements(CompositeToHoldGranularUIElements compositeToHoldGranularUIElements) {
		this.compositeToHoldGranularUIElements = compositeToHoldGranularUIElements;
	}

	public int getCounter() {
		return counter;
	}

	/**
	 * @return the compositeForXsl
	 */
	public CompositeForXsl getCompositeForXsl() {
		return compositeForXsl;
	}

	/**
	 * @param compositeForXsl
	 *        the compositeForXsl to set
	 */
	public void setCompositeForXsl(CompositeForXsl compositeForXsl) {
		this.compositeForXsl = compositeForXsl;

	}
}
