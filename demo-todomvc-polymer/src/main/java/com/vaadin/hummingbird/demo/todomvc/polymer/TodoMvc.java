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

    public static class TodoMvcModel {
        private String newTodo;
        private List<TodoItem> todos;

        public String getNewTodo() {
            return newTodo;
        }

        public void setNewTodo(String newTodo) {
            this.newTodo = newTodo;
        }

        @ComputedProperty("todos")
        public boolean isHasTodos() {
            return !todos.isEmpty();
        }

        @ComputedProperty("todos")
        public boolean isToggleAllChecked() {
            return getItemsLeft() == 0;
        }

        public List<TodoItem> getTodos() {
            return todos;
        }

        public void setTodos(List<TodoItem> todos) {
            this.todos = todos;
        }

        @ComputedProperty("todos")
        public int getItemsLeft() {
            return (int) todos.stream().filter(item -> !item.isCompleted())
                    .count();
        }

        @ComputedProperty("todos")
        public boolean isNoCompleted() {
            return getItemsLeft() == todos.size();
        }
    }

    public class TodoItem {
        private boolean completed;
        private boolean editing;
        private String caption;

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public boolean isEditing() {
            return editing;
        }

        public void setEditing(boolean editing) {
            this.editing = editing;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public TodoMvc() {
        addTodo("Implement Polymer template support");
    }

    public void addTodo(String caption) {
        TodoItem todo = new TodoItem();
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
