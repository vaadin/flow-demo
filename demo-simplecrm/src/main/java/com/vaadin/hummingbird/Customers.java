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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.vaadin.teemu.jsoncontainer.JsonContainer;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.data.Item;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Template;

@JavaScript({ "customers.js" })
@Bower({ "vaadin-grid" })
public class Customers extends Template {

	protected Grid customersGrid;
	
	protected JsonContainer dataSource = null;
	
	protected void deleteCustomer(Object itemId) throws IllegalArgumentException {
		dataSource.removeItem(itemId);
		this.getElement().getNode().enqueueRpc("closeEditor()");
	}
	
	protected void updateCustomer(Object itemId, CustomerModel customer) {
		Item item = dataSource.getItem(itemId);
		mapModelToItem(customer, item);
		this.getElement().getNode().enqueueRpc("closeEditor()");
	}
	
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
		this.getElement().getNode().enqueueRpc("amendGrid($0);", customersGrid.getElement());
		customersGrid.addSelectionListener(new SelectionListener() {
			public void onEvent(SelectionEvent event) {
				Set<Object> selected = event.getSelected();
				if (selected.size() == 1) {
					Object itemId = selected.iterator().next();
					showForm(itemId);
				}
			}
		});
	}
	
	private void mapItemToModel(Item item, CustomerModel cm) {
		cm.setEmail((String) item.getItemProperty("email").getValue());
		cm.setFirstName((String) item.getItemProperty("firstName").getValue());
		cm.setLastName((String) item.getItemProperty("lastName").getValue());
		cm.setBirthDate((String) item.getItemProperty("birthDate").getValue());
		cm.setGender((String) item.getItemProperty("gender").getValue());
		cm.setStatus((String) item.getItemProperty("status").getValue());
	}
	
	private void mapModelToItem(CustomerModel cm, Item item) {
		item.getItemProperty("email").setValue(cm.getEmail());
		item.getItemProperty("firstName").setValue(cm.getFirstName());
		item.getItemProperty("lastName").setValue(cm.getLastName());
		item.getItemProperty("birthDate").setValue(cm.getBirthDate());
		item.getItemProperty("gender").setValue(cm.getGender());
		item.getItemProperty("status").setValue(cm.getStatus());
	}
	
	@TemplateEventHandler
	public void showForm(Object itemId) {
		if (itemId != null) {
			// TODO: use existing form if one was already created
			CustomerForm customerForm = new CustomerForm(itemId);
			CustomerModel cm = customerForm.getModel();
			Item item = dataSource.getItem(itemId);
			mapItemToModel(item, cm);
			Element e = this.getElementById("form-wrapper");
			e.removeAllChildren();
			e.appendChild(customerForm.getElement());
			this.getElement().getNode().enqueueRpc("displayEditor()");
		}
	}

	@TemplateEventHandler
	public void filterCustomers(String filterText) {
		
	}
}
