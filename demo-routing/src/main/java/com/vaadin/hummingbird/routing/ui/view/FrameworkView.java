package com.vaadin.hummingbird.routing.ui.view;

public class FrameworkView extends AbstractSubMenuView {

    @Override
    public String getPath() {
        return "framework";
    }

    @Override
    public String[][] getSubMenuLinks() {
        return new String[][] { { "Demo", "/demo" },
                { "Tutorial", "/tutorial" }, { "Docs", "/docs/framework" },
                { "Add-ons", "/directory" } };
    }

}
