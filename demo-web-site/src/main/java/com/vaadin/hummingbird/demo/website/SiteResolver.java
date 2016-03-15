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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.hummingbird.demo.website.community.BlogView;
import com.vaadin.hummingbird.demo.website.community.CloudView;
import com.vaadin.hummingbird.demo.website.community.CommunityMenuView;
import com.vaadin.hummingbird.demo.website.community.CommunityView;
import com.vaadin.hummingbird.demo.website.community.ContributeView;
import com.vaadin.hummingbird.demo.website.community.ForumView;
import com.vaadin.hummingbird.demo.website.community.MeetupView;
import com.vaadin.hummingbird.demo.website.community.WebinarsView;
import com.vaadin.hummingbird.demo.website.community.WikiView;
import com.vaadin.hummingbird.demo.website.download.DocsView;
import com.vaadin.hummingbird.demo.website.download.DownloadMenuView;
import com.vaadin.hummingbird.demo.website.download.DownloadView;
import com.vaadin.hummingbird.demo.website.download.IconsAboutView;
import com.vaadin.hummingbird.demo.website.download.IconsMenuView;
import com.vaadin.hummingbird.demo.website.download.IconsView;
import com.vaadin.hummingbird.demo.website.download.MavenView;
import com.vaadin.hummingbird.demo.website.elements.ElementsMenuView;
import com.vaadin.hummingbird.demo.website.elements.ElementsView;
import com.vaadin.hummingbird.demo.website.elements.VideosView;
import com.vaadin.hummingbird.demo.website.framework.DemoView;
import com.vaadin.hummingbird.demo.website.framework.DirectoryView;
import com.vaadin.hummingbird.demo.website.framework.FrameworkMenuView;
import com.vaadin.hummingbird.demo.website.framework.FrameworkView;
import com.vaadin.hummingbird.demo.website.framework.TutorialView;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.Location;
import com.vaadin.hummingbird.router.NavigationEvent;
import com.vaadin.hummingbird.router.NavigationHandler;
import com.vaadin.hummingbird.router.Resolver;
import com.vaadin.hummingbird.router.View;
import com.vaadin.hummingbird.router.ViewRenderer;

public final class SiteResolver implements Resolver {

    private Map<String, Class<? extends View>> routes = new HashMap<>();
    private Map<Class<? extends View>, Class<? extends HasChildView>[]> viewHierarchy = new HashMap<>();

    public SiteResolver() {
        addRoute("", mainView(HomeView.class));
        addRoute("framework", frameworkView(FrameworkView.class));
        addRoute("demo", frameworkView(DemoView.class));
        addRoute("tutorial", frameworkView(TutorialView.class));
        addRoute("directory", frameworkView(DirectoryView.class));

        addRoute("elements", elementsView(ElementsView.class));
        addRoute("elements/videos", elementsView(VideosView.class));

        addRoute("download", downloadView(DownloadView.class));
        addRoute("docs", downloadView(DocsView.class));
        addRoute("maven", downloadView(MavenView.class));
        addRoute("icons", iconsView(IconsView.class));
        addRoute("icons/about", iconsView(IconsAboutView.class));

        addRoute("community", communityView(CommunityView.class));
        addRoute("forum", communityView(ForumView.class));
        addRoute("blog", communityView(BlogView.class));
        addRoute("wiki", communityView(WikiView.class));
        addRoute("meetup", communityView(MeetupView.class));
        addRoute("contribute", communityView(ContributeView.class));
        addRoute("cloud", communityView(CloudView.class));
        addRoute("vaadin-webinars", communityView(WebinarsView.class));
    }

    public Optional<String> getPath(Class<? extends View> viewClass) {
        return routes.entrySet().stream().filter(entry -> {
            return entry.getValue() == viewClass;
        }).map(Entry::getKey).findFirst();
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    private final void addRoute(String path,
            Class<? extends View>... viewChain) {
        routes.put(path, viewChain[0]);
        viewHierarchy.put(viewChain[0], (Class<? extends HasChildView>[]) Arrays
                .copyOfRange(viewChain, 1, viewChain.length));
    }

    @SuppressWarnings("unchecked")
    private Class<? extends View>[] mainView(Class<HomeView> class1) {
        return new Class[] { class1, MainMenuView.class };
    }

    @SuppressWarnings("unchecked")
    private Class<? extends View>[] frameworkView(
            Class<? extends View> class1) {
        return new Class[] { class1, FrameworkMenuView.class,
                MainMenuView.class };
    }

    @SuppressWarnings("unchecked")
    private Class<? extends View>[] downloadView(Class<? extends View> class1) {
        return new Class[] { class1, DownloadMenuView.class,
                MainMenuView.class };
    }

    @SuppressWarnings("unchecked")
    private Class<? extends View>[] communityView(
            Class<? extends View> class1) {
        return new Class[] { class1, CommunityMenuView.class,
                MainMenuView.class };
    }

    @SuppressWarnings("unchecked")
    private Class<? extends View>[] iconsView(Class<? extends View> class1) {
        return new Class[] { class1, IconsMenuView.class,
                DownloadMenuView.class, MainMenuView.class };
    }

    @SuppressWarnings("unchecked")
    private Class<? extends View>[] elementsView(Class<? extends View> class1) {
        return new Class[] { class1, ElementsMenuView.class,
                MainMenuView.class };
    }

    @Override
    public NavigationHandler resolve(NavigationEvent navigationEvent) {
        Location location = navigationEvent.getLocation();
        String path = location.getSegments().stream()
                .collect(Collectors.joining("/"));
        if (routes.containsKey(path)) {
            Class<? extends View> viewClass = routes.get(path);
            Class<? extends HasChildView>[] chain = viewHierarchy
                    .get(viewClass);
            return new ViewRenderer(viewClass, chain);
        }
        return null;
    }

    public Class<? extends HasChildView>[] getViewChain(
            Class<? extends View> viewClass) {
        return viewHierarchy.get(viewClass);
    }

}
