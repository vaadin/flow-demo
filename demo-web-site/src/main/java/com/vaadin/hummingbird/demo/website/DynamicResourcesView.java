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
import com.vaadin.hummingbird.html.Button;
import com.vaadin.hummingbird.html.Input;
import com.vaadin.server.StreamResource;

/**
 * A view that uses generated resources.
 *
 * @since
 * @author Vaadin Ltd
 */
public class DynamicResourcesView extends SimpleView {

    private Input name;
    private Element generatedImage;
    private Button generateImageButton;

    /**
     * Creates a new view.
     */
    public DynamicResourcesView() {
        super(ElementFactory.createDiv().setAttribute("class", "content"));
        getElement().appendChild(
                getMappingInfo(SiteRouterConfigurator.MAPPING_DYN_RESOURCE));

        initContent();
    }

    private void initContent() {
        Element label = ElementFactory
                .createDiv("Type a string to generate an image");
        name = new Input();
        name.setValue("text");

        // Generate button
        generateImageButton = new Button("Generate Image");
        generateImageButton
                .addClickListener(e -> generateImage(name.getValue()));

        // Open in new window button
        Button imageOpener = new Button("Open image in new tab/window");
        imageOpener.getElement().addAttachListener(e -> {
            // Delay until element is attached so we can find UI and Page
            imageOpener.getUI().get().getPage().executeJavaScript(
                    // Connect directly on the client side so we don't have a
                    // server visit
                    "$0.addEventListener('click',function(){window.open($1.getAttribute('data'))});",
                    imageOpener, generatedImage);
        });

        // Generated image
        generatedImage = new Element("object");
        generatedImage.setAttribute("type", "image/svg+xml");
        generatedImage.getStyle().set("display", "block");

        // Generate initial image
        generateImage(name.getValue());

        getElement().appendChild(label, name.getElement(),
                generateImageButton.getElement(), generatedImage,
                imageOpener.getElement());

    }

    private void generateImage(String text) {
        StreamResource resource = new StreamResource("image",
                () -> getImageInputStream(text));
        resource.setContentType("image/svg+xml");
        generatedImage.setAttribute("data", resource);
    }

    private static InputStream getImageInputStream(String text) {
        String svg = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>"
                + "<svg  xmlns='http://www.w3.org/2000/svg' "
                + "xmlns:xlink='http://www.w3.org/1999/xlink'>"
                + "<rect x='10' y='10' height='100' width='100' "
                + "style=' fill: #90C3D4'/>"
                + "<text x='20' y='30' fill='black'>" + text + "</text>"
                + "</svg>";
        return new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8));
    }
}
