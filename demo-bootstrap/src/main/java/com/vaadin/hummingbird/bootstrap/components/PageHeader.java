package com.vaadin.hummingbird.bootstrap.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

public class PageHeader extends CssLayout {
    {
        getElement().addClass("page-header");
    }

    public PageHeader() {
        super();
    }

    public PageHeader(Component... children) {
        super(children);
    }

    public PageHeader(String text) {
        this(new Heading(text));
    }
}
