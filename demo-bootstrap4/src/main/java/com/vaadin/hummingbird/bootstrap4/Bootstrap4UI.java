package com.vaadin.hummingbird.bootstrap4;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.bootstrap4.VaadinEmployees.Employee;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Viewport("user-scalable=no,initial-scale=1.0")
public class Bootstrap4UI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Carousel carousel = new Carousel();
        carousel.setWidth("400px");
        carousel.setHeight("200px");
        for (Employee e : VaadinEmployees.employees) {
            carousel.addImage(e.getImageUrl(), e.getName(), e.getTitle());
        }
        addComponent(carousel);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Bootstrap4UI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
