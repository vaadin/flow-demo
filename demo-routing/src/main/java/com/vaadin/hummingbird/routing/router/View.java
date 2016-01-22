package com.vaadin.hummingbird.routing.router;

public interface View {

    String url();

    // TODO add state object, e.g. a hash-map?
    default void open(String fragment) {
    }

    default String aliases() {
        return null;
    }

    default String parentViewUrls() {
        return null;
    }

    // TODO needs to be able to continue navigation if blocked
    default boolean remove() {
        return true;
    };
}
