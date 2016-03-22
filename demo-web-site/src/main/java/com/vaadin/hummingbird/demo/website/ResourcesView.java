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

import java.io.InputStream;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.server.VaadinServletService;

/**
 * A view that shows different resources based on the URL.
 *
 * @since
 * @author Vaadin Ltd
 */
public class ResourcesView extends SimpleView {

    private Element wrapper;
    private Element iframe;

    /**
     * Creates a new view.
     */
    public ResourcesView() {
        super(ElementFactory.createDiv());
        getElement().appendChild(
                getMappingInfo(SiteRouterConfigurator.MAPPING_RESOURCE));

        iframe = new Element("iframe");
        iframe.getStyle().set("width", "50%");
        iframe.getStyle().set("height", "400px");
        wrapper = ElementFactory.createDiv();

        Element links = new Element("ul");
        links.appendChild(createLink("", "<none>"));
        links.appendChild(createLink("css/site.css"));
        links.appendChild(createLink("images/vaadin-logo-small.png"));

        Element header = ElementFactory.createParagraph()
                .appendChild(ElementFactory.createStrong("Selected resource:"));
        wrapper.appendChild(
                ElementFactory.createSpan("Select the resource to display"),
                links, header, iframe);
        getElement().appendChild(wrapper);
    }

    private Element createLink(String resource) {
        return createLink(resource, resource);
    }

    private Element createLink(String resource, String caption) {
        Element link = ElementFactory.createRouterLink("resource/" + resource,
                caption);
        return new Element("li").appendChild(link);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String resourcePath = "/" + locationChangeEvent.getPathWildcard();
        if (resourceExists(resourcePath)) {
            iframe.setAttribute("src", resourcePath);
            wrapper.setChild(3, iframe);
        } else if ("/".equals(resourcePath)) {
            wrapper.setChild(3,
                    ElementFactory.createDiv("No resource selected"));
        } else {
            wrapper.setChild(3, ElementFactory
                    .createDiv("Resource " + resourcePath + " not available"));
        }

    }

    private boolean resourceExists(String resourcePath) {
        InputStream stream = VaadinServletService.getCurrentServletRequest()
                .getServletContext().getResourceAsStream(resourcePath);
        return stream != null;
    }

}
