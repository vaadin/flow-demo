package com.vaadin.hummingbird.routing.router;

public interface ViewDisplay {

    void show(View topLevelView);

    default void remove(View topLevelView) {
    };
}
