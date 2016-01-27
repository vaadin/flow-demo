package com.vaadin.hummingbird.routing;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.routing.router.History;
import com.vaadin.hummingbird.routing.router.Router;
import com.vaadin.hummingbird.routing.router.Router.HistoryStateUpdateStrategy;
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
        History history = new History(this);

        MainView mainView = new MainView();
        setContent(mainView);

        Router router = new Router(history);
        router.setViewDisplay(mainView);
        router.addView(new HomeView());
        router.addView(new FrameworkView());
        router.addView(new ElementsView());
        router.addView(new ProToolsView());
        router.addView(new DownloadView());
        router.addView(new CommunityView());
        router.addView(new ServicesView());

        // framework
        router.addView(new DemoView());
        router.addView(new TutorialView());
        router.addView(new DirectoryView());

        // downloads
        router.addView(new DocsView());
        router.addView(new MavenView());
        router.addView(new VaadinIconsView());

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
            pathInfo = "home";
        }
        router.open(pathInfo, HistoryStateUpdateStrategy.NO);
    }

}