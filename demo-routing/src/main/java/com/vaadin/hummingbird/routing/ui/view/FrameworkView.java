package com.vaadin.hummingbird.routing.ui.view;

import com.vaadin.hummingbird.routing.router.Route;

@Route(path = "framework")
public class FrameworkView extends AbstractSubMenuView {

    @Override
    public String[][] getSubMenuLinks() {
        return new String[][] { { "Demo", "/demo" },
                { "Tutorial", "/tutorial" }, { "Docs", "/docs/framework" },
                { "Add-ons", "/directory" } };
    }

}
