package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperRadioButton;
import com.vaadin.ui.CssLayout;

@PolymerStyle("radio-button-styles")
public class RadioButtonSample extends SampleBase {

    public RadioButtonSample() {
        addStyleName("radio-button-sample");

        CssLayout root = root();
        addComponent(root);

        addEnabled(root);
        addDisabled(root);
        addColor(root);
    }

    private void addEnabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Enabled");

        section.addComponents(new PaperRadioButton().setTextContent("Oxygen"),
                new PaperRadioButton().setTextContent("Carbon"),
                new PaperRadioButton().setChecked(true)
                        .setTextContent("Hydrogen"),
                new PaperRadioButton().setChecked(true)
                        .setTextContent("Nitrogen"),
                new PaperRadioButton().setChecked(true)
                        .setTextContent("Calcium"));
    }

    private void addDisabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Disabled");

        section.addComponents(
                new PaperRadioButton().setDisabled(true)
                        .setTextContent("Oxygen"),
                new PaperRadioButton().setDisabled(true)
                        .setTextContent("Carbon"),
                new PaperRadioButton().setDisabled(true).setChecked(true)
                        .setTextContent("Hydrogen"),
                new PaperRadioButton().setDisabled(true).setChecked(true)
                        .setTextContent("Nitrogen"),
                new PaperRadioButton().setDisabled(true).setChecked(true)
                        .setTextContent("Calcium"));
    }

    private void addColor(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Color");

        section.addComponents(
                new PaperRadioButton().setChecked(true).withClassName("blue")
                        .setTextContent("Oxygen"),
                new PaperRadioButton().setChecked(true).withClassName("red")
                        .setTextContent("Carbon"),
                new PaperRadioButton().setChecked(true).withClassName("orange")
                        .setTextContent("Hydrogen"),
                new PaperRadioButton().setChecked(true).withClassName("green")
                        .setTextContent("Nitrogen"),
                new PaperRadioButton().setChecked(true).withClassName("blue")
                        .setTextContent("Calcium"));
    }
}

