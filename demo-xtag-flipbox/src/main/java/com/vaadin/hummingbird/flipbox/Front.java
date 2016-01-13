package com.vaadin.hummingbird.flipbox;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.ui.Template;

@Bower("paper-button")
public class Front extends Template {

    @TemplateEventHandler
    public void flip() {
        ((FlipBox) getParent()).toggle();
    }
}
