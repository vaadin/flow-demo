package com.vaadin.hummingbird.routing.ui.view.framework;

import com.vaadin.hummingbird.routing.ui.view.AbstractView;

public class DirectoryView extends AbstractView {

    @Override
    public String getPath() {
        return "directory";
    }

    @Override
    public String getParentViewPath() {
        return "framework";
    }

}
