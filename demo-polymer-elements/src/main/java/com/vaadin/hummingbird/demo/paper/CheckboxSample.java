package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.event.EventListener;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperCheckbox;
import com.vaadin.hummingbird.paper.event.ChangeEvent;
import com.vaadin.ui.CssLayout;

@PolymerStyle("checkbox-styles")
public class CheckboxSample extends SampleBase {

    private final EventListener<ChangeEvent> changeListener = event -> onEvent(
            event);

    public CheckboxSample() {
        addStyleName("checkbox-sample");

        CssLayout root = root();
        addComponent(root);

        createEnabled(root);
        createDisabled(root);
        createColor(root);

    }

    private void createEnabled(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Enabled");

        sectionContent.addComponent(new PaperCheckbox().setTextContent("Oxygen")
                .addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().setTextContent("Carbon")
                .addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setTextContent("Hydrogen").addChangeListener(changeListener));

        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setTextContent("Nitrogen").addChangeListener(changeListener));

        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setTextContent("Calcium").addChangeListener(changeListener));

    }

    private void createDisabled(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Disabled");

        sectionContent.addComponent(new PaperCheckbox().setDisabled(true)
                .setTextContent("Oxygen").addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().setDisabled(true)
                .setTextContent("Carbon").addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setDisabled(true).setTextContent("Hydrogen")
                .addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setDisabled(true).setTextContent("Nitrogen")
                .addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().setChecked(true)
                .setDisabled(true).setTextContent("Calcium")
                .addChangeListener(changeListener));
    }

    private void createColor(CssLayout root) {
        CssLayout sectionContent = createHorizontalSection(root, "Color");

        sectionContent.addComponent(new PaperCheckbox().withClassName("blue")
                .setTextContent("Oxygen").addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().withClassName("red")
                .setTextContent("Carbon").addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().withClassName("orange")
                .setChecked(true).setTextContent("Hydrogen")
                .addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().withClassName("green")
                .setChecked(true).setTextContent("Nitrogen")
                .addChangeListener(changeListener));
        sectionContent.addComponent(new PaperCheckbox().withClassName("blue")
                .setChecked(true).setTextContent("Calcium")
                .addChangeListener(changeListener));
    }

}
