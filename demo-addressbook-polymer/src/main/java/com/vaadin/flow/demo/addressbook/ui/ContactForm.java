/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.addressbook.ui;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.demo.addressbook.backend.Contact;
import com.vaadin.flow.demo.addressbook.backend.ContactService;
import com.vaadin.flow.demo.addressbook.ui.ContactForm.ContactModel;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
@Tag("contacts-form")
@HtmlImport("frontend://components/ContactForm.html")
class ContactForm extends PolymerTemplate<ContactModel> {

    /**
     * Contact model interface to bind properties to the template.
     */
    public interface ContactModel extends TemplateModel {

        void setFirstName(String firstName);

        String getFirstName();

        void setLastName(String lastName);

        String getLastName();

        void setPhoneNumber(String phone);

        String getPhoneNumber();

        void setEmail(String mail);

        String getEmail();
    }

    private final SerializableConsumer<Boolean> elementUpdateCallback;

    private Contact selectedContact;

    ContactForm(SerializableConsumer<Boolean> elementUpdateCallback) {
        this.elementUpdateCallback = elementUpdateCallback;
    }

    void updateContent(Contact contact) {
        selectedContact = contact;
        getModel().importBean("", contact, property -> true);
    }

    @EventHandler
    private void save() {
        selectedContact.setFirstName(getModel().getFirstName());
        selectedContact.setLastName(getModel().getLastName());
        selectedContact.setPhoneNumber(getModel().getPhoneNumber());
        selectedContact.setEmail(getModel().getEmail());
        ContactService.getDemoService().save(selectedContact);
        elementUpdateCallback.accept(true);
    }

    @EventHandler
    private void cancel() {
        elementUpdateCallback.accept(false);
    }

}
