/*
 * Copyright 2000-2019 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.flow.demo.dnd.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropEvent;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.demo.dnd.MainLayout;
import com.vaadin.flow.router.Route;

@Route(value = "components", layout = MainLayout.class)
public class Components extends SplitLayout {

    private Component first;
    private Component second;

    public Components() {
        initLayouts(Div.class);
        initComponents();
    }

    private void initComponents() {
        Accordion accordion = new Accordion();
        accordion.add("Accordion", new Text("FOOBAR"));
        Button button = new Button("Button");
        Checkbox checkBox = new Checkbox("CheckBox");
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems("foo", "bar", "baz");
        checkboxGroup.setLabel("CheckboxGroup");
        ComboBox<String> comboBox = new ComboBox<>("ComboBox");
        comboBox.setItems("foo", "bar", "baz");
        DatePicker datePicker = new DatePicker("DatePicker");
        Details details = new Details();
        details.setSummaryText("Details");
        details.setContent(new Text("FOOBAR"));
        Icon icon = VaadinIcon.ABACUS.create();
        EmailField emailField = new EmailField("Email");
        ListBox<String> listBox = new ListBox<>();
        listBox.setItems("foo", "bar", "baz");
        NumberField numberField = new NumberField("NumberField");
        PasswordField passwordField = new PasswordField("Password");
        ProgressBar progressBar = new ProgressBar();
        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setLabel("RadioButtonGroup");
        radioButtonGroup.setItems("foo", "bar", "baz");
        Select<String> select = new Select<>("foo", "bar", "baz");
        select.setLabel("Select");
        TextField textField = new TextField("TextField");
        TextArea textArea = new TextArea("TextArea");
        TimePicker timePicker = new TimePicker("TimePicker");
        Upload upload = new Upload();

        ((HasComponents) first).add(accordion, button, checkBox, checkboxGroup,
                comboBox, datePicker, details, icon, emailField, listBox,
                numberField, passwordField, progressBar, radioButtonGroup,
                select, textField, textArea, timePicker, upload);

        first.getChildren().forEach(DragSource::create);
    }

    private void initLayouts(Class<? extends Component> clazz) {
        try {
            first = clazz.newInstance();
            second = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        DropTarget.create(first).addDropListener(this::onDrop);
        DropTarget.create(second).addDropListener(this::onDrop);
        addToPrimary(first);
        addToSecondary(second);
        setSecondaryStyle("background","lightgreen");
    }

    private void onDrop(DropEvent<Component> componentDropEvent) {
        Component target;
        if (componentDropEvent.getSource() == first) {
            target = first;
        } else {
            target = second;
        }
        ((HasComponents) target)
                .add(componentDropEvent.getDragSourceComponent().get());
    }
}
