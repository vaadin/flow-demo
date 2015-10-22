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

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.vaadin.teemu.jsoncontainer.JsonContainer;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.JavaScriptModule;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.data.Container;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Page;
import com.vaadin.ui.Template;

@JavaScript({ "customers.js" })
@Bower({ "vaadin-grid" })
public class Customers extends Template {

	protected Grid customersGrid;
	
	protected JsonContainer dataSource = null;
	
	private void initDataSource() {
		if (dataSource != null) {
			return;
		}
		InputStream is = this.getClass().getResourceAsStream("customers-snapshot.json");
		try {
			String json = IOUtils.toString(is, "UTF-8");
			dataSource = JsonContainer.Factory.newInstance(json);
			is.close();
		} catch (Exception e) {
			System.out.println("Customers.java initDataSource exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Customers() {
		initDataSource();
	}
	
	@Override
	public void attach() {
		super.attach();
		initDataSource();
		customersGrid.setContainerDataSource(dataSource);
		Element e = this.getElementById("form-wrapper");
		e.appendChild(new CustomerForm().getElement());
		this.getElement().getNode().enqueueRpc("amendGrid($0);", customersGrid.getElement());
	}

}
