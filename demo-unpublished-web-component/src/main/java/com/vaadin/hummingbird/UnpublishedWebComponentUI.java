package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.addon.MyProgressBar;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PaperButton;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UnpublishedWebComponentUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout vl = new VerticalLayout();

        Label l1 = new Label();
        Label l2 = new Label();
        Label l3 = new Label();
        // l1.setSizeUndefined();
        // l2.setSizeUndefined();
        // l3.setSizeUndefined();

        MyProgressBar pb1 = new MyProgressBar();
        pb1.setValue(0f);
        MyProgressBar pb2 = new MyProgressBar();
        pb2.setMin(40);
        pb2.setValue(40f);

        MyProgressBar pb3 = new MyProgressBar();
        pb3.setMin(40);
        pb3.setValue(40f);
        pb3.setMax(60);

        PaperButton b4 = new PaperButton("Progress!");
        b4.addClickListener(e -> {
            if (pb1.getValue() >= pb1.getMax()) {
                pb1.setValue((float) pb1.getMin());
            } else {
                pb1.setValue(pb1.getValue() + 5);
            }

            if (pb2.getValue() >= pb2.getMax()) {
                pb2.setValue((float) pb2.getMin());
            } else {
                pb2.setValue(pb2.getValue() + 5);
            }

            if (pb3.getValue() >= pb3.getMax()) {
                pb3.setValue((float) pb3.getMin());
            } else {
                pb3.setValue(pb3.getValue() + 5);
            }

            l1.setValue("Min: " + pb1.getMin() + ", max: " + pb1.getMax()
                    + ", value: " + pb1.getValue().intValue());
            l2.setValue("Min: " + pb2.getMin() + ", max: " + pb2.getMax()
                    + ", value: " + pb2.getValue().intValue());
            l3.setValue("Min: " + pb3.getMin() + ", max: " + pb3.getMax()
                    + ", value: " + pb3.getValue().intValue());
        });

        l1.setValue("Min: " + pb1.getMin() + ", max: " + pb1.getMax()
                + ", value: " + pb1.getValue().intValue());
        l2.setValue("Min: " + pb2.getMin() + ", max: " + pb2.getMax()
                + ", value: " + pb2.getValue().intValue());
        l3.setValue("Min: " + pb3.getMin() + ", max: " + pb3.getMax()
                + ", value: " + pb3.getValue().intValue());

        // l1.setWidth("25%");
        vl.setMargin(true);
        vl.setSpacing(true);

        HorizontalLayout h1 = new HorizontalLayout(l1, pb1);
        h1.setSpacing(true);
        h1.setExpandRatio(pb1, 1);

        HorizontalLayout h2 = new HorizontalLayout(l2, pb2);
        h2.setSpacing(true);
        h2.setExpandRatio(pb2, 1);

        HorizontalLayout h3 = new HorizontalLayout(l3, pb3);
        h3.setSpacing(true);
        h3.setExpandRatio(pb3, 1);

        vl.addComponents(h1, h2, h3, b4);
        setContent(vl);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UnpublishedWebComponentUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
