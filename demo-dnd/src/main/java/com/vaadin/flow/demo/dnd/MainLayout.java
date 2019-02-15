package com.vaadin.flow.demo.dnd;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout implements RouterLayout {


    public MainLayout() {
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        attachEvent.getUI().getRouter().getRegistry().getRegisteredRoutes()
                .forEach(routeData -> {
                    addToNavbar(new RouterLink(routeData.getUrl(),
                            routeData.getNavigationTarget()));
                });
    }
}
