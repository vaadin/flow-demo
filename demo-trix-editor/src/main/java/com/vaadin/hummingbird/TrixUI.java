package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.addon.trixeditor.TrixEditor;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Viewport("user-scalable=no,initial-scale=1.0")
@Push
public class TrixUI extends UI {

    private VerticalLayout log;
    private int mouseMoveIndex;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        TrixEditor editor = new TrixEditor();
        editor.addValueListener(e -> {
            while (getComponentCount() > 10) {
                removeComponent(getComponent(1));
            }
            addComponent(new Label("Value is now: " + e.getValue()));
        });
        addComponent(editor);

        // mouseMoveIndex = 0;
        // editor.getElement().addEventData("mousemove", "event.clientX");
        // editor.getElement().addEventData("mousemove", "event.clientY");
        // editor.getElement().addEventListener("mousemove", e -> {
        // while (getComponentCount() > 10) {
        // removeComponent(getComponent(1));
        // }
        // addComponent(new Label("Mouse move " + (mouseMoveIndex++) + " X: "
        // + e.getNumber("event.clientX") + " Y: "
        // + e.getNumber("event.clientY")));
        // });

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TrixUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
