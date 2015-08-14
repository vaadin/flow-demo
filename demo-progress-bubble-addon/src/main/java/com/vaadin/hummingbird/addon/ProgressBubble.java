package com.vaadin.hummingbird.addon;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.AbstractComponent;

@HTML({ "vaadin://bower_components/polymer/polymer-mini.html",
        "vaadin://bower_components/progress-bubble/progress-bubble.html" })
@Tag("progress-bubble")
public class ProgressBubble extends AbstractComponent {

    private Element textElement;

    public ProgressBubble() {
        textElement = Element.createText("");
        getElement().appendChild(new Element("span")).appendChild(textElement);
    }

    public void setValue(int value) {
        if (value > getMax()) {
            value = getMax();
        }
        getElement().setAttribute("value", value + "");
        textElement.setAttribute("content", value + " %");
    }

    public int getValue() {
        if (getElement().hasAttribute("value")) {
            return Integer.parseInt(getElement().getAttribute("value"));
        } else {
            return 0;
        }
    }

    public void setMax(int max) {
        getElement().setAttribute("max", max + "");
    }

    public int getMax() {
        if (getElement().hasAttribute("max")) {
            return Integer.parseInt(getElement().getAttribute("max"));
        } else {
            return 100;
        }
    }

}
