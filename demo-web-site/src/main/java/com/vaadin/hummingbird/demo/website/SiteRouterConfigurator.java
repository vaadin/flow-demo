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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.demo.website.community.CloudView;
import com.vaadin.hummingbird.demo.website.community.CommunityMenuView;
import com.vaadin.hummingbird.demo.website.community.CommunityView;
import com.vaadin.hummingbird.demo.website.community.ContributeView;
import com.vaadin.hummingbird.demo.website.community.ForumView;
import com.vaadin.hummingbird.demo.website.community.MeetupView;
import com.vaadin.hummingbird.demo.website.community.WebinarsView;
import com.vaadin.hummingbird.demo.website.community.WikiView;
import com.vaadin.hummingbird.demo.website.community.blog.BlogPost;
import com.vaadin.hummingbird.demo.website.community.blog.BlogsView;
import com.vaadin.hummingbird.demo.website.download.DocsSubView;
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
import com.vaadin.hummingbird.router.ModifiableRouterConfiguration;
import com.vaadin.hummingbird.router.RouterConfigurator;
import com.vaadin.hummingbird.router.RouterUI;
import com.vaadin.hummingbird.router.View;
import com.vaadin.server.VaadinServlet;

/**
 * Initializes the site by configuring the router to use different views for
 * different URLs.
 *
 * @author Vaadin Ltd
 */
public class SiteRouterConfigurator implements RouterConfigurator {
    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RouterUI.class, routerConfigurator = SiteRouterConfigurator.class, productionMode = false)
    public static class DemoSiteServlet extends VaadinServlet {
    }

    @Override
    public void configure(ModifiableRouterConfiguration configuration) {
        configuration.setRoute("", HomeView.class, MainMenuView.class);
        configuration.setParentView(FrameworkMenuView.class,
                MainMenuView.class);
        configuration.setRoute("framework", FrameworkView.class,
                FrameworkMenuView.class);
        configuration.setRoute("demo", DemoView.class, FrameworkMenuView.class);
        configuration.setRoute("tutorial", TutorialView.class,
                FrameworkMenuView.class);
        configuration.setRoute("directory", DirectoryView.class,
                FrameworkMenuView.class);

        configuration.setParentView(ElementsMenuView.class, MainMenuView.class);
        configuration.setRoute("elements", ElementsView.class,
                ElementsMenuView.class);
        configuration.setRoute("elements/videos", VideosView.class,
                ElementsMenuView.class);

        configuration.setParentView(DownloadMenuView.class, MainMenuView.class);
        configuration.setRoute("download", DownloadView.class,
                DownloadMenuView.class);
        configuration.setRoute("docs", DocsView.class, DownloadMenuView.class);
        configuration.setRoute("docs/*", DocsSubView.class,
                DownloadMenuView.class);
        configuration.setRoute("maven", MavenView.class,
                DownloadMenuView.class);

        configuration.setParentView(IconsMenuView.class,
                DownloadMenuView.class);
        configuration.setRoute("icons", IconsView.class, IconsMenuView.class);
        configuration.setRoute("icons/about", IconsAboutView.class,
                IconsMenuView.class);

        configuration.setParentView(CommunityMenuView.class,
                MainMenuView.class);
        configuration.setRoute("community", CommunityView.class,
                CommunityMenuView.class);
        configuration.setRoute("forum", ForumView.class,
                CommunityMenuView.class);
        configuration.setParentView(BlogsView.class, CommunityMenuView.class);
        configuration.setRoute("blog/{id}", BlogPost.class, BlogsView.class);

        configuration.setRoute("wiki", WikiView.class, CommunityMenuView.class);
        configuration.setRoute("meetup", MeetupView.class,
                CommunityMenuView.class);
        configuration.setRoute("contribute", ContributeView.class,
                CommunityMenuView.class);
        configuration.setRoute("cloud", CloudView.class,
                CommunityMenuView.class);
        configuration.setRoute("vaadin-webinars", WebinarsView.class,
                CommunityMenuView.class);
    }

    public static Optional<String> getNavigablePath(
            Class<? extends View> childViewClass) {
        return getNavigablePath(childViewClass, Collections.emptyMap());
    }

    public static Optional<String> getNavigablePath(
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

    public static Optional<String> getNavigablePath(
            Class<? extends View> childViewClass, String key, String value) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(key, value);
        return getNavigablePath(childViewClass, parameters);

    }

}
