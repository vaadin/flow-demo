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

import com.vaadin.annotations.ClientDelegate;
import com.vaadin.annotations.Exclude;
import com.vaadin.annotations.Id;
import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.impl.BasicElementStateProvider;
import com.vaadin.hummingbird.dom.impl.TemplateElementStateProvider;
import com.vaadin.hummingbird.html.Input;
import com.vaadin.hummingbird.nodefeature.TemplateOverridesMap;
import com.vaadin.hummingbird.template.angular.ElementTemplateNode;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.server.Command;
import com.vaadin.ui.AngularTemplate;
import com.vaadin.ui.HasElement;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
public class ContactForm extends AngularTemplate {

    @Id("firstName")
    private Input firstName;
    @Id("lastName")
    private Input lastName;
    @Id("phoneNumber")
    private Input phoneNumber;
    @Id("email")
    private Input email;

    private SerializableConsumer<Contact> onSave;
    private Command onCancel;
    private Contact editContact;

    public interface ContactBean extends TemplateModel {
        @Exclude({ "id", "birthDate" })
        void setContact(Contact contact);

        Contact getContact();
    }

    /**
     * Creates a new form for editing the given contact.
     *
     * @param contact
     *            the contact to edit
     * @param onSave
     *            the callback to invoke when the contact is saved
     * @param onCancel
     *            the callback to invoke when cancel is pressed
     */
    public ContactForm(Contact contact, SerializableConsumer<Contact> onSave,
            Command onCancel) {
        // Uglyness to avoid needing an event bus only for this
        this.onSave = onSave;
        this.onCancel = onCancel;
        editContact = contact;
        getModel().setContact(contact);
    }

    @Override
    protected final ContactBean getModel() {
        return (ContactBean) super.getModel();
    }

    @ClientDelegate
    protected void onSave() {
        Contact contact = new Contact();
        // TODO Use getModel().update(editContact,"contact") once
        // https://github.com/vaadin/hummingbird/issues/732 is implemented
        contact.setId(editContact.getId());
        contact.setBirthDate(editContact.getBirthDate());

        // TODO Update model in template once
        // https://github.com/vaadin/hummingbird/issues/612 is implemented
        contact.setFirstName(getValue(firstName));
        contact.setLastName(getValue(lastName));
        contact.setPhoneNumber(getValue(phoneNumber));
        contact.setEmail(getValue(email));

        onSave.accept(ContactService.getDemoService().save(contact));
    }

    private static String getValue(HasElement hasElement) {
        StateNode node = getOverrideNode(hasElement.getElement());
        String valueProperty = "value";
        if (BasicElementStateProvider.get().hasProperty(node, valueProperty)) {
            return (String) BasicElementStateProvider.get().getProperty(node,
                    valueProperty);
        } else {
            return hasElement.getElement().getProperty(valueProperty);
        }
    }

    private static StateNode getOverrideNode(Element element) {
        ElementTemplateNode templateNode = ((TemplateElementStateProvider) element
                .getStateProvider()).getTemplateNode();
        StateNode node = element.getNode();
        return node.getFeature(TemplateOverridesMap.class).get(templateNode,
                false);
    }

    @ClientDelegate
    protected void onCancel() {
        onCancel.execute();
    }

}
