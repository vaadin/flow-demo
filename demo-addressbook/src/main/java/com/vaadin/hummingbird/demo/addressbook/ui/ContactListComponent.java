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

import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.nodefeature.ModelList;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Template;

/**
 * Displays a list of contacts.
 */
public class ContactListComponent extends Template implements View {

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        writeContactsToModel();
    }

    private void writeContactsToModel() {
        ModelList contacts = getContactsList();

        for (Contact c : ContactService.getDemoService().findAll(null)) {
            StateNode node = new StateNode(ModelMap.class);
            ModelMap modelMap = node.getFeature(ModelMap.class);
            modelMap.setValue("firstName", c.getFirstName());
            modelMap.setValue("lastName", c.getLastName());
            modelMap.setValue("email", c.getEmail());
            contacts.add(node);
        }
    }

    private ModelList getContactsList() {
        ModelMap modelMap = getElement().getNode().getFeature(ModelMap.class);
        ModelList contacts;
        if (modelMap.hasValue("contacts")) {
            contacts = ((StateNode) modelMap.getValue("contacts"))
                    .getFeature(ModelList.class);
        } else {
            StateNode stateNode = new StateNode(ModelList.class);
            modelMap.setValue("contacts", stateNode);
            contacts = stateNode.getFeature(ModelList.class);
        }
        return contacts;
    }

}
