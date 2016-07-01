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
package com.vaadin.hummingbird.demo.simplecrm;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.JavaScript;
import com.vaadin.hummingbird.demo.simplecrm.data.Customer;
import com.vaadin.hummingbird.demo.simplecrm.data.CustomerService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.ui.AttachEvent;
import com.vaadin.ui.Template;

@JavaScript("customers.js")
@HtmlImport("context://bower_components/vaadin-grid/vaadin-grid.html")
public class Customers extends Template {

    // protected Grid customersGrid;
    @Id("form-wrapper")
    private Element formWrapper;

    protected void deleteCustomer(int id) {
        CustomerService.get().deleteCustomer(id);
        getUI().get().getPage().executeJavaScript("closeEditor()");
    }

    protected void updateCustomer(int id, Customer customer) {
        CustomerService.get().updateCustomer(id, customer);
        getUI().get().getPage().executeJavaScript("closeEditor()");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        // customersGrid.setContainerDataSource(getCustomerData().getDataSource());
        // getElement().getNode().enqueueRpc("amendGrid($0);",
        // customersGrid.getElement());
        // customersGrid.addSelectionListener(new SelectionListener() {
        // @Override
        // public void onEvent(SelectionEvent event) {
        // Set<Object> selected = event.getSelected();
        // if (selected.size() == 1) {
        // Object itemId = selected.iterator().next();
        // showForm(itemId);
        // }
        // }
        // });
    }

    @EventHandler
    public void showForm(int customerId) {
        // TODO: use existing form if one was already created
        CustomerForm customerForm = new CustomerForm(customerId);
        formWrapper.removeAllChildren();
        formWrapper.appendChild(customerForm.getElement());
        getUI().get().getPage().executeJavaScript("displayEditor()");
    }

    @EventHandler
    public void filterCustomers(String filterText) {
        // getCustomerData().filterCustomers(filterText);
    }

}
