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
import com.vaadin.router.Route;
import com.vaadin.router.WildcardParameter;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.i18n.I18NProvider;

/**
 * The Docs view.
 *
 * @author Vaadin Ltd
 */
@Route(value = "docs", layout = DownloadMenuView.class, absolute = true)
public class DocsView extends DummyView implements HasUrlParameter<String> {
    Div div = new Div();

    /**
     * Constructor for the docs view.
     */
    public DocsView() {
        add(div);
    }

    @Override
    public void setParameter(BeforeNavigationEvent event,
            @WildcardParameter String parameter) {
        if (!parameter.isEmpty()) {
            I18NProvider provider = VaadinService.getCurrent().getInstantiator()
                    .getI18NProvider();
            div.setText(
                    provider.getTranslation("docs.viewing.page", parameter));
        } else {
            div.setText("");
        }
    }
}
