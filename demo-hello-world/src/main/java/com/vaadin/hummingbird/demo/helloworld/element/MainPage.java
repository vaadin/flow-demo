package com.vaadin.hummingbird.demo.helloworld.element;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.ui.Component;
import com.vaadin.ui.Template;

public class MainPage extends Template {

    public MainPage() {
        super(new ByteArrayInputStream(
                "<div><div id='header'></div><div id='content'></div><hr><div id='footer'><a href='mailto:someone@example.com?Subject=Hello%20again' target='_top'>Send Mail</a></div></div>"
                        .getBytes(StandardCharsets.UTF_8)));
    }

    public void setContent(Component content) {
        Element contentContainer = getElement().getChildren()
                .filter(this::isContent).findFirst().get();
        contentContainer.removeAllChildren();
        contentContainer.appendChild(content.getElement());
    }

    private boolean isContent(Element element) {
        if (!element.isTextNode()) {
            return "content".equals(element.getAttribute("id"));
        }
        return false;
    }
}