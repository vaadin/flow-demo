package com.vaadin.hummingbird.bootstrap.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

public class Container extends CssLayout {
    {
        getElement().addClass("container");
    }

    public Container() {
        super();
    }

    public Container(Component... children) {
        super(children);
    }

}
