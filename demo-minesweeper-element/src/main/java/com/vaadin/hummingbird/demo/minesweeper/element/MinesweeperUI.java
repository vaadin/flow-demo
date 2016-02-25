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
package com.vaadin.hummingbird.demo.minesweeper.element;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.dom.Element;
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
        int rows, cols;

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
        String rowsParam = request.getParameter("rows");
        if (rowsParam != null) {
            rows = Integer.parseInt(rowsParam);
        } else {
            rows = 10;
        }
        String colsParam = request.getParameter("cols");
        if (colsParam != null) {
            cols = Integer.parseInt(colsParam);
        } else {
            cols = 10;
        }

        Element style = new Element("style");
        style.setTextContent("   td {" + //
                "                background: grey;" + //
                "                border: 1px solid black;" + //
                "                width:20px;" + //
                "                height:20px;" + //
                "        }" + //
                "        .empty {" + //
                "                background: white;" + //
                "        }" + //
                "        .mine {" + //
                "                background: red;" + //
                "        }" + //
                "        td {" + //
                "                text-align: center;" + //
                "        }");
        getElement().appendChild(style);

        ElementMinesweeper minesweeper = new ElementMinesweeper(seed,
                mineDensity, rows, cols);
        getElement().appendChild(minesweeper.getElement());
    }

}
