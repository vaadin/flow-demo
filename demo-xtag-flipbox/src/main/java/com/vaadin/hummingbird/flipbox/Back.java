package com.vaadin.hummingbird.flipbox;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class Back extends VerticalLayout {

    @Override
    protected void init() {
        super.init();

        Button button = new Button("Front side, plx", e -> {
            ((FlipBox) getParent()).showFront();
        });
        button.getElement().setStyle("background", "black").setStyle("color",
                "white");

        addComponent(button);
    }
}
