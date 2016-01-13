package com.vaadin.hummingbird.demo;

import com.vaadin.server.communication.UidlWriter.Dependency;
import com.vaadin.server.communication.UidlWriter.Dependency.Type;

public class PolymerThemes {

    public static final String[] themes = new String[] { "sampler", "candy",
            "classical", "dark-side", "denim", "gelati", "golden-goose", "ice",
            "the-times" };

    public static Dependency getDependency(String themeName) {
        return new Dependency(Type.HTML,
                new StringBuilder("vaadin://themes/polymer/").append(themeName)
                        .append(".html").toString());
    }

}