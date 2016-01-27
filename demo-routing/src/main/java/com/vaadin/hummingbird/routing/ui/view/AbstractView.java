package com.vaadin.hummingbird.routing.ui.view;

import java.util.logging.Logger;

import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;
import com.vaadin.ui.HasElement;

import elemental.json.JsonValue;

public abstract class AbstractView extends CssLayout implements View {

    @Override
    public void open(JsonValue state, String path) {
        removeAllComponents();
        addComponent(new HTML("<div>" + getPath() + "</div>"));
    }

    @Override
    public void show(View subView) {
        removeAllComponents();
        addComponent(new HTML(
                "<div>" + getPath() + " > " + subView.getPath() + "</div>"));
        if (subView instanceof HasElement) {
            getElement().appendChild(((HasElement) subView).getElement());
        }
    }

    @Override
    public boolean remove() {
        Logger.getLogger(getClass().getName()).info("REMOVING THIS VIEW");
        return true;
    }

    @Override
    public boolean remove(View subView) {
        Logger.getLogger(getClass().getName())
                .info("REMOVING SUB VIEW: " + subView.getClass().getName());
        return true;
    }
}
