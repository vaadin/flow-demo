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
package com.vaadin.flow.demo.helloworld.template;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * The main application view of the todo application.
 */
@Tag("todo-template")
@HtmlImport("frontend://components/TodoTemplate.html")
public class TodoList extends PolymerTemplate<TemplateModel> {

    @Id("creator")
    private TodoCreator creator;

    /**
     * Creates the todo list applicaton base.
     */
    public TodoList() {
        setId("template");

        creator.addCreateCallback(todo -> {
            addNewTodoItem(todo);
        });
    }

    private void addNewTodoItem(Todo todo) {
        TodoElement todoElement = new TodoElement(todo);

        todoElement.getElement().addEventListener("remove",
                e -> getElement().removeChild(todoElement.getElement()));

        todoElement.addStateChangeListener(() -> {
            if (todoElement.isCompleted()) {
                todoElement.getElement().setAttribute("slot", "done");
            }
        });

        getElement().appendChild(todoElement.getElement());
    }

}
