package com.vaadin.hummingbird.demo;

import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.polymer.PolymerComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

public class SampleBase extends CssLayout {

    private int eventCounter;

    protected void onEvent(Component.Event event) {
        StringBuilder stringBuilder = new StringBuilder(
                "Event " + eventCounter++ + ": ")
                        .append(event.getClass().getSimpleName())
                        .append(", Source Text Content: ")
                        .append(((PolymerComponent<?>) event.getComponent())
                                .getTextContent());
        final PaperDialog dialog = new PaperDialog();
        dialog.setEntryAnimation("scale-up-animation")
                .setExitAnimation("fade-out-animation")
                .with(new PaperButton().setTextContent(stringBuilder.toString())
                        .withClickListener(e -> dialog.setOpened(false)))
                .setOpened(true);
        addComponent(dialog);
    }

    protected CssLayout createHorizontalSection(CssLayout root, String name) {
        CssLayout sectionRoot = layout(null);
        HTML header = element("h4", name, false);
        CssLayout sectionContent = layout("horizontal-section");
        sectionRoot.addComponents(header, sectionContent);
        root.addComponent(sectionRoot);
        return sectionContent;
    }

    protected HTML style(String styling) {
        HTML style = new HTML("<style></style>");
        style.setInnerHtml(styling);
        return style;
    }

    protected CssLayout root() {
        return layout("horizontal-section-container center-justified layout");
    }

    protected CssLayout layout(String classNames) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName(classNames);
        return cssLayout;
    }

    protected CssLayout layout(String classNames, Component... components) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName(classNames);
        cssLayout.addComponents(components);
        return cssLayout;
    }

    protected HTML element(String tag) {
        return new HTML(new StringBuilder("<").append(tag).append("\"></")
                .append(tag).append(">").toString());
    }

    protected HTML element(String tag, String classNames) {
        return new HTML(new StringBuilder("<").append(tag).append(" class=\"")
                .append(classNames).append("\"></").append(tag).append(">")
                .toString());
    }

    protected HTML element(String tag, String classNames, String innerText) {
        return new HTML(new StringBuilder("<").append(tag).append(" class=\"")
                .append(classNames).append("\">").append(innerText).append("</")
                .append(tag).append(">").toString());
    }

    protected HTML element(String tag, String innerText, boolean b) {
        return new HTML(
                new StringBuilder("<").append(tag).append(">").append(innerText)
                        .append("</").append(tag).append(">").toString());
    }
}
