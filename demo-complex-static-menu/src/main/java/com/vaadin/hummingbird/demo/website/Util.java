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
package com.vaadin.hummingbird.demo.website;

import com.vaadin.annotations.AnnotationReader;
import com.vaadin.hummingbird.router.Router;
import com.vaadin.hummingbird.router.RouterConfiguration;
import com.vaadin.hummingbird.router.RouterUI;
import com.vaadin.hummingbird.router.View;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class Util {

    public static Router getRouter() {
        return VaadinService.getCurrentRequest().getService().getRouter();
    }

    public static RouterUI getUI() {
        return (RouterUI) UI.getCurrent();
    }

    public static RouterConfiguration getRouterConfiguration() {
        return getRouter().getConfiguration();
    }

    public static String getViewTitle(Class<? extends View> viewClass) {
        return getViewName(viewClass) + " - vaadin.com";
    }

    public static String getViewName(Class<? extends View> viewClass) {
        String title = AnnotationReader.getPageTitle(viewClass);
        if (title == null) {
            title = viewClass.getSimpleName().replace("View", "");
        }

        return title;
    }

}
