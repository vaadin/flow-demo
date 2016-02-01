package com.vaadin.hummingbird.routing.router;

public interface ViewDisplay {

    void show(View topLevelView, String path);

    default void remove(View topLevelView, String path) {
    };
}
