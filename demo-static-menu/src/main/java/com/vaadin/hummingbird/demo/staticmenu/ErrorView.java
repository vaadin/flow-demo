package com.vaadin.hummingbird.demo.staticmenu;

import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;

public class ErrorView extends Div implements View {

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        getElement().appendChild(
                ElementFactory.createHeading1("404 - Page not found"),
                ElementFactory.createSpan("Please check the location "),
                ElementFactory.createEmphasis(
                        locationChangeEvent.getLocation().getPath()),
                ElementFactory.createSpan(" and try again."));
    }
}
