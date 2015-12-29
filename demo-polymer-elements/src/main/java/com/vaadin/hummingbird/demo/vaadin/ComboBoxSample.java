package com.vaadin.hummingbird.demo.vaadin;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.vaadin.VaadinComboBox;
import com.vaadin.ui.CssLayout;

public class ComboBoxSample extends SampleBase {

    private static final String[] OPTIONS = { "foo", "bar", "baz" };

    public ComboBoxSample() {
        addBasic();
        addPreselected();
        addDisabled();
        addValueChangeListener();
    }

    private void addBasic() {
        CssLayout section = createHorizontalSection(this, "Basic");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz");

        addOptions(comboBox);

        section.addComponent(comboBox);

        addComponent(section);
    }

    private void addPreselected() {
        CssLayout section = createHorizontalSection(this, "Preselected Value");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz").setValue("baz");

        addOptions(comboBox);

        section.addComponent(comboBox);

        addComponent(section);
    }

    private void addDisabled() {
        CssLayout section = createHorizontalSection(this,
                "Disabled (missing from element)");

        VaadinComboBox comboBox = new VaadinComboBox()
                .setLabel("Foo, bar or baz").setDisabled(true);

        addOptions(comboBox);

        section.addComponent(comboBox);

        addComponent(section);
    }

    private void addValueChangeListener() {
        CssLayout section = createHorizontalSection(this,
                "With ValueChangeListener");

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

        addComponent(section);
    }

    private void addOptions(VaadinComboBox comboBox) {
        comboBox.setItems(OPTIONS);
    }
}
