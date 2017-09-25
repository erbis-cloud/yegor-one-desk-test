package com.erbis.test.onedesk.client;

import com.erbis.test.onedesk.client.register.RegisterComponent;
import com.google.gwt.core.client.EntryPoint;

import elemental.client.Browser;

public class OneDesk implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RegisterComponent component = new RegisterComponent();
		component.init(Browser.getDocument().getBody());
	}

}
