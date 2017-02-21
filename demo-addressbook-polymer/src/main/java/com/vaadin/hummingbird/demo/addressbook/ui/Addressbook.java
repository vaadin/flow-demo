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

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.annotations.Title;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.html.HtmlContainer;
import com.vaadin.hummingbird.router.View;

import elemental.json.Json;
import elemental.json.JsonObject;

/**
 * Displays a list of contacts.
 */
@Title("Addressbook")
@Tag("div")
@StyleSheet("css/site.css")
@HtmlImport("js/bower_components/polymer/polymer.html")
@HtmlImport("components/ContactsTable.html")
@HtmlImport("components/ContactForm.html")
public class Addressbook extends HtmlContainer implements View {
    private final ContactForm contactForm = new ContactForm(this::updateForm);
    private final ContactsTable contactsTable = new ContactsTable(
            this::tableRowClick);

    private boolean contactFormRemoved = true;

    private void updateForm(Boolean dataUpdated) {
        if (dataUpdated) {
            contactsTable.updateTableContents();
        }

        if (!contactFormRemoved) {
            remove(contactForm);
            contactFormRemoved = true;
        }
    }

    public Addressbook() {
        setClassName("main");
        add(contactsTable);
    }

    private void tableRowClick(String selectedRowId) {
        Optional<JsonObject> selectedObject = Optional.ofNullable(selectedRowId)
                .map(Integer::parseInt).map(id -> ContactService
                        .getDemoService().findById(id).orElse(null))
                .map(Contact::toJsonObject);

        if (selectedObject.isPresent()) {
            add(contactForm);
            contactFormRemoved = false;
        } else if (!contactFormRemoved) {
            remove(contactForm);
            contactFormRemoved = true;
        }

        contactForm.updateContent(selectedObject.orElse(Json.createObject()));
    }
}
