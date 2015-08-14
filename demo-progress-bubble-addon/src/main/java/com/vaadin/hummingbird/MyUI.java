package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.addon.ProgressBubble;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.PaperButton;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout vl = new VerticalLayout();

        ProgressBubble pb = new ProgressBubble();
        pb.setValue(0);
        pb.setMax(100);

        PaperButton b4 = new PaperButton("Progress!");
        b4.addClickListener(e -> {
            if (pb.getValue() == pb.getMax()) {
                pb.setValue(0);
            }

            pb.setValue(pb.getValue() + 5);
        });

        vl.addComponents(pb, b4);
        setContent(vl);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
