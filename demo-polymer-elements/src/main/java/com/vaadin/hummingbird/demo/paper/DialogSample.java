package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperDialogScrollable;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

@PolymerStyle("dialog-styles")
public class DialogSample extends SampleBase {

    private static final String loremIpsum = "Lorem ipsum dolor sit amet,"
            + " consectetur adipisicing elit, sed do eiusmod tempor incididunt ut"
            + " labore et dolore magna aliqua. Ut enim ad minim veniam, quis"
            + " nostrud exercitation ullamco laboris nisi ut aliquip ex ea"
            + " commodo consequat. Duis aute irure dolor in reprehenderit in"
            + " voluptate velit esse cillum dolore eu fugiat nulla pariatur."
            + " Excepteur sint occaecat cupidatat non proident, sunt in culpa qui"
            + " officia deserunt mollit anim id est laborum.";

    public DialogSample() {
        addStyleName("dialog-sample");

        addDialogLayouts();

        addCustomStyling();

        addTransitions();
    }

    private void addDialogLayouts() {
        HTML header = element("h4", "Dialog layouts", false);
        CssLayout section = layout("horizontal-section");

        PaperDialog plainDialog = new PaperDialog()
                .with(element("h2", "", "Dialog title"), loremIpsum());

        PaperDialog scrollingDlg = new PaperDialog();
        scrollingDlg.with(element("h4", "", "Scrolling"),
                new PaperDialogScrollable().with(loremIpsum(), loremIpsum(),
                        loremIpsum(), loremIpsum(), loremIpsum(), loremIpsum(),
                        loremIpsum(), loremIpsum(), loremIpsum()),
                layout("buttons",
                        new PaperButton()
                                .setBooleanAttribute("dialog-dismiss", true)
                                .setTextContent("Cancel").withClickListener(
                                        e -> scrollingDlg.setOpened(false)),
                        new PaperButton()
                                .setBooleanAttribute("dialog-dismiss", true)
                                .setBooleanAttribute("autofocus", true)
                                .setTextContent("OK").withClickListener(
                                        e -> scrollingDlg.setOpened(false))));

        PaperDialog actionsDialog = new PaperDialog();
        actionsDialog.with(element("h2", "", "Dialog Title"), loremIpsum(),
                layout("buttons",
                        new PaperButton().setTextContent("More Info..."),
                        new PaperButton()
                                .setBooleanAttribute("dialog-dismiss", true)
                                .setTextContent("DECLINE").withClickListener(
                                        e -> actionsDialog.setOpened(false)),
                        new PaperButton()
                                .setBooleanAttribute("dialog-dismiss", true)
                                .setBooleanAttribute("autofocus", true)
                                .setTextContent("ACCEPT").withClickListener(
                                        e -> actionsDialog.setOpened(false))));

        PaperDialog modalDialog = new PaperDialog();
        modalDialog.setModal(true)
                .with(loremIpsum(),
                        layout("buttons",
                                new PaperButton()
                                        .setBooleanAttribute("dialog-dismiss",
                                                true)
                                        .setBooleanAttribute("autofocus", true)
                                        .setTextContent(
                                                "Tap me to close")
                        .withClickListener(e -> modalDialog.setOpened(false))));

        section.addComponents(
                new PaperButton().setTextContent("Plain Dialog")
                        .withClassName("emphasised")
                        .withClickListener(e -> plainDialog.setOpened(true)),
                new PaperButton().setTextContent("Scrolling Dialog")
                        .withClassName("emphasised")
                        .withClickListener(e -> scrollingDlg.setOpened(true)),
                new PaperButton().setTextContent("Dialog with actions")
                        .withClassName("emphasised")
                        .withClickListener(e -> actionsDialog.setOpened(true)),
                new PaperButton().setTextContent("Modal dialog")
                        .withClassName("emphasised")
                        .withClickListener(e -> modalDialog.setOpened(true)),
                plainDialog, scrollingDlg, actionsDialog, modalDialog);

        addComponents(header, section);
    }

    private void addCustomStyling() {
        HTML header = element("h4", "Custom styling", false);
        CssLayout section = layout("horizontal-section");

        PaperDialog colorsDialog = new PaperDialog();
        colorsDialog.withClassName("colored").with(loremIpsum())
                .addIronOverlayClosedListener(
                        e -> colorsDialog.setOpened(false));

        PaperDialog positionDialog = new PaperDialog();
        positionDialog.withClassName("size-position").with(loremIpsum())
                .addIronOverlayClosedListener(
                        e -> positionDialog.setOpened(false));

        section.addComponents(
                new PaperButton().withClassName("emphasised")
                        .setTextContent("colors")
                        .withClickListener(e -> colorsDialog.setOpened(true)),
                new PaperButton().withClassName("emphasised")
                        .setTextContent("size & position")
                        .withClickListener(e -> positionDialog.setOpened(true)),
                colorsDialog, positionDialog);

        addComponents(header, section);
    }

    private void addTransitions() {
        HTML header = element("h4", "Transitions", false);
        CssLayout section = layout("horizontal-section");

        PaperDialog paperDialog = new PaperDialog();
        paperDialog.setWithBackdrop(true)
                .setEntryAnimation("scale-up-animation")
                .setExitAnimation("fade-out-animation")
                .with(element("h2", "Dialog Title", false), loremIpsum())
                .addIronOverlayClosedListener(
                        e -> paperDialog.setOpened(false));

        section.addComponents(new PaperButton().withClassName("emphasised")
                .setTextContent("Transitions").withClickListener(
                        e -> paperDialog.setOpened(true)),
                paperDialog);

        addComponents(header, section);
    }

    private HTML loremIpsum() {
        return element("p", loremIpsum, false);
    }
}
