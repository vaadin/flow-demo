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
package com.vaadin.flow.demo.minesweeper.component;

import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.demo.minesweeper.component.component.MinesweeperComponent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

/**
 * UI which demonstrates how you can use the Flow {@link Component} API to
 * create a Minesweeper game.
 */
@Route(value = "")
public class MinesweeperView extends Div implements BeforeEnterObserver {

    private static int getParam(BeforeEnterEvent event, String name,
            int defaultValue) {
        List<String> param = event.getLocation().getQueryParameters()
                .getParameters().get(name);
        return Optional.ofNullable(param).map(l -> l.get(0))
                .map(Integer::parseInt).orElse(defaultValue);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        long seed = getParam(event, "seed", (int) System.currentTimeMillis());
        double mineDensity = getParam(event, "mineDensity", 20) / 100.0;
        int rows = getParam(event, "rows", 10);
        int cols = getParam(event, "cols", 10);

        add(new MinesweeperComponent(seed, mineDensity, rows, cols));
    }

}
