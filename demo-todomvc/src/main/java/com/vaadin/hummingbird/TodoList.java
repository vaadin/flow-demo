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

import java.util.Iterator;
import java.util.List;

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.kernel.StateNode;
import com.vaadin.ui.Template;

public class TodoList extends Template {
    private Runnable recalculateRunnable = this::updateCounters;

    private int completeCount = 0;

    public TodoList() {
        getNode().put("remainingCount", Integer.valueOf(0));
    }

    private void setNeedsRecount() {
        getElement().runBeforeNextClientResponse(recalculateRunnable);
    }

    @TemplateEventHandler
    public void addTodo(String todo) {
        StateNode todoNode = StateNode.create();
        todoNode.put("title", todo);
        getTodos().add(todoNode);
        setNeedsRecount();
    }

    private List<Object> getTodos() {
        return getNode().getMultiValued("todos");
    }

    public void setCompleted(int todoIndex, boolean completed) {
        StateNode todoNode = (StateNode) getTodos().get(todoIndex);
        setTodoCompleted(todoNode, completed);
    }

    @TemplateEventHandler
    protected void setTodoCompleted(Element element, boolean completed) {
        setTodoCompleted(element.getNode(), completed);
    }

    private void setTodoCompleted(StateNode todoNode, boolean completed) {
        if (completed && !todoNode.containsKey("completed")) {
            completeCount++;
            todoNode.put("completed", Boolean.TRUE);
        } else if (!completed && todoNode.containsKey("completed")) {
            completeCount--;
            todoNode.remove("completed");
        }
        setNeedsRecount();
    }

    private void updateCounters() {
        StateNode node = getNode();

        updateBoolean(node, completeCount == 0, "hasNoCompleted");

        int todoCount = getTodos().size();

        int remainingCount = todoCount - completeCount;
        updateBoolean(node, remainingCount == 0, "isAllCompleted");
        updateBoolean(node, (remainingCount == 0), "hasNoRemaining");
        updateBoolean(node, todoCount == 0, "hasNoTodos");

        Integer remainingObj = Integer.valueOf(remainingCount);
        if (!remainingObj.equals(node.get("remainingCount"))) {
            node.put("remainingCount", remainingObj);
        }
    }

    private void updateBoolean(StateNode node, boolean value, String key) {
        if (value && !node.containsKey(key)) {
            node.put(key, Boolean.TRUE);
        } else if (!value && node.containsKey(key)) {
            node.remove(key);
        }
    }

    public boolean isCompleted(int todoIndex) {
        StateNode todoNode = (StateNode) getTodos().get(todoIndex);
        return todoNode.containsKey("completed");
    }

    @TemplateEventHandler
    private void handleEditBlur(Element element, String text) {
        StateNode node = element.getNode();
        node.put("title", text);
        updateBoolean(node, false, "editing");
    }

    @TemplateEventHandler
    private void labelDoubleClick(Element element) {
        StateNode node = element.getNode();
        updateBoolean(node, true, "editing");
        Element todoView = element.getParent();
        Element todoLi = todoView.getParent();
        for (int i = 0; i < todoLi.getChildCount(); i++) {
            Element child = todoLi.getChild(i);
            if ("input".equals(child.getTag())) {
                child.focus();
                return;
            }
        }
    }

    @TemplateEventHandler
    protected void removeTodo(Element element) {
        removeTodo(element.getNode());
    }

    private void removeTodo(StateNode todoNode) {
        if (todoNode.containsKey("completed")) {
            completeCount--;
        }
        getNode().getMultiValued("todos").remove(todoNode);
        setNeedsRecount();
    }

    @TemplateEventHandler
    public void clearCompleted() {
        List<Object> todos = getNode().getMultiValued("todos");
        Iterator<Object> iterator = todos.iterator();
        while (iterator.hasNext()) {
            StateNode todoNode = (StateNode) iterator.next();
            if (todoNode.containsKey("completed")) {
                iterator.remove();
            }
        }
        completeCount = 0;
        setNeedsRecount();
    }

    @TemplateEventHandler
    public void setAllCompleted(boolean completed) {
        List<Object> todos = getNode().getMultiValued("todos");
        for (Object object : todos) {
            updateBoolean((StateNode) object, completed, "completed");
        }
        if (completed) {
            completeCount = todos.size();
        } else {
            completeCount = 0;
        }
        setNeedsRecount();
    }
}
