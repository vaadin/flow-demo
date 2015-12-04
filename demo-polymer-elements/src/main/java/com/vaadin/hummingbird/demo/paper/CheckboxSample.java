package com.vaadin.hummingbird.demo.paper;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperCheckbox;
import com.vaadin.ui.CssLayout;

public class CheckboxSample extends SampleBase {

    public CheckboxSample() {
        addStyleName("checkbox-sample");

        CssLayout root = root();
        addComponent(root);

        createEnabled(root);
        createDisabled(root);
        createColor(root);

    }

    private void createEnabled(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Enabled");

        sectionContent
                .addComponent(new PaperCheckbox().setTextContent("Oxygen"));
        sectionContent
                .addComponent(new PaperCheckbox().setTextContent("Carbon"));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setTextContent("Hydrogen"));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setTextContent("Nitrogen"));
        sectionContent.addComponent(
                new PaperCheckbox().setChecked(true).setTextContent("Calcium"));
    }

    private void createDisabled(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Disabled");

        sectionContent.addComponent(
                new PaperCheckbox().setDisabled(true).setTextContent("Oxygen"));
        sectionContent.addComponent(
                new PaperCheckbox().setDisabled(true).setTextContent("Carbon"));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setDisabled(true).setTextContent("Hydrogen"));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setDisabled(true).setTextContent("Nitrogen"));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setDisabled(true).setTextContent("Calcium"));
    }

    private void createColor(CssLayout root) {
        CssLayout sectionContent = createSection(root, "Color");

        sectionContent.addComponent(new PaperCheckbox().withClassName("blue")
                .setTextContent("Oxygen"));
        sectionContent.addComponent(new PaperCheckbox().withClassName("red")
                .setTextContent("Carbon"));
        sectionContent.addComponent(new PaperCheckbox().withClassName("orange")
                .setChecked(true).setTextContent("Hydrogen"));
        sectionContent.addComponent(new PaperCheckbox().withClassName("green")
                .setChecked(true).setTextContent("Nitrogen"));
        sectionContent.addComponent(new PaperCheckbox().withClassName("blue")
                .setChecked(true).setTextContent("Calcium"));
    }
}
