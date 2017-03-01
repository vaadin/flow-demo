/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.todomvc.polymer;

import java.util.List;

import com.vaadin.annotations.EventData;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.todomvc.polymer.TodoMvc.TodoMvcModel;
import com.vaadin.hummingbird.router.View;

@Tag("todo-mvc")
public class TodoMvc extends Template<TodoMvcModel> implements View {

    public interface TodoMvcModel {

        String getNewTodo();

        void setNewTodo(String newTodo);

        default boolean isHasTodos() {
            return !getTodos().isEmpty();
        }

        default boolean isToggleAllChecked() {
            return getItemsLeft() == 0;
        }

        List<TodoItem> getTodos();

        void setTodos(List<TodoItem> todos);

        default int getItemsLeft() {
            return (int) getTodos().stream().filter(item -> !item.isCompleted())
                    .count();
        }

        default boolean isNoCompleted() {
            return getItemsLeft() == getTodos().size();
        }
    }

    public interface TodoItem {

        public boolean isCompleted();

        public void setCompleted(boolean completed);

        public boolean isEditing();

        public void setEditing(boolean editing);

        public String getCaption();

        public void setCaption(String caption);
    }

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public TodoMvc() {
        addTodo("Implement Polymer template support");
    }

    public void addTodo(String caption) {
        TodoItem todo = createModel(TodoItem.class);
        todo.setCaption(caption);
        getModel().getTodos().add(todo);
        getModel().setNewTodo("");
    }

    @PropertyChangeHandler("newTodo")
    private void newTodo() {
        String newTodoCaption = getModel().getNewTodo();
        if (newTodoCaption != null && !newTodoCaption.isEmpty()) {
            addTodo(newTodoCaption);
        }
    }

    @TemplateEventHandler
    private void toggleAll(@EventData("event.target.checked") boolean checked) {
        List<TodoItem> todos = getModel().getTodos();

        todos.forEach(todo -> todo.setCompleted(checked));
    }

    @TemplateEventHandler
    private void editItem(TodoItem eventTarget) {
        eventTarget.setEditing(true);
    }

    @TemplateEventHandler
    private void destroyItem(TodoItem eventTarget) {
        // Should also persist the change
        getModel().getTodos().remove(eventTarget);
    }

    @TemplateEventHandler
    private void completeItem(TodoItem eventTarget) {
        // Model already updated, should also persist
    }

    @TemplateEventHandler
    private void saveCaption(TodoItem eventTarget) {
        // Model already updated, should also persist
        eventTarget.setEditing(false);
    }

    @TemplateEventHandler
    private void clearCompleted() {
        List<TodoItem> items = getModel().getTodos();
        items.removeIf(TodoItem::isCompleted);
    }
}
