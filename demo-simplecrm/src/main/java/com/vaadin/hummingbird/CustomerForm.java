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

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.data.Container;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Template;

public class CustomerForm extends Template {

	@TemplateEventHandler
	public void save(String input) {
		System.out.println("Save: " + input);
	}

	@TemplateEventHandler
	public void delete(String input) {
		// TODO: Fix this to edit grid's datasource instead of creating a new one
		// after that feature works
		Container.Indexed dataSource;
		Grid customersGrid = ((Customers) getParent()).customersGrid;
		InputStream is = this.getClass().getResourceAsStream("customers-snapshot.json");
		try {
			String json = IOUtils.toString(is, "UTF-8");
			dataSource = JsonContainer.Factory.newInstance(json);
			dataSource.removeItem(dataSource.getIdByIndex((Integer.parseInt(input))));
			customersGrid.setContainerDataSource(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
