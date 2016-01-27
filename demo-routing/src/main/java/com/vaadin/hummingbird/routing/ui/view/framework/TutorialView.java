package com.vaadin.hummingbird.routing.ui.view.framework;

import com.vaadin.hummingbird.routing.ui.view.AbstractView;

public class TutorialView extends AbstractView {

    @Override
    public String getPath() {
        return "tutorial";
    }

    @Override
    public String getParentViewPath() {
        return "framework";
    }

}
