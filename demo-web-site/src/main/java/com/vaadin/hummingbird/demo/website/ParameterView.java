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
package com.vaadin.hummingbird.demo.website;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * A dynamic view that shows different content based on a parameter in the URL.
 *
 * @since
 * @author Vaadin Ltd
 */
public class ParameterView extends SimpleView {

    private Element idElement;
    private Element linkToNext;

    /**
     * Creates a new dynamic view.
     */
    public ParameterView() {
        super(ElementFactory.createDiv());
        getElement().appendChild(
                getMappingInfo(SiteRouterConfigurator.MAPPING_PARAM));
        idElement = ElementFactory.createDiv();

        linkToNext = ElementFactory.createRouterLink("param/",
                "Open same view with another parameter");
        getElement().appendChild(idElement, linkToNext);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String stringId = locationChangeEvent.getPathParameter("id");
        int id;
        try {
            id = Integer.parseInt(stringId);
            idElement.setTextContent("Id parameter: " + id);
            linkToNext.setAttribute("href", "param/" + (id + 1));
        } catch (NumberFormatException e) {
            idElement.setTextContent(
                    "Id parameter: " + "Invalid, must be an integer");
            linkToNext.setAttribute("href", "param/1");
        }
    }

}
