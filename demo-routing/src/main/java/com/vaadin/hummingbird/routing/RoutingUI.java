package com.vaadin.hummingbird.routing;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.routing.router.History;
import com.vaadin.hummingbird.routing.router.Router;
import com.vaadin.hummingbird.routing.ui.MainView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

@Title("Routing")
public class RoutingUI extends UI {
    private CssLayout root;

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RoutingUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        History history = new History(this);

        history.pushState(1, "title 1", "?page=1");
        history.pushState("State 2", "title 2", "?page=2");
        history.pushState(null, "title 3", "#nullstate");
        Object[] o = new Object[2];
        o[0] = new String("Item 1");
        o[1] = 1;
        history.pushState(o, "title 4", "/foobar");
        history.pushState(5, "title 5", "?page=5");

        Router router = new Router(history);

        setContent(new MainView());
    }

}