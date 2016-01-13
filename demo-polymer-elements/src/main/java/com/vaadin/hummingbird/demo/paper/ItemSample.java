package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.iron.IronIcon;
import com.vaadin.hummingbird.paper.PaperCheckbox;
import com.vaadin.hummingbird.paper.PaperIconItem;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperItemBody;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

@com.vaadin.annotations.HTML("context://bower_components/iron-icons/communication-icons.html")
@PolymerStyle("item-styles")
public class ItemSample extends SampleBase {

    public ItemSample() {
        CssLayout root = layout("layout wrap inline center-center");
        addComponent(root);
        addStyleName("item-sample");

        addSingleLineItems(root);
        addIconWithText(root);
        addAvatarWithText(root);
        addAvatarWithTextAndIcon(root);
        addAvatarWithTextAndControl(root);
        addControlWithTextAndIcon(root);
        addTwoLineItems(root);
        addIconWithTwoLineText(root);
        addAvatarWithTextAndIconColor(root);
    }

    private void addSingleLineItems(CssLayout root) {
        CssLayout section = createListShort(root, "Single line items");

        section.addComponents(new PaperItem().setTextContent("Inbox"),
                new PaperItem().setTextContent("Starred"),
                new PaperItem().setTextContent("Sent mail"),
                new PaperItem().setTextContent("Drafts"));
    }

    private void addIconWithText(CssLayout root) {
        CssLayout section = createListShort(root, "Icon with text");

        section.addComponents(
                new PaperIconItem()
                        .with(new IronIcon().setIconPolymer("inbox")
                                .setAttributes("item-icon"))
                        .appendTextContent("Inbox"),
                new PaperIconItem().with(new IronIcon().setIconPolymer("send")
                        .setAttributes("item-icon"))
                        .appendTextContent("Outbox"),
                new PaperIconItem()
                        .with(new IronIcon().setIconPolymer("delete")
                                .setAttributes("item-icon"))
                        .appendTextContent("Trash"),
                new PaperIconItem()
                        .with(new IronIcon().setIconPolymer("report")
                                .setAttributes("item-icon"))
                        .appendTextContent("Spam"));
    }

    private void addAvatarWithText(CssLayout root) {
        CssLayout section = createListShort(root, "Avatar with text");

        section.addComponents(
                new PaperIconItem().with(divItemIcon("avatar blue"))
                        .appendTextContent("Alphonso"),
                new PaperIconItem().with(divItemIcon("avatar red"))
                        .appendTextContent("Andrews"),
                new PaperIconItem().with(divItemIcon("avatar orange"))
                        .appendTextContent("Angela"),
                new PaperIconItem().with(divItemIcon("avatar green"))
                        .appendTextContent("Lorem"));
    }

    private void addAvatarWithTextAndIcon(CssLayout root) {
        CssLayout section = createListShort(root, "Avatar with text and icon");

        section.addComponents(
                new PaperIconItem().with(divItemIcon("avatar red"),
                        divFlex("Alphonso"),
                        new IronIcon().setIconPolymer("communication:chat")),
                new PaperIconItem().with(divItemIcon("avatar orange"),
                        divFlex("Andrews"),
                        new IronIcon().setIconPolymer("communication:chat")),
                new PaperIconItem().with(divItemIcon("avatar green"),
                        divFlex("Angela"),
                        new IronIcon().setIconPolymer("communication:chat")),
                new PaperIconItem().with(divItemIcon("avatar blue"),
                        divFlex("Lorem"),
                        new IronIcon().setIconPolymer("communication:chat")));
    }

    private void addAvatarWithTextAndControl(CssLayout root) {
        CssLayout section = createListShort(root,
                "Avatar with text and control");

        section.addComponents(
                new PaperIconItem().with(divItemIcon("avatar red"),
                        divFlex("Alphonso"), new PaperCheckbox()),
                new PaperIconItem().with(divItemIcon("avatar orange"),
                        divFlex("Andrews"),
                        new PaperCheckbox().setChecked(true)),
                new PaperIconItem().with(divItemIcon("avatar green"),
                        divFlex("Angela"), new PaperCheckbox()),
                new PaperIconItem().with(divItemIcon("avatar blue"),
                        divFlex("Lorem"), new PaperCheckbox()));
    }

    private void addControlWithTextAndIcon(CssLayout root) {
        CssLayout section = createListShort(root, "Control with text and icon");

        section.addComponents(
                new PaperIconItem().with(
                        new PaperCheckbox().setBooleanAttribute("item-icon",
                                true),
                        divFlex("Alphonso"),
                        new IronIcon().setIconPolymer("communication:chat")),
                new PaperIconItem().with(
                        new PaperCheckbox()
                                .setBooleanAttribute("item-icon", true)
                                .setChecked(true),
                        divFlex("Andrews"),
                        new IronIcon().setIconPolymer("communication:chat")),
                new PaperIconItem().with(
                        new PaperCheckbox().setBooleanAttribute("item-icon",
                                true),
                        divFlex("Angela"),
                        new IronIcon().setIconPolymer("communication:chat")),
                new PaperIconItem().with(
                        new PaperCheckbox().setBooleanAttribute("item-icon",
                                true),
                        divFlex("Lorem"),
                        new IronIcon().setIconPolymer("communication:chat")));
    }

    private void addTwoLineItems(CssLayout root) {
        CssLayout section = createList(root, "Two-line items");

        section.addComponents(new PaperItem().with(new PaperItemBody()
                .setBooleanAttribute("two-line", true)
                .withClassName("layout vertical")
                .with(element("div", "Profile Photo", false),
                        secondary("Change your Google+ profile photo"))),

                new PaperItem().with(new PaperItemBody()
                        .setBooleanAttribute("two-line", true)
                        .with(element("div", "Show your status", false),
                                secondary(
                                        "Your status is visible to everyone you use with"))),
                new PaperItem().with(new PaperItemBody()
                        .setBooleanAttribute("two-line", true)
                        .withClassName("layout vertical")
                        .with(element("div", "Settings", false),
                                secondary("Change your account settings"))));
    }

    private void addIconWithTwoLineText(CssLayout root) {
        CssLayout section = createList(root, "Icon with two-line text");

        section.addComponents(new PaperIconItem().with(
                divItemIcon("avatar green"),
                new PaperItemBody().setBooleanAttribute("two-line", true).with(
                        element("div", "Alphonso Engelking", false),
                        secondary("Change photo"))),

                new PaperIconItem().with(
                        new IronIcon().setIconPolymer("communication:phone")
                                .setBooleanAttribute("item-icon", true),
                        new PaperItemBody()
                                .setBooleanAttribute("two-line", true)
                                .with(element("div", "(650) 555-1234", false),
                                        secondary("Mobile"))),
                new PaperIconItem().with(
                        new IronIcon().setIconPolymer("communication:email")
                                .setBooleanAttribute("item-icon", true),
                        new PaperItemBody()
                                .setBooleanAttribute("two-line", true).with(
                                        element("div", "alphonso@example.com",
                                                false),
                                                secondary("Personal"))));
    }

    private void addAvatarWithTextAndIconColor(CssLayout root) {
        CssLayout section = createList(root, "Icon with two-line text");

        section.addComponents(new PaperIconItem().with(
                divItemIcon("avatar blue"),
                new PaperItemBody().setBooleanAttribute("two-line", true).with(
                        element("div", "Photos", false),
                        secondary("Jan 9, 2014")),
                new IronIcon().setIconPolymer("star")),

                new PaperIconItem().with(divItemIcon("avatar red"),
                        new PaperItemBody()
                                .setBooleanAttribute("two-line", true)
                                .with(element("div", "Recipes", false),
                                        secondary("Jan 17, 2014")),
                        new IronIcon().setIconPolymer("star")),
                new PaperIconItem().with(divItemIcon("avatar orange"),
                        new PaperItemBody()
                                .setBooleanAttribute("two-line", true)
                                .with(element("div", "Work", false),
                                        secondary("Jan 28, 2014")),
                        new IronIcon().setIconPolymer("star"))

        );
    }

    private Component secondary(String innerText) {
        HTML div = element("div", innerText, false);
        div.getElement().setAttribute("attr.secondary", "");
        return div;
    }

    private Component divItemIcon(String classNames) {
        HTML div = element("div", classNames);
        div.getElement().setAttribute("attr.item-icon", "");
        return div;
    }

    private Component divFlex(String innerText) {
        HTML div = element("div", "flex", innerText);
        return div;
    }
}
