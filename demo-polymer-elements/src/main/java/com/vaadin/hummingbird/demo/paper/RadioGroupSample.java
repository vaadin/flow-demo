package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperRadioButton;
import com.vaadin.hummingbird.paper.PaperRadioGroup;
import com.vaadin.ui.CssLayout;

@PolymerStyle("radio-group-styles")
public class RadioGroupSample extends SampleBase {

    public RadioGroupSample() {
        addStyleName("radio-group-sample");

        CssLayout root = root();
        addComponent(root);

        addEnabled(root);
        addDisabled(root);
        addColor(root);
    }

    private void addEnabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Enabled");

        section.addComponents(new PaperRadioGroup().setAttrForSelected("name")
                .setSelected("c")
                .with(new PaperRadioButton().setName("o")
                        .setTextContent("Oxygen"),
                        new PaperRadioButton().setName("c")
                                .setTextContent("Carbon"),
                        new PaperRadioButton().setName("h")
                        .setTextContent("Hydrogen"),
                        new PaperRadioButton().setName("n")
                        .setTextContent("Nitrogen"),
                        new PaperRadioButton().setName("ca")
                        .setTextContent("Calcium")));
    }

    private void addDisabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Disabled");

        section.addComponents(new PaperRadioGroup().setAttrForSelected("name")
                .setSelected("c")
                .with(new PaperRadioButton().setName("o")
                        .setTextContent("Oxygen"),
                        new PaperRadioButton().setName("c")
                                .setTextContent("Carbon"),
                        new PaperRadioButton().setName("h").setDisabled(true)
                        .setTextContent("Hydrogen"),
                        new PaperRadioButton().setName("n").setDisabled(true)
                        .setTextContent("Nitrogen"),
                        new PaperRadioButton().setName("ca")
                                .setTextContent("Calcium")));
    }

    private void addColor(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Color");

        section.addComponents(new PaperRadioGroup().setAttrForSelected("name")
                .setSelected("c")
                .with(new PaperRadioButton().setName("o").withClassName("blue")
                        .setTextContent("Oxygen"),
                        new PaperRadioButton().setName("c").withClassName("red")
                                .setTextContent("Carbon"),
                        new PaperRadioButton().setName("h")
                                .withClassName("orange")
                        .setTextContent("Hydrogen"),
                        new PaperRadioButton().setName("n")
                                .withClassName("green")
                        .setTextContent("Nitrogen"),
                        new PaperRadioButton().setName("ca")
                                .withClassName("blue")
                                .setTextContent("Calcium")));
    }
}

