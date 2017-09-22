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
package com.vaadin.flow.demo.staticmenu.download;

import com.vaadin.flow.demo.staticmenu.DummyView;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.OptionalParameter;
import com.vaadin.router.Route;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.ui.html.Div;

/**
 * The Docs view.
 *
 * @author Vaadin Ltd
 */
@Route(value = "docs", layout = DownloadMenuView.class, absolute = true)
public class DocsView extends DummyView implements HasUrlParameter<String> {
    Div div = new Div();

    /**
     * Constructor
     */
    public DocsView() {
        add(div);
    }

    @Override
    public void setParameter(BeforeNavigationEvent event,
            @OptionalParameter String parameter) {
        if (parameter != null) {
            div.setText("Viewing " + parameter);
        } else {
            div.setText("");
        }
    }
}
