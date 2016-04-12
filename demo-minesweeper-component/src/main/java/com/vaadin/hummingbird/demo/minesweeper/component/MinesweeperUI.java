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
        long seed = getParam(request, "seed", (int) System.currentTimeMillis());
        double mineDensity = getParam(request, "mineDensity", 20) / 100.0;
        int rows = getParam(request, "rows", 10);
        int cols = getParam(request, "cols", 10);

        getPage().addStyleSheet("VAADIN/minesweeper.css");

        MinesweeperComponent minesweeper = new MinesweeperComponent(seed,
                mineDensity, rows, cols);
        add(minesweeper);
    }

    private int getParam(VaadinRequest request, String name, int defaultValue) {
        int value;
        String colsParam = request.getParameter(name);
        if (colsParam != null) {
            value = Integer.parseInt(colsParam);
        } else {
            value = defaultValue;
        }
        return value;
    }

}
