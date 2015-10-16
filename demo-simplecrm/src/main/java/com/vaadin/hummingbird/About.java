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

import java.util.List;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.Customers.JS;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.kernel.StateNode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Page;
import com.vaadin.ui.Template;

@HTML({ "vaadin://bower_components/iron-icons/social-icons.html",
		"vaadin://bower_components/iron-icons/maps-icons.html" })
@StyleSheet("http://fonts.googleapis.com/css?family=Open+Sans:400,300,700")
@Bower({ "paper-button", "paper-dropdown-menu", "paper-drawer-panel", "paper-header-panel", "paper-input",
		"paper-icon-button", "paper-item", "paper-menu", "paper-radio-button", "paper-radio-group", "paper-toolbar",
		"iron-flex-layout", "iron-form", "iron-icons", "iron-icon" })
public class About extends Template {
	
	@Override
	public void attach() {
		super.attach();
		Element e = this.getElementById("simplecrm-menu");
		e.appendChild(new SimpleCrmMenu().getElement());
	}
}
