package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.event.EventListener;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.iron.IronIcon;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.event.TransitionendEvent;
import com.vaadin.ui.CssLayout;

@PolymerStyle("button-styles")
public class ButtonSample extends SampleBase {

    private final EventListener<TransitionendEvent<PaperButton>> transitionListener = event -> onEvent(
            event);

    public ButtonSample() {
        addStyleName("button-sample");

        CssLayout root = root();
        addComponent(root);

        createFlatButtons(root);
        createRaisedButtons(root);
        createTogglableButtons(root);
        createColorButtons(root);
        createTemplateButtons(root);
        createRaisedTemplateButtons(root);
    }

    private void createFlatButtons(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Flat");

        sectionContent.addComponent(new PaperButton().setTextContent("button")
                .addTransitionendListener(transitionListener));
        sectionContent.addComponent(new PaperButton().withClassName("colorful")
                .setTextContent("colorful")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(
                new PaperButton().setDisabled(true).setTextContent("disabled")
                        .addTransitionendListener(transitionListener));

        sectionContent.addComponent(
                new PaperButton().setNoink(true).setTextContent("noink")
                        .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setNoink(false)
                .withClassName("colorful custom")
                .with(new IronIcon().setIconPolymer("check"))
                .appendTextContent("ok")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(
                new PaperButton().setNoink(false).withClassName("custom")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel")
                        .addTransitionendListener(transitionListener));

    }

    private void createRaisedButtons(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Raised");

        sectionContent.addComponent(
                new PaperButton().setRaised(true).setTextContent("button")
                        .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setRaised(true)
                .withClassName("colorful").setTextContent("colorful")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setRaised(true)
                .setDisabled(true).setTextContent("disabled")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setRaised(true)
                .setNoink(true).setTextContent("noink")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setRaised(true)
                .withClassName("colorful custom")
                .with(new IronIcon().setIconPolymer("check"))
                .appendTextContent("ok")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(
                new PaperButton().setRaised(true).withClassName("custom")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel")
                        .addTransitionendListener(transitionListener));

    }

    private void createTogglableButtons(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Togglable");

        sectionContent.addComponent(
                new PaperButton().setToggles(true).setTextContent("button")
                        .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setToggles(true)
                .setRaised(true).setNoink(true).setTextContent("noink")
                .addTransitionendListener(transitionListener));

        sectionContent
                .addComponent(new PaperButton().setToggles(true).setActive(true)
                        .withClassName("colorful").setTextContent("colorful")
                        .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setToggles(true)
                .setActive(true).setRaised(true).withClassName("colorful")
                .setTextContent("colorful").withClassName("testing-button")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setToggles(true)
                .withClassName("colorful custom")
                .with(new IronIcon().setIconPolymer("check"))
                .appendTextContent("ok")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(
                new PaperButton().setToggles(true).withClassName("custom")
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel")
                        .addTransitionendListener(transitionListener));

    }

    private void createColorButtons(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Color");

        sectionContent.addComponent(new PaperButton()
                .withClassName("blue ripple").setTextContent("button")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton().setNoink(true)
                .withClassName("red ripple").setTextContent("noink")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton()
                .withClassName("orange ripple").setTextContent("button")
                .addTransitionendListener(transitionListener));

        sectionContent.addComponent(new PaperButton()
                .withClassName("green ripple").setTextContent("button")
                .addTransitionendListener(transitionListener));

        sectionContent
                .addComponent(new PaperButton().withClassName("red ripple")
                        .with(new IronIcon().setIconPolymer("check"))
                        .appendTextContent("ok")
                        .addTransitionendListener(transitionListener));

        sectionContent.addComponent(
                new PaperButton().setNoink(false).withClassName("blue ripple")
                        .withClassName(LAST_COMPONENT_CLASS_NAME)
                        .with(new IronIcon().setIconPolymer("clear"))
                        .appendTextContent("cancel")
                        .addTransitionendListener(transitionListener));
    }

    private void createTemplateButtons(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Template");

        section.addComponents(
                new PaperButton().setTextContent("Primary")
                        .withClassName("primary"),
                new PaperButton().setTextContent("Secondary")
                        .withClassName("secondary"),
                new PaperButton().setTextContent("Success")
                        .withClassName("success"),
                new PaperButton().setTextContent("Info").withClassName("info"),
                new PaperButton().setTextContent("Warning")
                        .withClassName("warning"),
                new PaperButton().setTextContent("Error")
                        .withClassName("error"),
                new PaperButton().setTextContent("Link").withClassName("link"));
    }

    private void createRaisedTemplateButtons(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Raised Template");

        section.addComponents(
                new PaperButton().setRaised(true).setTextContent("Primary")
                        .withClassName("primary"),
                new PaperButton().setRaised(true).setTextContent("Secondary")
                        .withClassName("secondary"),
                new PaperButton().setRaised(true).setTextContent("Success")
                        .withClassName("success"),
                new PaperButton().setRaised(true).setTextContent("Info")
                        .withClassName("info"),
                new PaperButton().setRaised(true).setTextContent("Warning")
                        .withClassName("warning"),
                new PaperButton().setRaised(true).setTextContent("Error")
                        .withClassName("error"),
                new PaperButton().setRaised(true).setTextContent("Link")
                        .withClassName("link"));
    }
}
