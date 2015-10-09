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

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Template;

public class Customers extends Template {
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

        public int getRemainingCount();

        public void setRemainingCount(int remainingCount);

        public boolean isHasNoCompleted();

        public void setHasNoCompleted(boolean hasNoCompleted);

        public boolean isHasNoRemaining();

        public void setHasNoRemaining(boolean hasNoRemaining);

        public boolean isHasNoTodos();

        public void setHasNoTodos(boolean hasNoTodos);
    }

    private Runnable recalculateRunnable = this::updateCounters;

    private int completeCount = 0;

    public Customers() {
        // Can't currently do this using the model api
        getNode().getMultiValued("todos");
    }

    private void setNeedsRecount() {
        getElement().runBeforeNextClientResponse(recalculateRunnable);
    }

    @TemplateEventHandler
    public void addTodo(String title) {
        Todo todo = Model.create(Todo.class);
        todo.setTitle(title);
        getTodos().add(todo);
        setNeedsRecount();
    }

    private List<Todo> getTodos() {
        return getModel().getTodos();
    }

    public void setCompleted(int todoIndex, boolean completed) {
        setTodoCompleted(getTodos().get(todoIndex), completed);
    }

    @TemplateEventHandler
    protected void setTodoCompleted(Element element, boolean completed) {
        setTodoCompleted(getTodoForElement(element), completed);
    }

    private Todo getTodoForElement(Element element) {
        return Model.wrap(element.getNode(), Todo.class);
    }

    private void setTodoCompleted(Todo todo, boolean completed) {
        if (completed == todo.isCompleted()) {
            return;
        }
        if (completed) {
            completeCount++;
        } else {
            completeCount--;
        }
        todo.setCompleted(completed);
        setNeedsRecount();
    }

    private void updateCounters() {
        TodoListModel model = getModel();

        model.setHasNoCompleted(completeCount == 0);

        int todoCount = getTodos().size();

        int remainingCount = todoCount - completeCount;
        model.setHasNoRemaining(remainingCount == 0);
        model.setHasNoTodos(todoCount == 0);

        model.setRemainingCount(remainingCount);
    }

    public boolean isCompleted(int todoIndex) {
        return getTodos().get(todoIndex).isCompleted();
    }

    @TemplateEventHandler
    private void handleEditBlur(Element element) {
        Todo todo = getTodoForElement(element);
        System.out.println("Title changed to: " + todo.getTitle());
    }

    @TemplateEventHandler
    protected void removeTodo(Element element) {
        removeTodo(getTodoForElement(element));
    }

    private void removeTodo(Todo todo) {
        if (todo.isCompleted()) {
            completeCount--;
        }
        getTodos().remove(todo);
        setNeedsRecount();
    }

    @TemplateEventHandler
    public void clearCompleted() {
        List<Todo> todos = getTodos();
        todos.removeIf(Todo::isCompleted);
        completeCount = 0;
        setNeedsRecount();
    }

    @TemplateEventHandler
    public void setAllCompleted(boolean completed) {
        List<Todo> todos = getTodos();
        todos.forEach(todo -> todo.setCompleted(completed));

        if (completed) {
            completeCount = todos.size();
        } else {
            completeCount = 0;
        }
        setNeedsRecount();
    }

    @Override
    protected TodoListModel getModel() {
        return (TodoListModel) super.getModel();
    }
}
