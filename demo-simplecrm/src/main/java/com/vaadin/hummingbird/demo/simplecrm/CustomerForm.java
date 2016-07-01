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

import java.util.Optional;

import com.vaadin.annotations.EventHandler;
import com.vaadin.hummingbird.demo.simplecrm.data.Customer;
import com.vaadin.hummingbird.demo.simplecrm.data.CustomerService;
import com.vaadin.hummingbird.demo.simplecrm.data.Gender;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

public class CustomerForm extends Template {

    public interface CustomerFormModel extends TemplateModel {
        public Customer getCustomer();

        public void setCustomer(Customer customer);
    }

    private int customerId;

    public CustomerForm(int customerId) {
        this.customerId = customerId;
        Optional<Customer> customer = CustomerService.get()
                .getCustomer(customerId);
        getModel().setCustomer(customer.get());
    }

    @EventHandler
    public void save() {
        Customer customer = getModel().getCustomer();
        CustomerService.get().updateCustomer(customerId, customer);
    }

    @EventHandler
    public void delete() {
        CustomerService.get().deleteCustomer(customerId);
    }

    @Override
    protected CustomerFormModel getModel() {
        return (CustomerFormModel) super.getModel();
    }

    @EventHandler
    public void setFirstName(String firstName) {
        getModel().getCustomer().setFirstName(firstName);
    }

    @EventHandler
    public void setLastName(String lastName) {
        getModel().getCustomer().setLastName(lastName);
    }

    @EventHandler
    public void setEmail(String email) {
        getModel().getCustomer().setEmail(email);
    }

    @EventHandler
    public void setStatus(String status) {
        getModel().getCustomer().setStatus(status);
    }

    @EventHandler
    public void setGender(String gender) {
        getModel().getCustomer().setGender(Gender.from(gender));
    }

    // @EventHandler
    // public void setBirthDate(String input) {
    // CustomerModel m = getModel();
    // m.setBirthDate(input);
    // }

}
