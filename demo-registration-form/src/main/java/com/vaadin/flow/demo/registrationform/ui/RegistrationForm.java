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
package com.vaadin.flow.demo.registrationform.ui;

import java.util.Objects;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.components.data.HasValue;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.BindingValidationStatus.Status;
import com.vaadin.data.Validator;
import com.vaadin.flow.html.H2;
import com.vaadin.flow.html.HtmlComponent;
import com.vaadin.flow.html.Label;
import com.vaadin.flow.router.View;
import com.vaadin.generated.paper.dialog.GeneratedPaperDialog;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.HasStyle;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
@StyleSheet("frontend://src/style.css")
public class RegistrationForm extends Composite<VerticalLayout>
implements View {

    private static final String WIDTH = "350px";

    private final Binder<Person> binder = new Binder<>();

    private Binding<Person, String> passwordBinding;
    private Binding<Person, String> confirmPasswordBinding;

    private boolean showConfirmPasswordStatus;

    private static final String VALID = "valid";

    public RegistrationForm() {
        RegistrationTextField fullNameField = new RegistrationTextField();
        addToLayout(fullNameField, "Full name");

        binder.forField(fullNameField).asRequired("Full name may not be empty")
        .withValidationStatusHandler(
                status -> commonStatusChangeHandler(status,
                        fullNameField))
        .bind(Person::getFullName, Person::setFullName);

        RegistrationTextField phoneOrEmailField = new RegistrationTextField();
        addToLayout(phoneOrEmailField, "Phone or Email");
        binder.forField(phoneOrEmailField)
        .withValidator(new EmailOrPhoneValidator())
        .withValidationStatusHandler(
                status -> commonStatusChangeHandler(status,
                        phoneOrEmailField))
        .bind(Person::getEmailOrPhone, Person::setEmailOrPhone);

        RegistrationPasswordField passwordField = new RegistrationPasswordField();
        addToLayout(passwordField, "Password");
        passwordBinding = binder.forField(passwordField)
                .withValidator(new PasswordValidator())
                .withValidationStatusHandler(
                        status -> commonStatusChangeHandler(status,
                                passwordField))
                .bind(Person::getPassword, Person::setPassword);
        passwordField.addValueChangeListener(
                event -> confirmPasswordBinding.validate());

        RegistrationPasswordField confirmPasswordField = new RegistrationPasswordField();
        addToLayout(confirmPasswordField, "Password again");

        confirmPasswordBinding = binder.forField(confirmPasswordField)
                .withValidator(Validator.from(this::validateConfirmPasswd,
                        "Password doesn't match"))
                .withValidationStatusHandler(
                        status -> confirmPasswordStatusChangeHandler(status,
                                confirmPasswordField))
                .bind(Person::getPassword, (person, pwd) -> {
                });

        getContent().add(createButton());

        fullNameField.focus();

        binder.setBean(new Person());
    }

    private void addToLayout(AbstractTextField<?> textField,
            String placeHolderText) {
        textField.setPlaceholder(placeHolderText);
        Label statusMessage = new Label();
        setVisible(statusMessage, false);
        statusMessage.getClassNames().add("validation-message");
        textField.setData(statusMessage);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.add((Component) textField);
        textField.setWidth(WIDTH);
        horizontalLayout.add(statusMessage);
        getContent().add(horizontalLayout);
    }

    private Button createButton() {
        Button button = new Button("Sign Up", event -> save());
        button.getClassNames().add("primary");
        button.setWidth(WIDTH);
        return button;
    }

    private void commonStatusChangeHandler(BindingValidationStatus<?> event,
            AbstractTextField<?> field) {
        Label statusLabel = (Label) field.getData();
        setVisible(statusLabel, !event.getStatus().equals(Status.UNRESOLVED));
        switch (event.getStatus()) {
        case OK:
            statusLabel.setText(" ");
            statusLabel.setClassName("ok");
            statusLabel.removeClassName("error");
            ((HasStyle) statusLabel.getParent().get()).setClassName(VALID);
            break;
        case ERROR:
            statusLabel.removeClassName("ok");
            statusLabel.setClassName("error");
            statusLabel.setText(event.getMessage().orElse("Unknown error"));
            ((HasStyle) statusLabel.getParent().get()).removeClassName(VALID);
        default:
            break;
        }
    }

    private void confirmPasswordStatusChangeHandler(
            BindingValidationStatus<?> event, AbstractTextField<?> field) {
        commonStatusChangeHandler(event, field);
        Label statusLabel = (Label) field.getData();
        setVisible(statusLabel, showConfirmPasswordStatus);
    }

    private boolean validateConfirmPasswd(String confirmPasswordValue) {
        showConfirmPasswordStatus = false;
        if (confirmPasswordValue.isEmpty()) {
            return true;

        }
        BindingValidationStatus<String> status = passwordBinding.validate();
        if (status.isError()) {
            return true;
        }
        showConfirmPasswordStatus = true;
        HasValue<?, ?> pwdField = passwordBinding.getField();
        return Objects.equals(pwdField.getValue(), confirmPasswordValue);
    }

    private void save() {
        Person person = new Person();
        if (binder.writeBeanIfValid(person)) {
            showNotification("Registration data saved successfully",
                    String.format("Full name '%s', email or phone '%s'",
                            person.getFullName(), person.getEmailOrPhone()),
                    false);
        } else {
            showNotification("Error",
                    "Registration could not be saved, please check all fields",
                    true);
        }
    }

    private void showNotification(String title, String message, boolean error) {
        GeneratedPaperDialog dialog = createDialog(title,
                message, error);

        dialog.setEntryAnimation("scale-up-animation");
        dialog.setExitAnimation("scale-down-animation");
        dialog.setWithBackdrop(true);
        getUI().get().add(dialog);
        dialog.open();
    }

    private GeneratedPaperDialog<?> createDialog(String title, String text,
            boolean error) {
        GeneratedPaperDialog dialog = new GeneratedPaperDialog();
        dialog.add(new H2(title));
        HtmlComponent paragraph = new HtmlComponent(Tag.P);
        paragraph.getElement().setText(text);
        if (error) {
            paragraph.getClassNames().add("error");
        }
        dialog.add(paragraph);
        return dialog;
    }

    private static void setVisible(Label label, boolean visible) {
        if (visible) {
            label.getElement().getStyle().remove("display");
        } else {
            label.getElement().getStyle().set("display", "none");
        }
    }
}