package com.vaadin.hummingbird.minesweeper;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.namespace.ElementPropertiesNamespace;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class MinesweeperUI extends UI {

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MinesweeperUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        long seed;
        double mineDensity;

        String seedParam = request.getParameter("seed");
        if (seedParam != null) {
            seed = Long.parseLong(seedParam);
        } else {
            seed = System.currentTimeMillis();
        }

        String mineDensityParam = request.getParameter("mineDensity");
        if (mineDensityParam != null) {
            mineDensity = Double.parseDouble(mineDensityParam);
        } else {
            mineDensity = 0.2;
        }

        Element style = new Element("style");
        style.getNode().getNamespace(ElementPropertiesNamespace.class)
                .setProperty("textContent", //
                        "   td {\n" + "                background: grey;\n" + //
                                "                border: 1px solid black;\n" + //
                                "                width:20px;\n" + //
                                "                height:20px;\n" + //
                                "        }\n" + //
                                "        .empty {\n" + //
                                "                background: white;\n" + //
                                "        }\n" + //
                                "        .mine {\n" + //
                                "                background: red;\n" + //
                                "        }\n" + //
                                "        td {\n" + //
                                "                text-align: center;\n" + //
                                "        }\n" + "");
        getElement().appendChild(style);
        ElementMinesweeper minesweeper = new ElementMinesweeper(seed,
                mineDensity);
        getElement().appendChild(minesweeper.getElement());
        minesweeper.initDOM();

    }

}
