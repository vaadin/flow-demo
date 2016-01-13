package com.vaadin.hummingbird.bootstrap.components;

import com.vaadin.annotations.Tag;
import com.vaadin.ui.CssLayout;

@Tag("h1")
public class Heading extends CssLayout {
    public Heading(String text) {
        getElement().setTextContent(text);
    }
}
