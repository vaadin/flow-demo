package com.vaadin.hummingbird.demo.paper;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperMaterial;
import com.vaadin.hummingbird.polymer.ClickEvent;
import com.vaadin.ui.CssLayout;

public class MaterialSample extends SampleBase {

    public MaterialSample() {
        addStyleName("collapse-sample");

        CssLayout section1 = new CssLayout();
        section1.addComponents(element("div", "Paper Elevations", false),
                new PaperMaterial().setElevation(0)
                        .setTextContent("elevation = 0"),
                new PaperMaterial().setElevation(1)
                        .setTextContent("elevation = 1"),
                new PaperMaterial().setElevation(2)
                        .setTextContent("elevation = 2"),
                new PaperMaterial().setElevation(3)
                        .setTextContent("elevation = 3"),
                new PaperMaterial().setElevation(4)
                        .setTextContent("elevation = 4"),
                new PaperMaterial().setElevation(5)
                        .setTextContent("elevation = 5"));

        CssLayout section2 = new CssLayout();
        section2.addComponents(element("div", "Animated", false),
                new PaperMaterial().setElevation(0).setAnimated(true)
                        .setTextContent("tap").withClickListener(e -> onTap(e)),
                new PaperMaterial().setElevation(0).setAnimated(true)
                        .withClassName("fab layout center-center")
                        .setTextContent("tap")
                        .withClickListener(e -> onTap(e)));

        addComponents(section1, section2);
    }

    private void onTap(ClickEvent<PaperMaterial> event) {
        PaperMaterial paperMaterial = event.getPolymerComponent();
        if (paperMaterial.getElevation() == 0) {
            paperMaterial.setElevation(5);
        } else {
            paperMaterial.setElevation(0);
        }
    }
}
