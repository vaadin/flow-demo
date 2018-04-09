/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.minesweeper.element;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;

/**
 * View which demonstrates how you can use the Flow {@link Element} API to
 * create a Minesweeper game.
 */
@Route("")
@StyleSheet("frontend://minesweeper.css")
public class MinesweeperView extends Div {

    /**
     * Create Minesweeper view.
     */
    public MinesweeperView() {
        long seed;
        double mineDensity;
        int rows, cols;
        VaadinRequest request = VaadinService.getCurrentRequest();

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

        ElementMinesweeper minesweeper = new ElementMinesweeper(seed,
                mineDensity, rows, cols);
        getElement().appendChild(minesweeper.getElement());
    }

}
