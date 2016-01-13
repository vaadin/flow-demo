package com.vaadin.hummingbird.demo.paper;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperMenu;
import com.vaadin.ui.CssLayout;

public class MenuSample extends SampleBase {

    public MenuSample() {
        CssLayout root = root();
        addComponent(root);
        addStyleName("menu-sample");

        addStandard(root);
        addPreselected(root);
        addMultiselect(root);
    }

    private void addStandard(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Standard");

        section.addComponents(
                new PaperMenu().with(new PaperItem().setTextContent("Inbox"),
                        new PaperItem().setTextContent("Starred"),
                        new PaperItem().setTextContent("Sent mail"),
                        new PaperItem().setTextContent("Drafts")));
    }

    private void addPreselected(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Pre-selected");

        section.addComponents(new PaperMenu().setSelected("0")
                .with(new PaperItem().setTextContent("Inbox"),
                        new PaperItem().setTextContent("Starred")
                                .setDisabled(true),
                new PaperItem().setTextContent("Sent mail"),
                new PaperItem().setTextContent("Drafts")));
    }

    private void addMultiselect(CssLayout root) {
        CssLayout section = createHorizontalSection(root, "Multi-select");

        section.addComponents(new PaperMenu().setMulti(true).with(
                new PaperItem().setTextContent("Bold"),
                new PaperItem().setTextContent("Italic"),
                new PaperItem().setTextContent("Underline"),
                new PaperItem().setTextContent("Strikethrough")));
    }
}
