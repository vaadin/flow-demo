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
package com.vaadin.hummingbird;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.hummingbird.CrmUI.MyView;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Template;

@HTML({ "vaadin://bower_components/iron-icons/social-icons.html",
        "vaadin://bower_components/iron-icons/maps-icons.html",
        "vaadin://bower_components/progress-bubble/progress-bubble.html" })
@StyleSheet("http://fonts.googleapis.com/css?family=Open+Sans:400,300,700")
@Bower({ "paper-button", "paper-dropdown-menu", "paper-drawer-panel",
        "paper-header-panel", "paper-input", "paper-icon-button", "paper-item",
        "paper-menu", "paper-radio-button", "paper-radio-group",
        "paper-toolbar", "iron-flex-layout", "iron-form", "iron-icons",
        "iron-icon" })
public class SimpleCrmMain extends Template {

    private CustomerData customerData;

    public SimpleCrmMain(CustomerData customerData) {
        this.customerData = customerData;
    }

    @Override
    public void attach() {
        super.attach();
        Element menuElement = this.getElementById("simplecrm-menu");
        SimpleCrmMenu menuTemplate = new SimpleCrmMenu();
        menuElement.appendChild(menuTemplate.getElement());
        Element mainElement = this.getElementById("main");
        // default main template: Customers
        // getUI().getNavigator().navigateTo("Customers");

    }

    protected void showTemplate(Template t) {
        Element mainElement = this.getElementById("main");
        mainElement.removeAllChildren();
        mainElement.appendChild(t.getElement());
    }

    protected CustomerData getCustomerData() {
        return customerData;
    }

}
