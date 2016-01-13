package com.vaadin.hummingbird.bootstrap.components;

import com.vaadin.annotations.Tag;
import com.vaadin.ui.CssLayout;

@Tag("p")
public class Paragraph extends CssLayout {

    public Paragraph() {
        // Nothing to do
    }

    public Paragraph(String text) {
        getElement().setTextContent(text);
    }

}
