package com.vaadin.hummingbird.demo.helloworld.element;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class HelloWorldUI extends UI {

    @WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HelloWorldUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Element textInput = new Element("input").setAttribute("id", "inputId")
                .setAttribute("placeholder", "Enter your name");

        Element button = new Element("button").setTextContent("Say hello");
        button.addEventListener("click", e -> {
            String inputValue = e.getEventData().getString("inputId.value");
            Element helloText = new Element("div")
                    .setTextContent("Hello " + inputValue);
            helloText.getClassList().add("helloText");
            getElement().appendChild(helloText);
        } , "inputId.value");

        getElement().appendChild(textInput, button);
    }

}
