package com.vaadin.hummingbird.routing.ui.view;

public class DownloadView extends AbstractSubMenuView {

    @Override
    public String getPath() {
        return "download";
    }

    @Override
    public String[][] getSubMenuLinks() {
        return new String[][] { { "Docs", "/docs" },
                { "Add-ons", "/directory" }, { "Maven", "/maven" },
                { "Vaadin Icons", "/font-icons/about" } };
    }

}
