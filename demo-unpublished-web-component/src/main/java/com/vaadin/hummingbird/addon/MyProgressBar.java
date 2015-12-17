package com.vaadin.hummingbird.addon;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.ui.ProgressBar;

@HTML("context://bower_components/vaadin-progressbar/vaadin-progressbar.html")
@Tag("v-progressbar")
public class MyProgressBar extends ProgressBar {

    public MyProgressBar() {
    }

    @Override
    protected void setInternalValue(Float newValue) {
        super.setInternalValue(newValue);
        getElement().setAttribute("value", newValue.intValue());
    }

    @Override
    public Float getValue() {
        return (float) getElement().getAttribute("value", 0);
    }

    public void setMax(int max) {
        getElement().setAttribute("max", max);
    }

    public int getMax() {
        return getElement().getAttribute("max", 100);
    }

    public void setMin(int Min) {
        getElement().setAttribute("min", Min);
    }

    public int getMin() {
        return getElement().getAttribute("min", 0);
    }

}
