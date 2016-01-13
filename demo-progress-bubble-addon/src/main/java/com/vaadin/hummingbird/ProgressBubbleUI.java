package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.addon.ProgressBubble;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.PaperButton;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Viewport("user-scalable=no,initial-scale=1.0")
public class ProgressBubbleUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout vl = new VerticalLayout();

        ProgressBubble pb = new ProgressBubble();
        pb.setValue(0);
        pb.setMax(100);

        Slider s = new Slider(0, 100, 1);
        s.setPin(true);
        s.setStep(5);
        s.addValueChangeListener(e -> {
            pb.setValue(s.getValue().intValue());
        });

        PaperButton b4 = new PaperButton("Progress!");
        b4.addClickListener(e -> {
            double newValue = s.getValue() + 5;
            if (newValue > s.getMax()) {
                newValue -= s.getMax();
            }

            s.setValue(newValue);
        });

        vl.addComponents(pb, s, b4);
        setContent(vl);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ProgressBubbleUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
