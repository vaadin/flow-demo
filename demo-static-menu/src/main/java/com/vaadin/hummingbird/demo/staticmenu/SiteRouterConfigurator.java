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
package com.vaadin.hummingbird.demo.staticmenu;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.demo.staticmenu.community.CloudView;
import com.vaadin.hummingbird.demo.staticmenu.community.CommunityMenuView;
import com.vaadin.hummingbird.demo.staticmenu.community.CommunityView;
import com.vaadin.hummingbird.demo.staticmenu.community.ContributeView;
import com.vaadin.hummingbird.demo.staticmenu.community.ForumView;
import com.vaadin.hummingbird.demo.staticmenu.community.MeetupView;
import com.vaadin.hummingbird.demo.staticmenu.community.WebinarsView;
import com.vaadin.hummingbird.demo.staticmenu.community.WikiView;
import com.vaadin.hummingbird.demo.staticmenu.community.blog.BlogPost;
import com.vaadin.hummingbird.demo.staticmenu.community.blog.BlogsView;
import com.vaadin.hummingbird.demo.staticmenu.download.DocsSubView;
import com.vaadin.hummingbird.demo.staticmenu.download.DocsView;
import com.vaadin.hummingbird.demo.staticmenu.download.DownloadMenuView;
import com.vaadin.hummingbird.demo.staticmenu.download.DownloadView;
import com.vaadin.hummingbird.demo.staticmenu.download.IconsAboutView;
import com.vaadin.hummingbird.demo.staticmenu.download.IconsMenuView;
import com.vaadin.hummingbird.demo.staticmenu.download.IconsView;
import com.vaadin.hummingbird.demo.staticmenu.download.MavenView;
import com.vaadin.hummingbird.demo.staticmenu.elements.ElementsMenuView;
import com.vaadin.hummingbird.demo.staticmenu.elements.ElementsView;
import com.vaadin.hummingbird.demo.staticmenu.elements.VideosView;
import com.vaadin.hummingbird.demo.staticmenu.framework.DemoView;
import com.vaadin.hummingbird.demo.staticmenu.framework.DirectoryView;
import com.vaadin.hummingbird.demo.staticmenu.framework.FrameworkMenuView;
import com.vaadin.hummingbird.demo.staticmenu.framework.FrameworkView;
import com.vaadin.hummingbird.demo.staticmenu.framework.TutorialView;
import com.vaadin.hummingbird.router.ModifiableRouterConfiguration;
import com.vaadin.hummingbird.router.RouterConfigurator;
import com.vaadin.hummingbird.router.RouterUI;
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

}
