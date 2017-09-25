package com.erbis.test.onedesk.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;

import elemental.client.Browser;
import elemental.dom.Element;
import elemental.html.InputElement;
import elemental.html.OptionElement;
import elemental.html.SelectElement;

public class OneDesk implements EntryPoint {

	private static final String SUCCESS_MESSAGE_ID = "success-message";
	private static final String CONTAINER_ID = "container";
	private static final String HIDDEN_STYLE_NAME = "hidden";
	private static final String FIELDS_ERROR_ID = "fields-error";
	private static final String CHECKBOX_ERROR_ID = "checkbox-error";
	private static final String ERROR_CLASSNAME = "validation-error";
	private static final String CHECK_BOX_ID = "check";
	private static final String JOB_BOX_ID = "job";
	private static final String NAME_BOX_ID = "name";
	private static final String SEND_BUTTON_ID = "send";
	private static final String NAME_WRAPPER_ID = "name-wrapper";
	private static final String JOB_WRAPPER_ID = "job-wrapper";
	
	private List<Job> jobs = Arrays.asList(new Job("tinker", "Tinker"),
			new Job("tailor", "Tailor"),
			new Job("soldier", "Soldier"),
			new Job("sailor", "Sailor"));

	private Element sendButton;
	private InputElement nameBox;
	private SelectElement jobBox;
	private InputElement checkBox;
	
	private Element nameWrapper;
	private Element jobWrapper;
	
	private Element checkboxError;
	private Element fieldsError;
	
	private Element container;
	private Element successMessage;
	
	
	@Override
	public void onModuleLoad() {
		initUi();
		sendButton.addEventListener("click", (event) -> send());
		nameBox.addEventListener("focus", (event) -> clearError(nameWrapper));
		jobBox.addEventListener("change", (event) -> clearError(jobWrapper));
	}
	
	
	private void send() {
		if (validate()) {
//			Scheduler.get().scheduleDeferred(() -> alert("Sendind:\n" + "Name: "
//					+ nameBox.getValue() + ";\n" + "Job: " + jobBox.getValue() + ";\n" + "Terms accepted."));
			hideElement(container);
			showElement(successMessage);
		}
	}
	
	private void alert(String message) { 
		Browser.getWindow().alert(message);
	}
	
	private boolean validate() {
		boolean valid = true;
		boolean fieldEmpty = false;
		if (nameBox.getValue() == null || nameBox.getValue().isEmpty()) {
			markError(nameWrapper);
			fieldEmpty = true;
		}
		if (jobBox.getValue() == null || jobBox.getValue().isEmpty()) {
			markError(jobWrapper);
			fieldEmpty = true;
		}
		if (!checkBox.isChecked()) {
			showElement(checkboxError);
			valid = false;
		} else {
			hideElement(checkboxError);
		}
		if (fieldEmpty) {
			showElement(fieldsError);
			valid = false;
		} else {
			hideElement(fieldsError);
		}
		
		return valid;
	}


	private void hideElement(Element element) {
		element.getClassList().add(HIDDEN_STYLE_NAME);
	}


	private void showElement(Element element) {
		element.getClassList().remove(HIDDEN_STYLE_NAME);
	}

	private void clearError(Element element) {
		element.getClassList().remove(ERROR_CLASSNAME);
	}
	
	private void markError(Element element) {
		element.getClassList().add(ERROR_CLASSNAME);
	}

	private void initUi() {
		sendButton = Browser.getDocument().getElementById(SEND_BUTTON_ID);
		nameBox = (InputElement) Browser.getDocument().getElementById(NAME_BOX_ID);
		jobBox = (SelectElement) Browser.getDocument().getElementById(JOB_BOX_ID);
		checkBox = (InputElement) Browser.getDocument().getElementById(CHECK_BOX_ID);
		nameWrapper = Browser.getDocument().getElementById(NAME_WRAPPER_ID);
		jobWrapper = Browser.getDocument().getElementById(JOB_WRAPPER_ID);
		
		checkboxError = Browser.getDocument().getElementById(CHECKBOX_ERROR_ID);
		fieldsError = Browser.getDocument().getElementById(FIELDS_ERROR_ID);
		
		container = Browser.getDocument().getElementById(CONTAINER_ID);
		successMessage = Browser.getDocument().getElementById(SUCCESS_MESSAGE_ID);
		
		initJobs(jobs);
	}
	
	private void initJobs(List<Job> jobs) {
		jobs.stream().forEach(job -> {
			OptionElement jobOption = Browser.getDocument().createOptionElement();
			jobOption.setValue(job.getId());
			jobOption.setTextContent(job.getName());
			jobBox.appendChild(jobOption);
		});
		refreshSelect();
	}

	private native void refreshSelect() /*-{
		 $wnd.$('.selectpicker').selectpicker('refresh'); 
	}-*/;

	
	private static class Job {
		private String id;
		private String name;
		public Job(String id, String name) {
			super();
			this.setId(id);
			this.setName(name);
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
