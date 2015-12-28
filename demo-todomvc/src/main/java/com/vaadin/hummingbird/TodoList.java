/*
 * Copyright 2000-2014 Vaadin Ltd.
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
package com.vaadin.hummingbird;

import java.util.List;

import com.vaadin.annotations.JS;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.ui.Template;

public class TodoList extends Template {
    public interface Todo {
        public String getTitle();

        public void setTitle(String title);

        public boolean isCompleted();

        public void setCompleted(boolean completed);

        public boolean isEditing();

        public void setEditing(boolean editing);
    }

    public interface TodoListModel extends Model {
        public List<Todo> getTodos();

        public void setTodos(List<Todo> todods);

        @JS("todos.length")
        public int getTodoCount();

        @JS("(function() {var count=0; for(var i=0;i<todos.length;i++) if (todos[i].completed) count++; return count;})();")
        public int getCompleteCount();

        @JS("todoCount - completeCount")
        public int getRemainingCount();
    }

    public TodoList() {
        // Can't currently do this using the model api
        getNode().getMultiValued("todos");
    }

    @TemplateEventHandler
    public void addTodo(String title) {
        Todo todo = Model.create(Todo.class);
        todo.setTitle(title);
        getTodos().add(todo);
    }

    private List<Todo> getTodos() {
        return getModel().getTodos();
    }

    public void setCompleted(int todoIndex, boolean completed) {
        getTodos().get(todoIndex).setCompleted(completed);
    }

    @TemplateEventHandler
    protected void logTodoCompleted(Todo todo) {
        System.out.println("Todo \"" + todo.getTitle() + "\".completed: "
                + todo.isCompleted());
    }

    public boolean isCompleted(int todoIndex) {
        return getTodos().get(todoIndex).isCompleted();
    }

    @TemplateEventHandler
    private void handleEditBlur(Todo todo) {
        System.out.println("Title changed to: " + todo.getTitle());
    }

    @TemplateEventHandler
    private void removeTodo(Todo todo) {
        getTodos().remove(todo);
    }

    @TemplateEventHandler
    public void clearCompleted() {
        List<Todo> todos = getTodos();
        todos.removeIf(Todo::isCompleted);
    }

    @TemplateEventHandler
    public void setAllCompleted(boolean completed) {
        List<Todo> todos = getTodos();
        todos.forEach(todo -> todo.setCompleted(completed));
    }

    @Override
    protected TodoListModel getModel() {
        return (TodoListModel) super.getModel();
    }
}
