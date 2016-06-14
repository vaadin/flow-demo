/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.addressbook.ui;

import java.util.Optional;

import com.vaadin.annotations.EventHandler;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.server.Command;
import com.vaadin.ui.Template;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
public class ContactForm extends Template {

    private Command onSave;
    private Command onCancel;

    interface ContactBean extends TemplateModel {
        public String getFirstName();

        public void setFirstName(String firstName);

        public String getLastName();

        public void setLastName(String lastName);

        public String getPhoneNumber();

        public void setPhoneNumber(String phoneNumber);

        public String getEmail();

        public void setEmail(String email);
    }

    public ContactForm(String id, Command onSave, Command onCancel) {
        // Uglyness to avoid needing an event bus only for this
        this.onSave = onSave;
        this.onCancel = onCancel;
        Optional<Contact> contact = ContactService.getDemoService()
                .findById(Long.parseLong(id));
        if (contact.isPresent()) {
            getModel().importBean("", contact.get(),
                    propertyName -> !"id".equals(propertyName)
                            && !"birthDate".equals(propertyName));
        }
    }

    @Override
    protected final ContactBean getModel() {
        return (ContactBean) super.getModel();
    }

    @EventHandler
    protected void onSave() {
        onSave.execute();
    }

    @EventHandler
    protected void onCaancel() {
        onCancel.execute();
    }

}
