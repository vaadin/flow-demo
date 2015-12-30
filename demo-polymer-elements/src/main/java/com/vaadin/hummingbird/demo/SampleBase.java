package com.vaadin.hummingbird.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperTab;
import com.vaadin.hummingbird.polymer.PolymerComponent;
import com.vaadin.hummingbird.polymer.PolymerComponentEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

public class SampleBase extends CssLayout {

    private static String[] letters = { "alpha", "beta", "gamma", "delta",
            "epsilon" };

    private static String[] dinosaurs = { "allosaurus", "brontosaurus",
            "carcharodontosaurus", "diplodocus", "ekrixinatosaurus",
            "fukuiraptor", "gallimimus", "hadrosaurus", "iguanodon",
            "jainosaurus", "kritosaurus", "liaoceratops", "megalosaurus",
            "nemegtosaurus", "ornithomimus", "protoceratops", "quetecsaurus",
            "rajasaurus", "stegosaurus", "triceratops", "utahraptor",
            "vulcanodon", "wannanosaurus", "xenoceratops", "yandusaurus",
            "zephyrosaurus" };

    private int eventCounter;

    protected void onEvent(PolymerComponentEvent event) {
        StringBuilder stringBuilder = new StringBuilder(
                "Event " + eventCounter++ + ": ")
                        .append(event.getClass().getSimpleName())
                        .append(", Source: ")
                        .append(event.getSource().getClass().getSimpleName())
                        .append(", Text Content: ")
                        .append(event.getPolymerComponent().getTextContent());
        final PaperDialog dialog = new PaperDialog();
        dialog.setEntryAnimation("scale-up-animation")
                .setExitAnimation("fade-out-animation")
                .with(new PaperButton().setTextContent(stringBuilder.toString())
                        .withClickListener(e -> dialog.setOpened(false)))
                .setOpened(true);
        addComponent(dialog);
    }

    protected CssLayout createHorizontalSectionContainer(
            CssLayout sectionContainerParent, String name) {
        CssLayout layout = layout("horizontal-section-container");
        sectionContainerParent.addComponent(layout);
        return createHorizontalSection(layout, name);
    }

    protected CssLayout createHorizontalSection(CssLayout sectionParent,
            String name) {
        CssLayout sectionRoot = layout(null);
        HTML header = element("h4", name, false);
        CssLayout sectionContent = layout("horizontal-section");
        sectionRoot.addComponents(header, sectionContent);
        sectionParent.addComponent(sectionRoot);
        return sectionContent;
    }

    protected CssLayout createVerticalSection(CssLayout sectionParent,
            String name) {
        CssLayout sectionRoot = layout(null);
        HTML header = element("h4", name, false);
        CssLayout sectionContent = layout("vertical-section");
        sectionRoot.addComponents(header, sectionContent);
        sectionParent.addComponent(sectionRoot);
        return sectionContent;
    }

    protected CssLayout createList(CssLayout sectionParent, String name) {
        CssLayout sectionRoot = layout(null);
        HTML header = element("h4", name, false);
        CssLayout sectionContent = layout("list");
        sectionRoot.addComponents(header, sectionContent);
        sectionParent.addComponent(sectionRoot);
        return sectionContent;
    }

    protected CssLayout createListShort(CssLayout sectionParent, String name) {
        CssLayout list = createList(sectionParent, name);
        list.addStyleName("short");
        return list;
    }

    protected HTML style(String styling) {
        HTML style = new HTML("<style></style>");
        style.setInnerHtml(styling);
        return style;
    }

    protected CssLayout root() {
        return layout("horizontal-section-container center-justified layout");
    }

    protected CssLayout layout(String classNames) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName(classNames);
        return cssLayout;
    }

    protected CssLayout layout(String classNames, Component... components) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName(classNames);
        cssLayout.addComponents(components);
        return cssLayout;
    }

    protected HTML element(String tag) {
        return new HTML(new StringBuilder("<").append(tag).append("></")
                .append(tag).append(">").toString());
    }

    protected HTML element(String tag, String classNames) {
        return new HTML(new StringBuilder("<").append(tag).append(" class=\"")
                .append(classNames).append("\"></").append(tag).append(">")
                .toString());
    }

    protected HTML element(String tag, String classNames, String innerText) {
        return new HTML(new StringBuilder("<").append(tag).append(" class=\"")
                .append(classNames).append("\">").append(innerText).append("</")
                .append(tag).append(">").toString());
    }

    protected HTML element(String tag, String innerText, boolean b) {
        return new HTML(
                new StringBuilder("<").append(tag).append(">").append(innerText)
                        .append("</").append(tag).append(">").toString());
    }

    protected PaperItem[] createDinosaurPaperItems() {
        return createOptions(dinosaurs, PaperItem.class)
                .toArray(new PaperItem[] {});
    }

    protected PaperItem[] createLettersPaperItems() {
        return createOptions(letters, PaperItem.class)
                .toArray(new PaperItem[] {});
    }

    protected PaperTab[] createLettersPaperTabs() {
        return createOptions(letters, PaperTab.class)
                .toArray(new PaperTab[] {});
    }

    private <T extends PolymerComponent<T>> Collection<T> createOptions(
            String[] options, Class<T> type) {
        ArrayList<T> paperItems = new ArrayList<>();
        Arrays.stream(options).forEach(option -> {
            T component;
            try {
                component = type.newInstance();
                component.setTextContent(option);
                paperItems.add(component);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return paperItems;
    }
}
