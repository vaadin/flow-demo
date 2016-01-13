package com.vaadin.hummingbird.flipbox;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Viewport("user-scalable=no,initial-scale=1.0")
public class FlipBoxUI extends UI {

    private VerticalLayout log;
    private int mouseMoveIndex;
    private boolean lastShown = false;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        FlipBox box = new FlipBox();
        box.setWidth("250px");
        box.setHeight("350px");
        Front front = new Front();
        front.getElement().setStyle("border", "1px solid black");
        Back back = new Back();
        box.setFront(front);
        box.setBack(back);
        addComponent(box);
        box.addFlipListener(e -> {
            if (lastShown != e.isFlipped()) {
                lastShown = e.isFlipped();
                // flipbox sends multiple events
                // an event is sent whenever an animation INSIDE the flipbox
                // finishes, e.g. paper button ripple
                Notification.show("Flipped, now showing "
                        + (e.isFlipped() ? "back" : "front") + " side.");
            }
        });
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = FlipBoxUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
