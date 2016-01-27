package com.vaadin.hummingbird.routing.router;

public interface ViewDisplay {

    void show(Router router, View topLevelView);

    default void remove(View topLevelView) {
    };
}
