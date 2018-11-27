/*
 * Copyright 2000-2018 Vaadin Ltd.
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
package com.vaadin.flow.demo.dynamic;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.router.ParentLayout;

/**
 * Dynamic view that is setup manually.
 * No @Route help or other automation.
 */
@ParentLayout(MainLayout.class)
public class VersionView extends Div {

    public VersionView() {
        Span text = new Span("Version view");
        Button back = new Button("Return", event -> UI.getCurrent().navigate(""));

        add(text, back);
    }
}
