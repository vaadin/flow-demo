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

import com.vaadin.annotations.StyleSheet;
import com.vaadin.components.data.HasValue;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderInstanceFieldTest.AbstractTextField;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.flow.router.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Composite;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
@StyleSheet("frontend://src/style.css")
public class RegistrationForm extends Composite<FormLayout> implements View {

    private static final int WIDTH = 350;

    private final Binder<Person> binder = new Binder<>();

    private Binding<Person, String> passwordBinding;
    private Binding<Person, String> confirmPasswordBinding;

    private boolean showConfirmPasswordStatus;

    private static final String VALID = "valid";

    private void addToLayout(Layout layout, AbstractTextField textField,
            String placeHolderText) {
        textField.setPlaceholder(placeHolderText);
        Label statusMessage = new Label();
        statusMessage.setVisible(false);
        statusMessage.addStyleName("validation-message");
        textField.setData(statusMessage);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.addComponent(textField);
        textField.setWidth(WIDTH, Unit.PIXELS);
        horizontalLayout.addComponent(statusMessage);
        layout.addComponent(horizontalLayout);
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100, Unit.PERCENTAGE);
        setContent(layout);

        TextField fullNameField = new TextField();
        addToLayout(layout, fullNameField, "Full name");

        binder.forField(fullNameField).asRequired("Full name may not be empty")
        .withValidationStatusHandler(
                status -> commonStatusChangeHandler(status,
                        fullNameField))
        .bind(Person::getFullName, Person::setFullName);

        TextField phoneOrEmailField = new TextField();
        addToLayout(layout, phoneOrEmailField, "Phone or Email");
        binder.forField(phoneOrEmailField)
        .withValidator(new EmailOrPhoneValidator())
        .withValidationStatusHandler(
                status -> commonStatusChangeHandler(status,
                        phoneOrEmailField))
        .bind(Person::getEmailOrPhone, Person::setEmailOrPhone);

        PasswordField passwordField = new PasswordField();
        addToLayout(layout, passwordField, "Password");
        passwordBinding = binder.forField(passwordField)
                .withValidator(new PasswordValidator())
                .withValidationStatusHandler(
                        status -> commonStatusChangeHandler(status,
                                passwordField))
                .bind(Person::getPassword, Person::setPassword);
        passwordField.addValueChangeListener(
                event -> confirmPasswordBinding.validate());

        PasswordField confirmPasswordField = new PasswordField();
        addToLayout(layout, confirmPasswordField, "Password again");

        confirmPasswordBinding = binder.forField(confirmPasswordField)
                .withValidator(Validator.from(this::validateConfirmPasswd,
                        "Password doesn't match"))
                .withValidationStatusHandler(
                        status -> confirmPasswordStatusChangeHandler(status,
                                confirmPasswordField))
                .bind(Person::getPassword, (person, pwd) -> {
                });

        layout.addComponent(createButton());

        fullNameField.focus();

        binder.setBean(new Person());
    }

    private Button createButton() {
        Button button = new Button("Sign Up", event -> save());
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.setWidth(WIDTH, Unit.PIXELS);
        return button;
    }

    private void commonStatusChangeHandler(BindingValidationStatus<?> event,
            AbstractTextField field) {
        Label statusLabel = (Label) field.getData();
        statusLabel.setVisible(!event.getStatus().equals(Status.UNRESOLVED));
        switch (event.getStatus()) {
        case OK:
            statusLabel.setValue("");
            statusLabel.setIcon(VaadinIcons.CHECK);
            statusLabel.getParent().addStyleName(VALID);
            break;
        case ERROR:
            statusLabel.setIcon(VaadinIcons.CLOSE);
            statusLabel.setValue(event.getMessage().orElse("Unknown error"));
            statusLabel.getParent().removeStyleName(VALID);
        default:
            break;
        }
    }

    private void confirmPasswordStatusChangeHandler(
            BindingValidationStatus<?> event, AbstractTextField field) {
        commonStatusChangeHandler(event, field);
        Label statusLabel = (Label) field.getData();
        statusLabel.setVisible(showConfirmPasswordStatus);
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
        HasValue<?> pwdField = passwordBinding.getField();
        return Objects.equals(pwdField.getValue(), confirmPasswordValue);
    }

    private void save() {
        Person person = new Person();
        if (binder.writeBeanIfValid(person)) {
            Notification.show("Registration data saved successfully",
                    String.format("Full name '%s', email or phone '%s'",
                            person.getFullName(), person.getEmailOrPhone()),
                    Type.HUMANIZED_MESSAGE);
        } else {
            Notification.show(
                    "Registration could not be saved, please check all fields",
                    Type.ERROR_MESSAGE);
        }
    }
}