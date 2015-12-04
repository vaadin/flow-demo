package com.vaadin.hummingbird.demo.paper;

import com.vaadin.hummingbird.iron.IronIcon;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

public class ButtonSample extends CssLayout {

    public ButtonSample() {
        addStyleName("button-sample");

        CssLayout root = layout(
                "horizontal-section-container center-justified layout");
        addComponent(root);

        createFlatButtons(root);
        createRaisedButtons(root);
        createTogglableButtons(root);
        createColorButtons(root);
    }

    private void createFlatButtons(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Flat");

        sectionContent.addComponent(new PaperButton().setTextContent("button"));
        sectionContent.addComponent(new PaperButton().withClassName("colorful")
                .setTextContent("colorful"));
        sectionContent.addComponent(
                new PaperButton().setDisabled(true).setTextContent("disabled"));
        sectionContent.addComponent(
                new PaperButton().setNoink(true).setTextContent("noink"));
        sectionContent.addComponent(new PaperButton().setNoink(false)
                .withClassName("colorful custom")
                .with(new IronIcon().setIconPolymer("check"))
                .appendTextContent("ok"));
        sectionContent.addComponent(
                new PaperButton().setNoink(false).withClassName("custom")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel"));

    }

    private void createRaisedButtons(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Raised");

        sectionContent.addComponent(
                new PaperButton().setRaised(true).setTextContent("button"));
        sectionContent.addComponent(new PaperButton().setRaised(true)
                .withClassName("colorful").setTextContent("colorful"));
        sectionContent.addComponent(new PaperButton().setRaised(true)
                .setDisabled(true).setTextContent("disabled"));
        sectionContent.addComponent(new PaperButton().setRaised(true)
                .setNoink(true).setTextContent("noink"));
        sectionContent.addComponent(new PaperButton().setRaised(true)
                .withClassName("colorful custom")
                .with(new IronIcon().setIconPolymer("check"))
                .appendTextContent("ok"));
        sectionContent.addComponent(
                new PaperButton().setRaised(true).withClassName("custom")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel"));
    }

    private void createTogglableButtons(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Togglable");

        sectionContent.addComponent(
                new PaperButton().setToggles(true).setTextContent("button"));
        sectionContent.addComponent(new PaperButton().setToggles(true)
                .setRaised(true).setNoink(true).setTextContent("noink"));
        sectionContent
                .addComponent(new PaperButton().setToggles(true).setActive(true)
                        .withClassName("colorful").setTextContent("colorful"));
        sectionContent.addComponent(new PaperButton().setToggles(true)
                .setActive(true).setRaised(true).withClassName("colorful")
                .setTextContent("colorful"));

        sectionContent.addComponent(new PaperButton().setToggles(true)
                .withClassName("colorful custom")
                .with(new IronIcon().setIconPolymer("check"))
                .appendTextContent("ok"));
        sectionContent.addComponent(
                new PaperButton().setToggles(true).withClassName("custom")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel"));
    }

    private void createColorButtons(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Color");

        sectionContent.addComponent(new PaperButton()
                .withClassName("blue ripple").setTextContent("button"));
        sectionContent.addComponent(new PaperButton().setNoink(true)
                .withClassName("red ripple").setTextContent("noink"));
        sectionContent.addComponent(new PaperButton()
                .withClassName("orange ripple").setTextContent("button"));
        sectionContent.addComponent(new PaperButton()
                .withClassName("green ripple").setTextContent("button"));

        sectionContent
                .addComponent(new PaperButton().withClassName("red ripple")
                        .with(new IronIcon().setIconPolymer("check"))
                        .appendTextContent("ok"));
        sectionContent.addComponent(
                new PaperButton().setNoink(false).withClassName("blue ripple")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel"));

    }

    protected CssLayout createSection(CssLayout root, String name) {
        CssLayout sectionRoot = layout(null);
        HTML header = element("h4", name, false);
        CssLayout sectionContent = layout("horizontal-section");
        sectionRoot.addComponents(header, sectionContent);
        root.addComponent(sectionRoot);
        return sectionContent;
    }

    protected CssLayout layout(String classNames) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName(classNames);
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
