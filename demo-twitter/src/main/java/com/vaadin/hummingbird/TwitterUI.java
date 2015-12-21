package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.twitter.TwitterSearch;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Viewport("user-scalable=no,initial-scale=1.0")
public class TwitterUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        int initial = 100;
        int loadSize = 10;
        if (vaadinRequest.getParameter("initial") != null) {
            initial = Integer.parseInt(vaadinRequest.getParameter("initial"));
        }
        if (vaadinRequest.getParameter("loadSize") != null) {
            loadSize = Integer.parseInt(vaadinRequest.getParameter("loadSize"));
        }
        addComponent(new TwitterSearch(initial, loadSize));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TwitterUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
