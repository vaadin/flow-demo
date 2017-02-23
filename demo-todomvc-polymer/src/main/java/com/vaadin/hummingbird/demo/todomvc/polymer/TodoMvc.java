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

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.todomvc.polymer.TodoMvc.TodoMvcModel;
import com.vaadin.hummingbird.router.View;

@Tag("todo-mvc")
public class TodoMvc extends Template<TodoMvcModel> implements View {

    public static class TodoMvcModel {
        private String newTodo;
        private boolean hasTodos;
        private boolean toggleAllChecked;
        private List<TodoItem> todos;
        private int itemsLeft;
        private boolean noCompleted;

        public String getNewTodo() {
            return newTodo;
        }

        public void setNewTodo(String newTodo) {
            this.newTodo = newTodo;
        }

        public boolean isHasTodos() {
            return hasTodos;
        }

        public void setHasTodos(boolean hasTodos) {
            this.hasTodos = hasTodos;
        }

        public boolean isToggleAllChecked() {
            return toggleAllChecked;
        }

        public void setToggleAllChecked(boolean toggleAllChecked) {
            this.toggleAllChecked = toggleAllChecked;
        }

        public List<TodoItem> getTodos() {
            return todos;
        }

        public void setTodos(List<TodoItem> todos) {
            this.todos = todos;
        }

        public int getItemsLeft() {
            return itemsLeft;
        }

        public void setItemsLeft(int itemsLeft) {
            this.itemsLeft = itemsLeft;
        }

        public boolean isNoCompleted() {
            return noCompleted;
        }

        public void setNoCompleted(boolean noCompleted) {
            this.noCompleted = noCompleted;
        }
    }

    public class TodoItem {
        private boolean completed;
        private String className;
        private String caption;

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
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
        updateCounts();
    }

    private void updateCounts() {
        TodoMvcModel model = getModel();

        int completedCount = (int) model.getTodos().stream()
                .filter(TodoItem::isCompleted).count();
        int todoCount = model.getTodos().size();

        model.setHasTodos(todoCount != 0);
        model.setItemsLeft(todoCount - completedCount);
        model.setToggleAllChecked(completedCount == todoCount);
        model.setNoCompleted(completedCount == 0);
    }

    @TemplateEventHandler
    private void newTodo() {
        String newTodoCaption = getModel().getNewTodo();
        if (newTodoCaption != null && !newTodoCaption.isEmpty()) {
            addTodo(newTodoCaption);
        }
    }

    @TemplateEventHandler
    private void toggleAll() {
        List<TodoItem> todos = getModel().getTodos();
        boolean completed = getModel().getToggleAllChecked();

        todos.forEach(todo -> setCompleted(todo, completed));
        updateCounts();
    }

    @TemplateEventHandler
    private void editItem(TodoItem eventTarget) {
        eventTarget.setClassName(
                createItemClassName(true, eventTarget.isCompleted()));
        updateCounts();
    }

    @TemplateEventHandler
    private void destroyItem(TodoItem eventTarget) {
        getModel().getTodos().remove(eventTarget);
        updateCounts();
    }

    @TemplateEventHandler
    private void completeItem(TodoItem eventTarget) {
        // Model already updated, only need to update counts (and persist)
        updateCounts();
    }

    @TemplateEventHandler
    private void saveCaption(TodoItem eventTarget) {
        eventTarget.setClassName(
                createItemClassName(false, eventTarget.isCompleted()));
        updateCounts();
    }

    @TemplateEventHandler
    private void clearCompleted() {
        List<TodoItem> items = getModel().getTodos();
        items.removeIf(TodoItem::isCompleted);
        updateCounts();
    }

    private static void setCompleted(TodoItem todoItem, boolean completed) {
        todoItem.setCompleted(completed);
        todoItem.setClassName(createItemClassName(false, completed));
    }

    private static String createItemClassName(boolean editing,
            boolean completed) {
        if (editing && completed) {
            return "editing completed";
        } else if (editing) {
            return "editing";
        } else if (completed) {
            return "completed";
        } else {
            return "";
        }
    }
}
