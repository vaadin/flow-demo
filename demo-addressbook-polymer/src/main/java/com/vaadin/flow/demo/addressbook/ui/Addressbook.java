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

import java.util.Optional;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.annotations.Title;
import com.vaadin.flow.demo.addressbook.backend.Contact;
import com.vaadin.flow.demo.addressbook.backend.ContactService;
import com.vaadin.flow.html.HtmlContainer;
import com.vaadin.flow.router.View;

/**
 * Displays a list of contacts.
 */
@Title("Addressbook")
@Tag("div")
@StyleSheet("context://css/site.css")
public class Addressbook extends HtmlContainer implements View {
    private final ContactForm contactForm = new ContactForm(this::updateForm);
    private final ContactsTable contactsTable = new ContactsTable(
            this::tableRowClick);

    /**
     * Creates the Addressbook view.
     */
    public Addressbook() {
        setClassName("main");
        add(contactsTable);
    }

    private void updateForm(Boolean dataUpdated) {
        if (dataUpdated) {
            contactsTable.updateTableContents();
        }
        removeContactForm();
    }

    private void tableRowClick(String selectedRowId) {
        Optional<Contact> selectedObject = Optional.ofNullable(selectedRowId)
                .map(Integer::parseInt).map(id -> ContactService
                        .getDemoService().findById(id).orElse(null));

        if (selectedObject.isPresent()) {
            add(contactForm);
        } else {
            removeContactForm();
        }

        contactForm.updateContent(selectedObject.orElse(new Contact()));
    }

    private void removeContactForm() {
        if (getChildren().anyMatch(contactForm::equals)) {
            remove(contactForm);
        }
    }
}
