package com.vaadin.hummingbird.demo.webcomponent.progressbubble;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.html.Button;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class ProgressBubbleUI extends UI {

    List<ProgressBubble> bubbles = new ArrayList<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        ProgressBubble bubble = new ProgressBubble(0, 100);
        bubble.getElement().getStyle().set("background", "green");
        bubbles.add(bubble);
        bubble = new ProgressBubble(0, 100);
        bubble.getElement().getStyle().set("background", "red");
        bubbles.add(bubble);
        bubble = new ProgressBubble(0, 100);
        bubble.getElement().getStyle().set("background", "blue");
        bubbles.add(bubble);
        bubble = new ProgressBubble(0, 100);
        bubble.getElement().getStyle().set("background", "purple");
        bubbles.add(bubble);

        Button makeProgress = new Button("Make progress");
        makeProgress.setId("makeProgress");
        makeProgress.addClickListener(e -> {
            bubbles.forEach(pb -> pb.setValue(pb.getValue() + 5));
        });

        Button increaseMax = new Button("Increase max value");
        increaseMax.setId("increaseMax");
        increaseMax.addClickListener(e -> {
            bubbles.forEach(pb -> pb.setMax(pb.getMax() * 2));
        });

        add(makeProgress, increaseMax);
        bubbles.forEach(this::add);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ProgressBubbleUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}