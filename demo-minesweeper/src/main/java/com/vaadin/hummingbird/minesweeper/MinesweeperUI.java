package com.vaadin.hummingbird.minesweeper;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.minesweeper.secure.SecureMinesweeper;
import com.vaadin.hummingbird.minesweeper.simple.SimpleMinesweeper;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HTML;
import com.vaadin.ui.UI;

public class MinesweeperUI extends UI {

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MinesweeperUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        long seed = System.currentTimeMillis();
        double mineDensity = 0.2;

        String seedParam = request.getParameter("seed");
        if (seedParam != null) {
            seed = Long.parseLong(seedParam);
        }

        String mineDensityParam = request.getParameter("mineDensity");
        if (mineDensityParam != null) {
            mineDensity = Double.parseDouble(mineDensityParam);
        }

        addComponent(
                new HTML("<p>Simple version, cheat by looking at the DOM</p>"));
        addComponent(new SimpleMinesweeper(seed, mineDensity));
        addComponent(new HTML(
                "<p>Secure version, mine information on the server</p>"));
        addComponent(new SecureMinesweeper(seed, mineDensity));
    }

}