package com.vaadin.flow.demo.dnd;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

@CssImport("./styles.css")
public class MainLayout extends AppLayout
        implements RouterLayout {

    public MainLayout() {
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        attachEvent.getUI().getRouter().getRegistry().getRegisteredRoutes()
                .forEach(routeData -> {
                    addToNavbar(new Tab(new RouterLink(routeData.getUrl(),
                            routeData.getNavigationTarget())));
                });
    }
}
