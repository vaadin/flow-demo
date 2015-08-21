package com.vaadin.hummingbird.addon;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.ui.AbstractComponent;

@HTML("vaadin://bower_components/progress-bubble/progress-bubble.html")
@Tag("progress-bubble")
public class ProgressBubble extends AbstractComponent {

    public ProgressBubble() {
    }

    public void setValue(int value) {
        if (value > getMax()) {
            value = getMax();
        }
        getElement().setAttribute("value", value);
        getElement().setTextContent(value + " %");
    }

    public int getValue() {
        return getElement().getAttribute("value", 0);
    }

    public void setMax(int max) {
        getElement().setAttribute("max", max);
    }

    public int getMax() {
        return getElement().getAttribute("max", 100);
    }

}
