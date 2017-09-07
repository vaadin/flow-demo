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

import java.util.Optional;

import com.vaadin.annotations.AnnotationReader;
import com.vaadin.annotations.Route;
import com.vaadin.annotations.Title;
import com.vaadin.ui.Component;

/**
 * Generic helpers for the demo.
 *
 * @author Vaadin
 * @since
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
        if (title.isPresent()) {
            return title.get();
        } else {
            return navigationTarget.getSimpleName();
        }
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
        Optional<String> title = AnnotationReader
                .getAnnotationFor(navigationTarget, Route.class)
                .map(Route::value);
        return title.orElseThrow(() -> new IllegalArgumentException(String
                .format("Attempted to get navigation target path for class '%s' "
                        + "which doesn't hava a valid @Route annotation.",
                        navigationTarget.getName())));
    }
}
