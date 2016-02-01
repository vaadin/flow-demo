package com.vaadin.hummingbird.routing.ui.view;

import com.vaadin.hummingbird.routing.router.Route;

@Route(path = "download")
public class DownloadView extends AbstractSubMenuView {

    @Override
    public String[][] getSubMenuLinks() {
        return new String[][] { { "Docs", "/docs" },
                { "Add-ons", "/directory" }, { "Maven", "/maven" },
                { "Vaadin Icons", "/font-icons/about" } };
    }

}
