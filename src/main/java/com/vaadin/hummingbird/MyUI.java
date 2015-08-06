package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PaperButton;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Style;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout vl = new VerticalLayout();
        Button b1 = new Button("Say hello using a <button>");
        PaperButton b2 = new PaperButton("Say foo to the paper-button");

        Label l = new Label("A text does not need any div but will not be laid out correctly using flex then");
        Slider s = new Slider();

        Button b3 = new Button("Say bar");
        PaperButton b4 = new PaperButton("Say baz");
        Style.add(b4.getElement(), "color", "black");
        Style.add(b4.getElement(), "background-color", "green");
        Button b5 = new Button("Say Vaadin");
        b1.addClickListener((e) -> {
            vl.addComponent(new Label("Hello from the server"));
        });

        final HorizontalLayout hl = new HorizontalLayout(b3, b4, b5);
        vl.addComponent(b2);
        vl.addComponent(l);
        vl.addComponent(new Label("Paper slider, just because"));
        vl.addComponent(s);
        vl.addComponent(hl);
        vl.addComponentAsFirst(b1);
        setContent(vl);

        vl.setSizeFull();
        vl.setExpandRatio(b1, 1);
        vl.setExpandRatio(b2, 2);

        hl.setExpandRatio(b3, 0);
        hl.setExpandRatio(b4, 1);
        hl.setExpandRatio(b5, 2);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
