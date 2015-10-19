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

import java.util.HashMap;

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.ui.Template;

public class SimpleCrmMenu extends Template {

	private HashMap<String,Template> mainTemplatesByName = new HashMap<String,Template>();
	
	private Template createTemplateByName(String templateName) {
		switch (templateName) {
			case "ABOUT": {
				return new About();
			}
			case "MAP": {
				return new Map();
			}
			case "ANALYZE": {
				return new Analyze();
			}
			case "CUSTOMERS": {
				return new Customers();
			}
		}
		throw new IllegalArgumentException("Template with name " + templateName + " doesn't exist!");
	}
	
	public Template getOrCreateTemplate(String templateName) {
		Template t;
		if (mainTemplatesByName.containsKey(templateName)) {
			t = mainTemplatesByName.get(templateName);
			
		} else {
			t = createTemplateByName(templateName);
			mainTemplatesByName.put(templateName, t);
		}
		return t;
	}
	
	@TemplateEventHandler
	public void menuClick(String templateName) {
		Template t = getOrCreateTemplate(templateName);
		((SimpleCrmMain) getParent()).showTemplate(t);
	}
}
