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

import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.kernel.NodeChangeListener;
import com.vaadin.hummingbird.kernel.StateNode;
import com.vaadin.hummingbird.kernel.change.NodeChange;
import com.vaadin.hummingbird.kernel.change.PutChange;
import com.vaadin.ui.Template;

import elemental.json.JsonString;
import elemental.json.JsonValue;

public class TodoList extends Template {
    private static class NeedsRecountToken {
        // Empty marker class (ugh)
    }

    private int completeCount = 0;

    public TodoList() {
        getNode().put("remainingCount", Integer.valueOf(0));
        getNode().addChangeListener(new NodeChangeListener() {
            @Override
            public void onChange(StateNode stateNode,
                    List<NodeChange> changes) {
                for (NodeChange nodeChange : changes) {
                    if (nodeChange instanceof PutChange) {
                        if (((PutChange) nodeChange)
                                .getKey() == NeedsRecountToken.class) {
                            updateCounters();
                            getNode().remove(NeedsRecountToken.class);
                        }
                    }
                }
            }
        });
    }

    private void setNeedsRecount() {
        // haxx
        if (!getNode().containsKey(NeedsRecountToken.class)) {
            // Use a class as a key so it isn't sent to the server
            getNode().put(NeedsRecountToken.class, Boolean.TRUE);
        }
    }

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
        setCompleted(todoNode, completed);
    }

    private void setCompleted(StateNode todoNode, boolean completed) {
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
        updateBoolean(node, (remainingCount != 0), "hasRemaining");
        updateBoolean(node, todoCount != 0, "hasTodos");

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

    @Override
    protected void onBrowserEvent(StateNode node, Element element,
            String methodName, Object[] params) {
        switch (methodName) {
        case "setAllCompleted": {
            boolean checked = ((JsonValue) params[0]).asBoolean();
            setAllCompleted(checked);
            break;
        }
        case "setTodoCompleted": {
            boolean checked = ((JsonValue) params[0]).asBoolean();
            setCompleted(element.getNode(), checked);
            break;
        }
        case "addNewTodo": {
            String text = ((JsonString) params[0]).getString();
            addTodo(text);
            element.setAttribute("value", "");
            break;
        }
        case "removeTodo": {
            StateNode todoNode = element.getNode();
            if (todoNode.containsKey("completed")) {
                completeCount--;
            }
            getNode().getMultiValued("todos").remove(todoNode);
            break;
        }
        case "clearCompleted": {
            clearCompleted();
            break;
        }
        case "labelDoubleClick": {
            updateBoolean(node, true, "editing");
            Element todoView = element.getParent();
            Element todoLi = todoView.getParent();
            for (int i = 0; i < todoLi.getChildCount(); i++) {
                Element child = todoLi.getChild(i);
                if ("input".equals(child.getTag())) {
                    child.focus();
                    break;
                }
            }
            break;
        }
        case "handleEditBlur": {
            String text = ((JsonString) params[0]).getString();

            node.put("title", text);
            updateBoolean(node, false, "editing");
            break;
        }
        default:
            throw new RuntimeException(
                    "Unexpected event method name: " + methodName);
        }
    }

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
