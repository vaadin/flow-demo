package com.vaadin.hummingbird.bootstrap.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

public class Jumbotron extends CssLayout {
    {
        getElement().addClass("jumbotron");
    }

    public Jumbotron() {
        super();
    }

    public Jumbotron(Component... components) {
        super(components);
    }
}
