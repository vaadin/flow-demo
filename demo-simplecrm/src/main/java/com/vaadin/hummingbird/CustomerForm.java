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

import java.text.ParseException;
import java.util.Date;

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Template;

public class CustomerForm extends Template {

	private Object itemId;
	
	public CustomerForm(Object itemId) {
		setItemId(itemId);
	}
	
	@TemplateEventHandler
	public void save() {
		CustomerModel m = getModel();
		((Customers) getParent()).updateCustomer(getItemId(), m);
	}

	@TemplateEventHandler
	public void delete() {
		((Customers) getParent()).deleteCustomer(getItemId());
	}
	
	@Override
	protected CustomerModel getModel() {
		return (CustomerModel) super.getModel();
	}
	
	@TemplateEventHandler
	public void setFirstName(String input) {
		CustomerModel m = getModel();
		m.setFirstName(input);
	}
	
	@TemplateEventHandler
	public void setLastName(String input) {
		CustomerModel m = getModel();
		m.setLastName(input);
	}
	
	@TemplateEventHandler
	public void setEmail(String input) {
		CustomerModel m = getModel();
		m.setEmail(input);
	}
	
	@TemplateEventHandler
	public void setBirthday(String input) {
		CustomerModel m = getModel();
		m.setBirthDate(input);
	}
	
	@TemplateEventHandler
	public void setStatus(String input) {
		CustomerModel m = getModel();
		m.setStatus(input);
	}
	
	@TemplateEventHandler
	public void setGender(String input) {
		CustomerModel m = getModel();
		m.setGender(input);
	}
	
	public Object getItemId() {
		return itemId;
	}

	public void setItemId(Object itemId) {
		this.itemId = itemId;
	}
	
}
