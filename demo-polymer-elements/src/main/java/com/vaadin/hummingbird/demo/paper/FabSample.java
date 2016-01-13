package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperFab;
import com.vaadin.ui.CssLayout;

@PolymerStyle("fab-styles")
public class FabSample extends SampleBase {

    public FabSample() {
        addStyleName("fab-sample");
        CssLayout root = root();
        addComponent(root);

        addEnabled(root);
        addDisabled(root);
        addColors(root);
    }

    private void addEnabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Enabled");

        section.addComponents(
                new PaperFab().setIconPolymer("arrow-forward")
                        .setTitle("arrow-forward"),
                new PaperFab().setIconPolymer("create").setTitle("create"),
                new PaperFab().setIconPolymer("favorite").setTitle("heart"),
                new PaperFab().setMini(true).setIconPolymer("done")
                        .setTitle("done"),
                new PaperFab().setMini(true).setIconPolymer("reply")
                        .setTitle("reply"));
    }

    private void addDisabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Disabled");

        section.addComponents(
                new PaperFab().setIconPolymer("arrow-forward").setDisabled(true)
                        .setTitle("arrow-forward"),
                new PaperFab().setIconPolymer("create").setDisabled(true)
                        .setTitle("create"),
                new PaperFab().setIconPolymer("favorite").setDisabled(true)
                        .setTitle("heart"),
                new PaperFab().setMini(true).setIconPolymer("done")
                        .setDisabled(true).setTitle("done"),
                new PaperFab().setMini(true).setIconPolymer("reply")
                        .setDisabled(true).setTitle("reply"));
    }

    private void addColors(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Colors");

        section.addComponents(
                new PaperFab().setIconPolymer("arrow-forward")
                        .setTitle("arrow-forward").withClassName("blue"),
                new PaperFab().setIconPolymer("create").setTitle("create")
                        .withClassName("red"),
                new PaperFab().setIconPolymer("favorite").setTitle("heart")
                        .withClassName("orange"),
                new PaperFab().setMini(true).setIconPolymer("done")
                        .setTitle("done").withClassName("green"),
                new PaperFab().setMini(true).setIconPolymer("reply")
                        .setTitle("reply").withClassName("blue"));
    }

}
