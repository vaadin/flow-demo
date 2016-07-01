package com.vaadin.hummingbird.demo.simplecrm;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.router.InternalRedirectHandler;
import com.vaadin.hummingbird.router.Location;
import com.vaadin.hummingbird.router.RouterConfiguration;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@StyleSheet("/styles.css")
@Title("Hummingbird Simple CRM")
public class CrmUI extends UI {
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, routerConfigurator = CrmUI.RouterConfigurator.class)
    public static class MyUIServlet extends VaadinServlet {
    }

    public static class RouterConfigurator
            implements com.vaadin.hummingbird.router.RouterConfigurator {

        @Override
        public void configure(RouterConfiguration configuration) {
            configuration.setRoute("customers", Customers.class);
            configuration.setRoute("about", About.class);
            configuration.setRoute("analyze", Analyze.class);
            configuration.setRoute("map", Map.class);
            configuration.setRoute("",
                    new InternalRedirectHandler(new Location("customers")));
        }

    }

}