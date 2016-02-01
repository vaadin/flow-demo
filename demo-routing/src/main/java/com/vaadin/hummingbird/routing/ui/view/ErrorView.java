package com.vaadin.hummingbird.routing.ui.view;

import com.vaadin.hummingbird.routing.router.Route;
import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

import elemental.json.JsonValue;

@Route(path = "error")
public class ErrorView extends CssLayout implements View {

    @Override
    public void open(JsonValue state, String path) {
        removeAllComponents();
        addComponent(new HTML("404: No view for path:" + path));
    }

}
