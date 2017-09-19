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
package com.vaadin.flow.demo.staticmenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.vaadin.annotations.AnnotationReader;
import com.vaadin.annotations.ParentLayout;
import com.vaadin.annotations.Route;
import com.vaadin.annotations.RoutePrefix;
import com.vaadin.annotations.Title;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 * Generic helpers for the demo.
 *
 * @author Vaadin
 */
public interface Util {

    /**
     * Gets the name of the view.
     * <p>
     * This utility method exists so we can get the title based on only the view
     * class, for the menu. This works as we do not have any dynamic view names
     * in this site.
     *
     * @param navigationTarget
     *            the navigation target class
     * @return the target's name
     */
    static String getNavigationTargetName(
            Class<? extends Component> navigationTarget) {
        Optional<String> title = AnnotationReader
                .getAnnotationFor(navigationTarget, Title.class)
                .map(Title::value);
        return title.orElse(navigationTarget.getSimpleName());
    }

    /**
     * Gets the path this navigation target class can be found in.
     *
     * @param navigationTarget
     *            the navigation target class
     * @return the target's path
     */
    static String getNavigationTargetPath(
            Class<? extends Component> navigationTarget) {
        // NOTE! should use Router.getUrl() when #2295 is implemented.
        return getNavigationRoute(navigationTarget);
    }

    /**
     * Collect full navigationRoute for view.
     * 
     * !note! this should be removed after #2295 is implemented
     * 
     * @param navigationTarget
     *            target to get route for
     * @return full route
     */
    static String getNavigationRoute(Class<?> navigationTarget) {
        Route annotation = navigationTarget.getAnnotation(Route.class);
        if (annotation.absolute()) {
            return annotation.value();
        }

        StringBuilder fullRoute = new StringBuilder();
        List<String> parentRoutePrefixes = getParentRoutePrefixes(
                navigationTarget);
        Collections.reverse(parentRoutePrefixes);

        parentRoutePrefixes
                .forEach(prefix -> fullRoute.append(prefix).append("/"));

        fullRoute.append(annotation.value());

        return fullRoute.toString();
    }

    /**
     * Recurse routes for component.
     *
     * @param component
     *            component to recurse routes for any RoutePrefixes
     * @return list of parent layouts
     */
    static List<String> getParentRoutePrefixes(Class<?> component) {
        List<String> list = new ArrayList<>();

        Optional<Route> router = AnnotationReader.getAnnotationFor(component,
                Route.class);
        Optional<ParentLayout> parentLayout = AnnotationReader
                .getAnnotationFor(component, ParentLayout.class);
        Optional<RoutePrefix> routePrefix = AnnotationReader
                .getAnnotationFor(component, RoutePrefix.class);

        routePrefix.ifPresent(prefix -> list.add(prefix.value()));

        // break chain on an absolute RoutePrefix or Route
        if ((routePrefix.isPresent() && routePrefix.get().absolute())
                || (router.isPresent() && router.get().absolute())) {
            return list;
        }

        if (router.isPresent() && !router.get().layout().equals(UI.class)) {
            list.addAll(getParentRoutePrefixes(router.get().layout()));
        } else if (parentLayout.isPresent()) {
            list.addAll(getParentRoutePrefixes(parentLayout.get().value()));
        }

        return list;
    }
}
