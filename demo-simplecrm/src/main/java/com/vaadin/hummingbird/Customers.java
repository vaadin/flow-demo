/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.JavaScriptModule;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Page;
import com.vaadin.ui.Template;
import com.vaadin.ui.Grid.JS;

import elemental.json.JsonArray;

@JavaScript({"customers-snapshot.json","customers.js"})
@HTML({"vaadin://bower_components/iron-icons/social-icons.html","vaadin://bower_components/iron-icons/maps-icons.html"})
@Bower({"paper-button","paper-item","paper-dropdown-menu","paper-radio-button","paper-radio-group","paper-input","iron-form","iron-icons","iron-icon","paper-menu","vaadin-grid"})
public class Customers extends Template {
	
    @JavaScriptModule("customers.js")
    public interface JS {
        public void populate(String dataJson);
    }
	
	private Grid customersGrid = new Grid();
	
	@Override
	public void attach() {
		super.attach();
		customersGrid.addColumn("foo");
		customersGrid.addRow("bar");
	}

	@TemplateEventHandler
    public void getTestData() {
		String res = "[{\"id\": 1,\"firstName\": \"Gabrielle\",\"lastName\": \"Patel\",\"birthDate\": \"1943-07-17\",\"status\": \"ImportedLead\",\"gender\": \"Female\",\"email\": \"gabrielle@patel.com\",\"location\": {\"lat\": 42.38090140840465,\"lon\": -71.10852736243358},\"persisted\": true}]";
		// doesn't work
		Page.getCurrent().getJavaScript().execute("console.log('here');alert('hello');populate("+res+");");
		// doesn't work
		getJS(JS.class).populate(res);
	}
	
	@TemplateEventHandler
	public void save(String input) {
		System.out.println("Save: " + input);
	}
	
	@TemplateEventHandler
	public void delete(String input) {
		System.out.println("Delete: " + input);
	}
	
}
