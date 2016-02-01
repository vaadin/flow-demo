package com.vaadin.hummingbird.routing.ui.view;

import java.util.logging.Logger;

import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

import elemental.json.JsonValue;

public abstract class AbstractView extends CssLayout implements View {

    @Override
    public void open(JsonValue state, String path) {
        Logger.getLogger(getClass().getName())
                .info(getClass().getSimpleName() + ": OPENING THIS VIEW.");
        removeAllComponents();
        addComponent(new HTML(
                "<div>" + getClass().getSimpleName() + " " + path + "</div>"));
    }

    @Override
    public void show(View subView, String subViewPath) {
        getElement().appendChild(subView.getComponent().getElement());
    }

    @Override
    public boolean remove() {
        return true;
    }

    @Override
    public boolean remove(View subView, String subViewPath) {
        Logger.getLogger(getClass().getName()).info("REMOVING SUB VIEW "
                + subView.getClass().getName() + " with path:" + subViewPath);
        subView.getComponent().getElement().removeFromParent();
        return true;
    }
}
