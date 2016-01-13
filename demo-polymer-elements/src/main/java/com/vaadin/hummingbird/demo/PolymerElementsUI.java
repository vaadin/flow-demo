package com.vaadin.hummingbird.demo;

import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

@Push
@com.vaadin.annotations.HTML({ "vaadin://themes/polymer/polymer-styles.html",
        "context://bower_components/paper-styles/demo-pages.html",
        "context://bower_components/neon-animation/neon-animation.html" })
@StyleSheet("vaadin://themes/polymer-demo/styles.css")
@Viewport("user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1")
public class PolymerElementsUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        String currentTheme = resolveThemeFromURL(vaadinRequest);

        PolymerSampler polymerSampler = new PolymerSampler();
        CssLayout cssLayout = new CssLayout();
        cssLayout.addComponent(polymerSampler);
        setContent(cssLayout);

        polymerSampler.setCurrentTheme(currentTheme);
    }

    private String resolveThemeFromURL(VaadinRequest vaadinRequest) {
        String parameter = vaadinRequest.getParameter("theme");
        String themeName = Stream.of(PolymerThemes.themes)
                .filter(str -> str.equals(parameter)).findFirst()
                .orElse("sampler");
        Logger.getLogger(getClass().getName())
                .info("USING THEME: " + themeName);
        addDynamicDependency(PolymerThemes.getDependency(themeName));
        return themeName;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PolymerElementsUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}