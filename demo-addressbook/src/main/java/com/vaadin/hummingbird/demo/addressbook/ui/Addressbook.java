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
import java.util.Optional;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.Exclude;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.nodefeature.TemplateMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.server.Command;
import com.vaadin.ui.AngularTemplate;

/**
 * Displays a list of contacts.
 *
 * TODO update id in url based on table selection
 */
@Title("Addressbook")
@StyleSheet("css/site.css")
public class Addressbook extends AngularTemplate implements View {

    public interface AddressbookModel extends TemplateModel {
        void setFormHidden(boolean formHidden);

        boolean isFormHidden();

        @Exclude("birthDate")
        void setContacts(List<Contact> contacts);

        List<Contact> getContacts();
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
    protected void onRowSelect(Integer id) {
        if (id == null) {
            hideForm();
        } else {
            Optional<Contact> toEdit = ContactService.getDemoService()
                    .findById(id);
            if (!toEdit.isPresent()) {
                // If the client sends a non existing id, ignore it
                return;
            }
            SerializableConsumer<Contact> onSave = contact -> {
                // Update model with the new contact
                List<Contact> modelContacts = getModel().getContacts();
                for (int i = 0; i < modelContacts.size(); i++) {
                    if (modelContacts.get(i).getId() == id) {
                        // TODO Remove this once
                        // https://github.com/vaadin/hummingbird/issues/968 is
                        // implemented
                        contact.setBirthDate(null);
                        modelContacts.set(i, contact);
                        break;
                    }
                }
                hideForm();
            };
            Command onCancel = this::hideForm;
            ContactForm contactForm = new ContactForm(toEdit.get(), onSave,
                    onCancel);
            showForm(contactForm);
        }

    }

    private void showForm(ContactForm contactForm) {
        TemplateMap feature = getElement().getNode()
                .getFeature(TemplateMap.class);
        feature.setChild(contactForm.getElement().getNode());
    }

    private void hideForm() {
        TemplateMap feature = getElement().getNode()
                .getFeature(TemplateMap.class);
        feature.setChild(null);
    }

    private void writeContactsToModel() {
        List<Contact> allContacts = ContactService.getDemoService().findAll("");

        // TODO Store highlighted class in model instead of using JS logic
        getModel().setContacts(allContacts);
    }
}
