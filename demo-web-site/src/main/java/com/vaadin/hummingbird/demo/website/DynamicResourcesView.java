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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResourceRegistration;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

/**
 * A view that uses generated resources.
 *
 * @since
 * @author Vaadin Ltd
 */
public class DynamicResourcesView extends SimpleView {

    private Element name;
    private Element image;

    /**
     * Creates a new view.
     */
    public DynamicResourcesView() {
        super(ElementFactory.createDiv());
        getElement().appendChild(
                getMappingInfo(SiteRouterConfigurator.MAPPING_DYN_RESOURCE));

        initContent();
    }

    private void initContent() {
        Element label = ElementFactory
                .createDiv("Type a string to generate an image");
        name = ElementFactory.createInput("text");
        name.setSynchronizedProperties("value")
                .setSynchronizedPropertiesEvents("change");
        name.getStyle().set("display", "block");

        getElement().appendChild(label, name);
        StreamResourceRegistration registration = createResource();
        createGenerateImageButton(registration);
        createImageOpener(registration);
    }

    private void createGenerateImageButton(
            StreamResourceRegistration registration) {
        Element button = ElementFactory.createButton("Generate Image");

        button.addEventListener("click", event -> generateImage(
                registration.getResourceUri().toString()));

        getElement().appendChild(button);
    }

    private void createImageOpener(StreamResourceRegistration registration) {
        Element button = ElementFactory.createButton("Open Image");

        getElement().appendChild(button);

        UI.getCurrent().getPage().executeJavaScript(
                "$0.onclick=function(){window.open($1);}", button,
                registration.getResourceUri().toString());

    }

    private void generateImage(String url) {
        if (image != null) {
            getElement().removeChild(image);
        }
        image = new Element("object");
        image.setAttribute("type", "image/svg+xml");
        image.getStyle().set("display", "block");
        image.setAttribute("data", url);

        getElement().appendChild(image);
    }

    private StreamResourceRegistration createResource() {
        StreamResource resource = new StreamResource("image",
                this::getImageInputStream);
        resource.setContentType("image/svg+xml");
        return VaadinSession.getCurrent().getResourceRegistry()
                .registerResource(resource);
    }

    private InputStream getImageInputStream() {
        String svg = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>"
                + "<svg  xmlns='http://www.w3.org/2000/svg' "
                + "xmlns:xlink='http://www.w3.org/1999/xlink'>"
                + "<rect x='10' y='10' height='100' width='100' "
                + "style=' fill: #90C3D4'/>" + "<text x='30' y='30' fill='red'>"
                + name.getProperty("value") + "</text>" + "</svg>";
        return new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8));
    }
}
