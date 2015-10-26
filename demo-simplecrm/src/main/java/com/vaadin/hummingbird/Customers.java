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
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.vaadin.teemu.jsoncontainer.JsonContainer;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Template;

@JavaScript({ "customers.js" })
@Bower({ "vaadin-grid" })
public class Customers extends Template {

	protected Grid customersGrid;	
	protected void deleteCustomer(Object itemId) {
		getCustomerData().deleteCustomer(itemId);
		this.getElement().getNode().enqueueRpc("closeEditor()");
	}
	
	protected void updateCustomer(Object itemId, CustomerModel customer) {
		getCustomerData().updateCustomer(itemId, customer);		
		this.getElement().getNode().enqueueRpc("closeEditor()");
	}	
	
	@Override
	public void attach() {
		super.attach();
		customersGrid.setContainerDataSource(getCustomerData().getDataSource());
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
	
	@TemplateEventHandler
	public void showForm(Object itemId) {
		if (itemId != null) {
			// TODO: use existing form if one was already created
			CustomerForm customerForm = new CustomerForm(itemId);
			CustomerModel cm = customerForm.getModel();
			getCustomerData().modelFromItemId(itemId, cm);
			Element e = this.getElementById("form-wrapper");
			e.removeAllChildren();
			e.appendChild(customerForm.getElement());
			this.getElement().getNode().enqueueRpc("displayEditor()");
		}
	}

	@TemplateEventHandler
	public void filterCustomers(String filterText) {
		getCustomerData().filterCustomers(filterText);
	}
	
	
	private CustomerData getCustomerData() {
		SimpleCrmMain main = ((SimpleCrmMain) getParent());
		return main.getCustomerData();
	}
}
