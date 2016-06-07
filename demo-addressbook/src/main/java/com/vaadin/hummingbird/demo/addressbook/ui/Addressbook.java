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

import java.util.List;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.nodefeature.ModelList;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.nodefeature.TemplateMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

/**
 * Displays a list of contacts.
 *
 * TODO update id in url based on table selection
 */
@Title("Addressbook")
@StyleSheet("css/site.css")
public class Addressbook extends Template implements View {

    private static final String CONTACTS_PROPERTY_NAME = "contacts";

    interface AddressbookModel extends TemplateModel {
        void setFormHidden(boolean formHidden);

        boolean isFormHidden();

        void setContacts(List<Contact> contacts);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        getModel().setFormHidden(true);
        writeContactsToModel();
    }

    @Override
    protected AddressbookModel getModel() {
        return (AddressbookModel) super.getModel();
    }

    @EventHandler
    protected void onRowSelect(String id) {
        TemplateMap feature = getElement().getNode()
                .getFeature(TemplateMap.class);
        if (id.isEmpty()) {
            feature.setChild(null);
        } else {
            ContactForm contactForm = new ContactForm(id,
                    () -> feature.setChild(null), () -> feature.setChild(null));
            feature.setChild(contactForm.getElement().getNode());
        }

    }

    private void writeContactsToModel() {
        List<Contact> allContacts = ContactService.getDemoService().findAll("");

        // TODO import as a list

        // TODO Store highlighted class in model instead of using JS logic
        ModelList contacts = getContactsList();

        for (Contact c : allContacts) {
            StateNode node = new StateNode(ModelMap.class);
            ModelMap modelMap = node.getFeature(ModelMap.class);
            modelMap.setValue("firstName", c.getFirstName());
            modelMap.setValue("lastName", c.getLastName());
            modelMap.setValue("email", c.getEmail());
            modelMap.setValue("id", c.getId().intValue());
            contacts.add(node);
        }
    }

    private ModelList getContactsList() {
        ModelMap modelMap = getElement().getNode().getFeature(ModelMap.class);
        ModelList contacts;
        if (modelMap.hasValue(CONTACTS_PROPERTY_NAME)) {
            contacts = ((StateNode) modelMap.getValue(CONTACTS_PROPERTY_NAME))
                    .getFeature(ModelList.class);
        } else {
            StateNode stateNode = new StateNode(ModelList.class);
            modelMap.setValue(CONTACTS_PROPERTY_NAME, stateNode);
            contacts = stateNode.getFeature(ModelList.class);
        }
        return contacts;
    }

}
