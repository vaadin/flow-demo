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
import java.util.function.Consumer;

import com.vaadin.annotations.EventHandler;
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
import com.vaadin.hummingbird.template.ElementTemplateNode;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.server.Command;
import com.vaadin.ui.HasElement;
import com.vaadin.ui.Template;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
public class ContactForm extends Template {

    @Id("firstName")
    private Input firstName;
    @Id("lastName")
    private Input lastName;
    @Id("phoneNumber")
    private Input phoneNumber;
    @Id("email")
    private Input email;

    private Consumer<Contact> onSave;
    private Command onCancel;
    private Integer editId;

    public interface ContactBean extends TemplateModel {
        @Exclude({ "id", "birthDate" })
        public void setContact(Contact contact);

        public Contact getContact();
    }

    public ContactForm(Integer id, Consumer<Contact> onSave, Command onCancel) {
        // Uglyness to avoid needing an event bus only for this
        this.onSave = onSave;
        this.onCancel = onCancel;
        editId = id;
        Optional<Contact> contact = ContactService.getDemoService()
                .findById(id);
        if (contact.isPresent()) {
            getModel().setContact(contact.get());
        }
    }

    @Override
    protected final ContactBean getModel() {
        return (ContactBean) super.getModel();
    }

    @EventHandler
    protected void onSave() {
        Contact contact = new Contact();
        contact.setId(editId);
        contact.setFirstName(getValue(firstName));
        contact.setLastName(getValue(lastName));
        contact.setPhoneNumber(getValue(phoneNumber));
        contact.setEmail(getValue(email));
        onSave.accept(ContactService.getDemoService().save(contact));
    }

    private String getValue(HasElement hasElement) {
        StateNode node = getOverrideNode(hasElement.getElement());
        if (BasicElementStateProvider.get().hasProperty(node, "value")) {
            return (String) BasicElementStateProvider.get().getProperty(node,
                    "value");
        } else {
            return hasElement.getElement().getProperty("value");
        }
    }

    private StateNode getOverrideNode(Element element) {
        ElementTemplateNode templateNode = ((TemplateElementStateProvider) element
                .getStateProvider()).getTemplateNode();
        StateNode node = element.getNode();
        return node.getFeature(TemplateOverridesMap.class).get(templateNode,
                false);
    }

    @EventHandler
    protected void onCancel() {
        onCancel.execute();
    }

}
