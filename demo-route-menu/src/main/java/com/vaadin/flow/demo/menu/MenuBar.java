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
package com.vaadin.flow.demo.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class MenuBar extends UnorderedList {

    private List<Class<?>> external = new ArrayList<>();

    public MenuBar() {
        setId("menu");
        Map<Class<? extends RouterLayout>, List<RouteData>> routes = UI
                .getCurrent().getRouter().get().getRoutesByParent();
        List<Class<? extends RouterLayout>> parentLayouts = routes.keySet()
                .stream().collect(Collectors.toList());

        // We handle the external dependencies differently.
        parentLayouts.remove(UI.class);

        Collections.sort(parentLayouts, (o1, o2) -> {
            if (o1.equals(UI.class)) {
                return 1;
            } else if (o2.equals(UI.class)) {
                return -1;
            }
            return o1.getSimpleName().compareToIgnoreCase(o2.getSimpleName());
        });
        parentLayouts.forEach(key -> {
            ListItem listItem = new ListItem();
            UnorderedList subList = new UnorderedList();
            routes.get(key).forEach(route -> {
                RouterLink routerLink = new RouterLink(getRouteName(route),
                        route.getNavigationTarget());
                // ApplicationLayout children should be as root targets.
                if (key.equals(ApplicationLayout.class)) {
                    ListItem menuItem = new ListItem(routerLink);
                    add(menuItem);
                } else {
                    subList.add(new ListItem(routerLink));
                }
            });
            if (subList.getChildren().count() != 0) {
                String title = key.getSimpleName();
                if (key.isAnnotationPresent(PageTitle.class)) {
                    title = key.getAnnotation(PageTitle.class).value();
                }
                Anchor anchor = new Anchor("#", title);
                listItem.add(anchor);
                listItem.add(subList);
                add(listItem);
            }
        });

        // Handle external routes that do not contain our ApplicationLayout
        if (routes.containsKey(UI.class)) {
            ListItem listItem = new ListItem();
            UnorderedList subList = new UnorderedList();
            routes.get(UI.class).forEach(route -> {
                external.add(route.getNavigationTarget());

                String routeName = getRouteName(route);
                RouterLink routerLink = new RouterLink(
                        routeName.substring(0, routeName.indexOf("View")),
                        route.getNavigationTarget());
                subList.add(new ListItem(routerLink));
            });
            listItem.add(new Anchor("#", "External Demos"));
            listItem.add(subList);
            add(listItem);
        }
    }

    private String getRouteName(RouteData route) {
        Class<? extends Component> navigationTarget = route
                .getNavigationTarget();
        if (navigationTarget.isAnnotationPresent(PageTitle.class)) {
            return navigationTarget.getAnnotation(PageTitle.class).value();
        }
        // TODO: something else for getting the title?
        return navigationTarget.getSimpleName();
    }

    protected boolean isExternal(Class<?> target) {
        return external.contains(target);
    }
}
