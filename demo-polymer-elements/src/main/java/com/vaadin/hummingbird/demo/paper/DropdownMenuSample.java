package com.vaadin.hummingbird.demo.paper;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperDropdownMenu;
import com.vaadin.hummingbird.paper.PaperMenu;
import com.vaadin.hummingbird.paper.PaperTabs;
import com.vaadin.ui.CssLayout;

public class DropdownMenuSample extends SampleBase {

    public DropdownMenuSample() {
        addStyleName("dropdown-menu-sample");

        addBasicMenu();

        addPreselectedValue();

        addDisabled();

        addAlternativeContent();

        addNoLabelFloat();

        addNoRippleNoAnimations();
    }

    private void addBasicMenu() {
        CssLayout section = createHorizontalSectionContainer(this,
                "Basic Menu");

        section.addComponent(new PaperDropdownMenu().setLabel("Dinosaurs")
                .with(new PaperMenu().withClassName("dropdown-content")
                        .with(createDinosaurPaperItems())));
    }

    private void addPreselectedValue() {
        CssLayout section = createHorizontalSectionContainer(this,
                "Preselected Value");

        section.addComponent(
                new PaperDropdownMenu().setLabel("Dinosaurs")
                        .with(new PaperMenu().withClassName("dropdown-content")
                                .setSelected("1")
                                .with(createDinosaurPaperItems())));
    }

    private void addDisabled() {
        CssLayout section = createHorizontalSectionContainer(this, "Disabled");

        section.addComponent(
                new PaperDropdownMenu().setLabel("Disabled").setDisabled(true)
                        .with(new PaperMenu().withClassName("dropdown-content")
                                .with(createDinosaurPaperItems())));
    }

    private void addAlternativeContent() {
        CssLayout section = createHorizontalSectionContainer(this,
                "Alternative Content");

        section.addComponent(new PaperDropdownMenu().setLabel("Menu tabs!?")
                .with(new PaperTabs().withClassName("dropdown-content")
                        .with(createLettersPaperTabs())));
    }

    private void addNoLabelFloat() {
        CssLayout section = createHorizontalSectionContainer(this,
                "No Label Float");

        section.addComponent(new PaperDropdownMenu().setLabel("Letters")
                .withClassName("letters").setNoLabelFloat(true)
                .with(new PaperMenu().withClassName("dropdown-content")
                        .with(createLettersPaperItems())));
    }

    private void addNoRippleNoAnimations() {
        CssLayout section = createHorizontalSectionContainer(this,
                "No Ripple, No Animations");

        section.addComponent(new PaperDropdownMenu().setLabel("Dinosaurs")
                .setNoink(true).setNoAnimations(true)
                .with(new PaperMenu().withClassName("dropdown-content")
                        .with(createDinosaurPaperItems())));
    }

}
