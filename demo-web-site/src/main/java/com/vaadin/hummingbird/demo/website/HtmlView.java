package com.vaadin.hummingbird.demo.website;

import java.io.IOException;
import java.io.InputStream;

import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Html;

public class HtmlView extends Div implements View {
    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String path = locationChangeEvent.getPathWildcard();
        if (path.isEmpty()) {
            path = "index.html";
        }

        try (InputStream resourceAsStream = getClass().getClassLoader()
                .getResourceAsStream("html/" + path)) {
            removeAll();
            add(new Html(resourceAsStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
