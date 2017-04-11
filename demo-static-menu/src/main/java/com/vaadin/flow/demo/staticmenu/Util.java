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
package com.vaadin.flow.demo.staticmenu;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.vaadin.annotations.AnnotationReader;
import com.vaadin.flow.router.ImmutableRouterConfiguration;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.View;
import com.vaadin.server.VaadinService;

/**
 * Generic helpers for the demo.
 *
 * @author Vaadin
 * @since
 */
public interface Util {

    /**
     * Gets the active router.
     *
     * @return the active router
     */
    static Router getRouter() {
        return VaadinService.getCurrentRequest().getService().getRouter();
    }

    /**
     * Gets the active router configuration.
     *
     * @return the active router configuration
     */
    static ImmutableRouterConfiguration getRouterConfiguration() {
        return getRouter().getConfiguration();
    }

    /**
     * Gets the name of the view.
     * <p>
     * This utility method exists so we can get the title based on only the view
     * class, for the menu. This works as we do not have any dynamic view names
     * in this site.
     *
     * @param viewClass
     *            the view class
     * @return the view name
     */
    static String getViewName(Class<? extends View> viewClass) {
        Optional<String> title = AnnotationReader.getPageTitle(viewClass);
        if (title.isPresent()) {
            return title.get();
        } else {
            return viewClass.getSimpleName().replace("View", "");
        }
    }

    /**
     * Returns a path you can use to navigate to the given view.
     *
     * @param childViewClass
     *            the view to navigate to
     * @return a path to the given view
     */
    static Optional<String> getNavigablePath(
            Class<? extends View> childViewClass) {
        return getNavigablePath(childViewClass, Collections.emptyMap());
    }

    /**
     * Returns a path you can use to navigate to the given view, using the given
     * parameters.
     *
     * @param childViewClass
     *            the view to navigate to
     * @param parameters
     *            the parameters to use
     * @return a path to the given view
     */
    static Optional<String> getNavigablePath(
            Class<? extends View> childViewClass,
            Map<String, String> parameters) {
        Optional<String> path = Util.getRouterConfiguration()
                .getRoute(childViewClass);
        if (path.isPresent() && parameters != null) {
            String url = path.get();
            for (Entry<String, String> entry : parameters.entrySet()) {
                url = url.replace("{" + entry.getKey() + "}", entry.getValue());
            }
            path = Optional.of(url);
        }
        return path;
    }

}
