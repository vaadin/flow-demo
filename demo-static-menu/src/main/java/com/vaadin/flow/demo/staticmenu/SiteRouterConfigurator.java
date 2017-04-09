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

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.flow.demo.staticmenu.community.CommunityMenuView;
import com.vaadin.flow.demo.staticmenu.community.CommunityView;
import com.vaadin.flow.demo.staticmenu.community.blog.BlogPost;
import com.vaadin.flow.demo.staticmenu.community.blog.BlogsView;
import com.vaadin.flow.demo.staticmenu.download.DocsSubView;
import com.vaadin.flow.demo.staticmenu.download.DocsView;
import com.vaadin.flow.demo.staticmenu.download.DownloadMenuView;
import com.vaadin.flow.demo.staticmenu.download.DownloadView;
import com.vaadin.flow.demo.staticmenu.download.IconsAboutView;
import com.vaadin.flow.demo.staticmenu.download.IconsMenuView;
import com.vaadin.flow.demo.staticmenu.download.IconsView;
import com.vaadin.flow.demo.staticmenu.elements.ElementsMenuView;
import com.vaadin.flow.demo.staticmenu.elements.ElementsView;
import com.vaadin.flow.demo.staticmenu.framework.FrameworkMenuView;
import com.vaadin.flow.demo.staticmenu.framework.FrameworkView;
import com.vaadin.flow.demo.staticmenu.framework.TutorialView;
import com.vaadin.flow.router.RouterConfiguration;
import com.vaadin.flow.router.RouterConfigurator;
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
    @VaadinServletConfiguration(routerConfigurator = SiteRouterConfigurator.class, productionMode = false)
    public static class DemoSiteServlet extends VaadinServlet {
    }

    @Override
    public void configure(RouterConfiguration configuration) {
        configuration.setRoute("", HomeView.class, MainMenuView.class);
        configuration.setParentView(FrameworkMenuView.class,
                MainMenuView.class);
        configuration.setRoute("framework", FrameworkView.class,
                FrameworkMenuView.class);
        configuration.setRoute("tutorial", TutorialView.class,
                FrameworkMenuView.class);

        configuration.setParentView(ElementsMenuView.class, MainMenuView.class);
        configuration.setRoute("elements", ElementsView.class,
                ElementsMenuView.class);

        configuration.setParentView(DownloadMenuView.class, MainMenuView.class);
        configuration.setRoute("download", DownloadView.class,
                DownloadMenuView.class);
        configuration.setRoute("docs", DocsView.class, DownloadMenuView.class);
        configuration.setRoute("docs/*", DocsSubView.class,
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
        configuration.setParentView(BlogsView.class, CommunityMenuView.class);
        configuration.setRoute("blog/{id}", BlogPost.class, BlogsView.class);

        configuration.setPageTitleGenerator(event -> {
            String viewName = Util
                    .getViewName(event.getViewChain().get(0).getClass());
            return viewName + " - vaadin.com";
        });

        configuration.setErrorView(ErrorView.class, MainMenuView.class);
    }

}
