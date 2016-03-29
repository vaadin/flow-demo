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
package com.vaadin.hummingbird.demo.staticmenu.download;

import com.vaadin.annotations.Title;
import com.vaadin.hummingbird.demo.staticmenu.DummyView;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * The Docs sub view.
 *
 * @author Vaadin
 * @since
 */
@Title("Documentation")
public class DocsSubView extends DummyView {

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        getElement().appendChild(ElementFactory
                .createDiv("Viewing " + locationChangeEvent.getPathWildcard()));
    }

}
