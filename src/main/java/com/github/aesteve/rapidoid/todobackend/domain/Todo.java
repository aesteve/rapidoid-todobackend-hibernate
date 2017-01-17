package com.github.aesteve.rapidoid.todobackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Todo {

    @Id
    private String id;
    private String title;
    @Column(name = "ordering")
    private Integer order;
    private boolean completed;

    public Todo assignUUID() {
        id = UUID.randomUUID().toString();
        return this;
    }

    public String getUrl() {
        return "http://localhost:8888/todos/" + id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer ordering) {
        this.order = ordering;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
