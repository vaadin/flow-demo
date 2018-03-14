/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.staticmenu.community.blog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import com.vaadin.flow.shared.Registration;

/**
 * Simple confirmation dialog.
 */
class ConfirmationDialog extends Dialog {

    private final H2 titleField = new H2();
    private final Div messageLabel = new Div();
    private final Button confirmButton = new Button();
    private final Button cancelButton = new Button();
    private Registration registrationForConfirm;

    /**
     * Default constructor.
     */
    public ConfirmationDialog() {
        init();
    }

    private void init() {
        I18NProvider provider = getI18NProvider();

        confirmButton.setText(provider.getTranslation("common.ok"));
        cancelButton.setText(provider.getTranslation("common.cancel"));

        getElement().getClassList().add("confirm-dialog");
        confirmButton.getElement().setAttribute("dialog-confirm", true);
        confirmButton.setAutofocus(true);
        cancelButton.getElement().setAttribute("dialog-dismiss", true);

        HorizontalLayout buttonBar = new HorizontalLayout(confirmButton,
                cancelButton);
        buttonBar.setClassName("buttons");

        Div labels = new Div(messageLabel);
        labels.setClassName("text");

        registrationForConfirm = confirmButton.addClickListener(e -> close());
        cancelButton.addClickListener(e -> close());

        add(titleField, labels, buttonBar);
    }

    /**
     * Opens the confirmation dialog.
     *
     * The dialog will display the given title and message, then call
     * <code>confirmHandler</code> if the Confirm button is clicked, or
     * <code>cancelHandler</code> if the Cancel button is clicked.
     *
     * @param title
     *            The title text
     * @param message
     *            Detail message (optional, may be empty)
     * @param postpone
     *            postpone navigation action
     *
     */
    public void open(String title, String message,
            ContinueNavigationAction postpone) {
        titleField.setText(title);
        messageLabel.setText(message);

        if (registrationForConfirm != null) {
            registrationForConfirm.remove();
        }
        registrationForConfirm = confirmButton
                .addClickListener(e -> postpone.proceed());

        open();
    }

    /**
     * Close dialog.
     */
    public void close() {
        close();
        getElement().removeFromParent();
    }
}
