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
package com.vaadin.hummingbird.demo.simplecrm;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.ui.Template;

@HtmlImport("context://bower_components/iron-icons/social-icons.html")
@HtmlImport("context://bower_components/iron-icons/maps-icons.html")
@HtmlImport("context://bower_components/progress-bubble/progress-bubble.html")
@StyleSheet("http://fonts.googleapis.com/css?family=Open+Sans:400,300,700")
@HtmlImport("context://bower_components/paper-button/paper-button.html")
@HtmlImport("context://bower_components/paper-dropdown-menu/paper-dropdown-menu.html")
@HtmlImport("context://bower_components/paper-drawer-panel/paper-drawer-panel.html")
@HtmlImport("context://bower_components/paper-header-panel/paper-header-panel.html")
@HtmlImport("context://bower_components/paper-input/paper-input.html")
@HtmlImport("context://bower_components/paper-icon-button/paper-icon-button.html")
@HtmlImport("context://bower_components/paper-item/paper-item.html")
@HtmlImport("context://bower_components/paper-menu/paper-menu.html")
@HtmlImport("context://bower_components/paper-radio-button/paper-radio-button.html")
@HtmlImport("context://bower_components/paper-radio-group/paper-radio-group.html")
@HtmlImport("context://bower_components/paper-toolbar/paper-toolbar.html")
@HtmlImport("context://bower_components/iron-flex-layout/iron-flex-layout.html")
@HtmlImport("context://bower_components/iron-form/iron-form.html")
@HtmlImport("context://bower_components/iron-icons/iron-icons.html")
@HtmlImport("context://bower_components/iron-icon/iron-icon.html")
public class SimpleCrmMain extends Template {

    @Id("simplecrm-menu")
    Element menuElement;

    @Id("main")
    Element mainElement;

    public SimpleCrmMain() {
        SimpleCrmMenu menuTemplate = new SimpleCrmMenu();
        menuElement.appendChild(menuTemplate.getElement());

    }

    protected void showTemplate(Template t) {
        mainElement.removeAllChildren();
        mainElement.appendChild(t.getElement());
    }

}
