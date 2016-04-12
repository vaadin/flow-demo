/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird.demo.minesweeper.component;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * UI which demonstrates how you can use the Hummingbird {@link Element} API to
 * create a Minesweeper game.
 */
public class MinesweeperUI extends UI {

    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MinesweeperUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        long seed = getSeed(request);

        double mineDensity = getDensity(request);
        int rows = getRows(request);
        int cols = getColumns(request);

        getPage().addStyleSheet("VAADIN/minesweeper.css");

        MinesweeperComponent minesweeper = new MinesweeperComponent(seed,
                mineDensity, rows, cols);
        add(minesweeper);
    }

    private int getColumns(VaadinRequest request) {
        int cols;
        String colsParam = request.getParameter("cols");
        if (colsParam != null) {
            cols = Integer.parseInt(colsParam);
        } else {
            cols = 10;
        }
        return cols;
    }

    private int getRows(VaadinRequest request) {
        int rows;
        String rowsParam = request.getParameter("rows");
        if (rowsParam != null) {
            rows = Integer.parseInt(rowsParam);
        } else {
            rows = 10;
        }
        return rows;
    }

    private double getDensity(VaadinRequest request) {
        double mineDensity;
        String mineDensityParam = request.getParameter("mineDensity");
        if (mineDensityParam != null) {
            mineDensity = Double.parseDouble(mineDensityParam);
        } else {
            mineDensity = 0.2;
        }
        return mineDensity;
    }

    private long getSeed(VaadinRequest request) {
        long seed;
        String seedParam = request.getParameter("seed");
        if (seedParam != null) {
            seed = Long.parseLong(seedParam);
        } else {
            seed = System.currentTimeMillis();
        }
        return seed;
    }

}
