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
package com.vaadin.flow.demo.website;

import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.router.RouterLink;
import com.vaadin.ui.html.Div;

/**
 * A dynamic view that shows different content based on a parameter in the URL.
 *
 * @since
 * @author Vaadin Ltd
 */
public final class ParameterView extends SimpleView {

    private Div idElement;
    private RouterLink linkToNext;

    /**
     * Creates a new dynamic view.
     */
    public ParameterView() {
        add(getMappingInfo(SiteRouterConfigurator.MAPPING_PARAM));
        Div content = new Div();
        content.setClassName("content");

        idElement = new Div();
        linkToNext = new RouterLink("Open the same view with another parameter",
                ParameterView.class, Integer.toString(1));

        content.add(idElement, linkToNext);
        add(content);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String stringId = locationChangeEvent.getPathParameter("id");
        int id;
        try {
            id = Integer.parseInt(stringId);
            idElement.setText("Id parameter: " + id);
            linkToNext.setRoute(ParameterView.class, Integer.toString(id + 1));
        } catch (NumberFormatException e) {
            idElement.setText("Id parameter: " + "Invalid, must be an integer");
            linkToNext.setRoute(ParameterView.class, Integer.toString(1));
        }
    }

}
