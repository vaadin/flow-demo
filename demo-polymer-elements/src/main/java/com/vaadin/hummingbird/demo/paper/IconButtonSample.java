package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperIconButton;
import com.vaadin.ui.AbstractSimpleDOMComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

@PolymerStyle("icon-button-styles")
public class IconButtonSample extends SampleBase {

    public IconButtonSample() {
        CssLayout root = root();
        addComponent(root);
        addStyleName("icon-button-sample");

        addEnabled(root);
        addDisabled(root);
        addColor(root);
        addSize(root);
    }

    private void addEnabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Enabled");

        section.addComponents(
                new PaperIconButton().setIconPolymer("menu")
                        .setTitle(
                                "menu")
                .withClassName("with-click-listener")
                .withClickListener(e -> ((AbstractSimpleDOMComponentContainer) e
                        .getPolymerComponent().getParent()).addComponent(
                                new PaperDialog().withClassName("event-dialog")
                                        .setTextContent(
                                                "EVENT: " + e.toString())
                                        .open())),
                new PaperIconButton().setIconPolymer("favorite")
                        .setTitle("heart"),
                new PaperIconButton().setIconPolymer("arrow-back")
                        .setTitle("arrow-back"),
                new PaperIconButton().setIconPolymer("arrow-forward")
                        .setTitle("arrow-forward"),
                new PaperIconButton().setIconPolymer("clear").setTitle("clear"),
                new PaperIconButton().setIconPolymer("polymer")
                        .setTitle("polymer"),
                new PaperIconButton()
                        .setSrc("https://assets-cdn.github.com/images/modules/logos_page/Octocat.png")
                        .setAriaLabel("octocat").setTitle("octocat"));
    }

    private void addDisabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Disabled");

        section.addComponents(
                new PaperIconButton().setIconPolymer("menu").setTitle("menu")
                        .setDisabled(true),
                new PaperIconButton().setIconPolymer("favorite")
                        .setTitle("heart").setDisabled(true),
                new PaperIconButton().setIconPolymer("arrow-back")
                        .setTitle("arrow-back").setDisabled(true),
                new PaperIconButton().setIconPolymer("arrow-forward")
                        .setTitle("arrow-forward").setDisabled(true),
                new PaperIconButton().setIconPolymer("clear").setTitle("clear")
                        .setDisabled(true),
                new PaperIconButton().setIconPolymer("polymer")
                        .setTitle("polymer").setDisabled(true),
                new PaperIconButton()
                        .setSrc("https://assets-cdn.github.com/images/modules/logos_page/Octocat.png")
                        .setAriaLabel("octocat").setTitle("octocat")
                        .setDisabled(true));
    }

    private void addColor(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Color");

        section.addComponents(
                new PaperIconButton().setIconPolymer("menu").setTitle("menu")
                        .withClassName("blue"),
                new PaperIconButton().setIconPolymer("favorite")
                        .setTitle("heart").withClassName("red"),
                new PaperIconButton().setIconPolymer("arrow-back")
                        .setTitle("arrow-back").withClassName("orange"),
                new PaperIconButton().setIconPolymer("arrow-forward")
                        .setTitle("arrow-forward").withClassName("green"),
                new PaperIconButton().setIconPolymer("clear").setTitle("clear")
                        .withClassName("blue"),
                new PaperIconButton().setIconPolymer("polymer")
                        .setTitle("polymer").withClassName("red"),
                new PaperIconButton()
                        .setSrc("https://assets-cdn.github.com/images/modules/logos_page/Octocat.png")
                        .setAriaLabel("octocat").setTitle("octocat")
                        .withClassName("blue"));
    }

    private void addSize(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Size");
        section.addComponents(
                new PaperIconButton().setIconPolymer("favorite")
                        .setTitle("heart").withClassName("huge"),
                new HTML("<br>"), new HTML("<br>"), new HTML("<br>"),
                new PaperIconButton().setIconPolymer("polymer")
                        .withClassName(LAST_COMPONENT_CLASS_NAME)
                        .setTitle("polymer").withClassName("huge"));
    }
}
