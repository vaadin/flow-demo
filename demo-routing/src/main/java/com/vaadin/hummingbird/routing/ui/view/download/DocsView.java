package com.vaadin.hummingbird.routing.ui.view.download;

import com.vaadin.hummingbird.routing.ui.view.AbstractView;

public class DocsView extends AbstractView {

    @Override
    public String getPath() {
        return "docs";
    }

    @Override
    public String getParentViewPath() {
        return "download";
    }

}
