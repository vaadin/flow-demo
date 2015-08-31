package com.vaadin.hummingbird.bootstrap;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.bootstrap.components.Button;
import com.vaadin.hummingbird.bootstrap.components.Container;
import com.vaadin.hummingbird.bootstrap.components.Heading;
import com.vaadin.hummingbird.bootstrap.components.Jumbotron;
import com.vaadin.hummingbird.bootstrap.components.PageHeader;
import com.vaadin.hummingbird.bootstrap.components.Paragraph;
import com.vaadin.hummingbird.bootstrap.components.Button.Size;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

@StyleSheet({
        "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css",
        "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" })
@JavaScript({ "https://code.jquery.com/jquery-1.9.1.min.js",
        "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" })
// Mainly to avoid waiting for the loading indicator styles
@Theme("valo")
public class BootstapUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        // Based on http://getbootstrap.com/examples/theme/

        Container container = new Container();
        addComponent(container);

        container.addComponent(
                new Jumbotron(new Heading("Theme example"), new Paragraph(
                        "This is a template showcasing the optional theme stylesheet included in Bootstrap. Use it as a starting point to create something more unique by building on or modifying it.")));

        container.addComponent(new PageHeader("Buttons"));

        Paragraph largeButtons = new Paragraph("");
        container.addComponent(largeButtons);
        addButtons(Size.LARGE, largeButtons);

        Paragraph normalButtons = new Paragraph("");
        container.addComponent(normalButtons);
        addButtons(Size.DEFAULT, normalButtons);

        Paragraph smallButtons = new Paragraph("");
        container.addComponent(smallButtons);
        addButtons(Size.SMALL, smallButtons);

        Paragraph extraSmallButtons = new Paragraph("");
        container.addComponent(extraSmallButtons);
        addButtons(Size.EXTRA_SMALL, extraSmallButtons);

        container.addComponent(new PageHeader("Lorem ipsum..."));
    }

    private void addButtons(Button.Size size, CssLayout layout) {
        layout.addComponent(new Button("Default").setSize(size));
        layout.addComponent(
                new Button("Primary", Button.Type.PRIMARY).setSize(size));
        layout.addComponent(
                new Button("Success", Button.Type.SUCCESS).setSize(size));
        layout.addComponent(new Button("Info", Button.Type.INFO).setSize(size));
        layout.addComponent(
                new Button("Warning", Button.Type.WARNING).setSize(size));
        layout.addComponent(
                new Button("Danger", Button.Type.DANGER).setSize(size));
        layout.addComponent(new Button("Link", Button.Type.LINK).setSize(size));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = BootstapUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
