package com.vaadin.hummingbird.routing;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.routing.router.History;
import com.vaadin.hummingbird.routing.router.Router;
import com.vaadin.hummingbird.routing.router.Router.HistoryUpdateStrategy;
import com.vaadin.hummingbird.routing.ui.MainView;
import com.vaadin.hummingbird.routing.ui.view.CommunityView;
import com.vaadin.hummingbird.routing.ui.view.DownloadView;
import com.vaadin.hummingbird.routing.ui.view.ElementsView;
import com.vaadin.hummingbird.routing.ui.view.FrameworkView;
import com.vaadin.hummingbird.routing.ui.view.HomeView;
import com.vaadin.hummingbird.routing.ui.view.ProToolsView;
import com.vaadin.hummingbird.routing.ui.view.ServicesView;
import com.vaadin.hummingbird.routing.ui.view.download.DocsView;
import com.vaadin.hummingbird.routing.ui.view.download.MavenView;
import com.vaadin.hummingbird.routing.ui.view.download.VaadinIconsView;
import com.vaadin.hummingbird.routing.ui.view.framework.DemoView;
import com.vaadin.hummingbird.routing.ui.view.framework.DirectoryView;
import com.vaadin.hummingbird.routing.ui.view.framework.TutorialView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Title("Routing")
public class RoutingUI extends UI {

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RoutingUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }

    @Override
    protected void init(VaadinRequest request) {
        String pathInfo = request.getPathInfo();
        if ("/favicon.ico".equals(pathInfo)) {
            return;
        }
        History history = new History(this);
        MainView mainView = new MainView();
        setContent(mainView);

        Router router = new Router(history);
        router.setViewDisplay(mainView);
        router.addView(HomeView.class);
        router.addView(FrameworkView.class);
        router.addView(ElementsView.class);
        router.addView(ProToolsView.class);
        router.addView(DownloadView.class);
        router.addView(CommunityView.class);
        router.addView(ServicesView.class);

        // framework
        router.addView(DemoView.class);
        router.addView(TutorialView.class);
        router.addView(DirectoryView.class);

        // downloads
        router.addView(DocsView.class);
        router.addView(MavenView.class);
        router.addView(VaadinIconsView.class);

        if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
            router.open("home", HistoryUpdateStrategy.PUSH);
        } else if (pathInfo.equals("favicon.ico")) {
        } else {
            router.open(pathInfo, HistoryUpdateStrategy.NO);
        }
    }

}