package com.vaadin.hummingbird.demo.paper;

import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperInput;
import com.vaadin.hummingbird.paper.PaperTextarea;
import com.vaadin.ui.CssLayout;

public class InputSample extends SampleBase {

    public InputSample() {
        addStyleName("input sample");
        CssLayout root = layout("vertical center-justified layout");
        addComponent(root);

        addTextInput(root);
        addTextArea(root);
        addValidation(root);
        addCharacterCounter(root);
    }

    private void addTextInput(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Text input");

        section.addComponents(new PaperInput().setLabel("label"),
                new PaperInput().setLabel("password").setType("password"),
                new PaperInput().setLabel("label (no-label-float)")
                        .setNoLabelFloat(true),
                new PaperInput().setLabel("disabled").setDisabled(true));
    }

    private void addTextArea(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Text area");

        section.addComponent(new PaperTextarea().setLabel("textarea label"));
    }

    private void addValidation(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Validation");

        PaperInput lazyValidatedInput = new PaperInput()
                .setLabel("only type letters (no auto validate)")
                .setRequired(true).setPattern("[a-zA-Z]*")
                .setErrorMessagePolymer("letters only, required input!");
        section.addComponents(
                new PaperInput().setLabel("only type letters (auto-validate)")
                        .setAutoValidate(true).setPattern("[a-zA-Z]*")
                        .setErrorMessagePolymer("letters only!"),
                lazyValidatedInput,
                new PaperButton().setTextContent("Validate").setElevation("4")
                        .withClickListener(e -> lazyValidatedInput.validate()));
    }

    private void addCharacterCounter(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Character counter");

        section.addComponents(
                new PaperInput().setLabel("label").setCharCounter(true),
                new PaperInput().setLabel("at most 10 letters")
                        .setCharCounter(true).setAutoValidate(true)
                        .setPattern("[a-zA-Z]*").setMaxlength(10)
                        .setErrorMessagePolymer("letters only!"),
                new PaperTextarea().setLabel("textarea").setCharCounter(true),
                new PaperTextarea().setLabel("textarea with maxlength")
                        .setCharCounter(true).setMaxlength(10));

    }
}
