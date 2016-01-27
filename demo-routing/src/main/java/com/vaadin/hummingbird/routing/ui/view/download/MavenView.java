package com.vaadin.hummingbird.routing.ui.view.download;

import com.vaadin.hummingbird.routing.ui.view.AbstractView;

public class MavenView extends AbstractView {

    @Override
    public String getPath() {
        return "maven";
    }

    @Override
    public String getParentViewPath() {
        return "download";
    }

}
