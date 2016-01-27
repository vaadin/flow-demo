package com.vaadin.hummingbird.routing.ui.view.framework;

import com.vaadin.hummingbird.routing.ui.view.AbstractView;

public class DemoView extends AbstractView {

    @Override
    public String getPath() {
        return "demo";
    }

    @Override
    public String getAliases() {
        return "demo";
    }

    @Override
    public String getParentViewPath() {
        return "framework";
    }

}
