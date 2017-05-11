package com.vaadin.flow.demo.helloworld.template;

import java.time.LocalDateTime;

/**
 * Todo item.
 */
public class Todo {
    String task;
    String user;
    Number rid;

    LocalDateTime time;

    boolean completed = false;


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Number getRid() {
        return rid;
    }

    public void setRid(Number rid) {
        this.rid = rid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format("Task: %s, User: %s, Id: %s", task,
                user, rid);
    }
}
