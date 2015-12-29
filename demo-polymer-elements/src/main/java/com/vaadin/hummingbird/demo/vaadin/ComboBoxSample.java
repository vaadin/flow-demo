package com.vaadin.hummingbird.demo.vaadin;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.vaadin.VaadinComboBox;
import com.vaadin.ui.CssLayout;

public class ComboBoxSample extends SampleBase {

    private static final String[] OPTIONS = { "foo", "bar", "baz" };

    public ComboBoxSample() {
        CssLayout root = root();
        addComponent(root);

        addBasic(root);
        addPreselected(root);
        addDisabled(root);
        addValueChangeListener(root);
    }

    private void addBasic(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Basic");
        section.setWidth("250px");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz");

        addOptions(comboBox);

        section.addComponent(comboBox);
    }

    private void addPreselected(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Preselected Value");
        section.setWidth("250px");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz").setValue("baz");

        addOptions(comboBox);

        section.addComponent(comboBox);
    }

    private void addDisabled(CssLayout root) {
        CssLayout section = createHorizontalSection(root,
                "Disabled (missing from element)");
        section.setWidth("250px");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz").setDisabled(true);

        addOptions(comboBox);

        section.addComponent(comboBox);
    }

    private void addValueChangeListener(CssLayout root) {
        CssLayout section = createHorizontalSection(root,
                "With ValueChangeListener");
        section.setWidth("250px");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz");

        addOptions(comboBox);

        comboBox.getElement().addEventData("value-changed", "value");
        comboBox.getElement().addEventListener("value-changed",
                e -> section.addComponent(new PaperDialog()
                        .with(new PaperItem().setTextContent(
                                "Value Changed: " + e.get("value").asString()))
                        .open()));

        section.addComponent(comboBox);
    }

    private void addOptions(VaadinComboBox comboBox) {
        comboBox.setItems(OPTIONS);
    }
}
