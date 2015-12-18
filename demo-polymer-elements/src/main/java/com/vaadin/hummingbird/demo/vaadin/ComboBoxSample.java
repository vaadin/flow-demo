package com.vaadin.hummingbird.demo.vaadin;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.vaadin.VaadinComboBox;
import com.vaadin.ui.CssLayout;

public class ComboBoxSample extends SampleBase {

    public ComboBoxSample() {

        CssLayout basicSection = createHorizontalSection(this, "Basic");

        VaadinComboBox cb1 = new VaadinComboBox().setLabel("Foo, bar or baz");

        basicSection.addComponent(cb1);

        addComponent(basicSection);
    }
}
