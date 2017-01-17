package com.github.aesteve.rapidoid.todobackend;

import com.github.aesteve.rapidoid.todobackend.domain.Todo;
import org.rapidoid.http.Req;
import org.rapidoid.jpa.Entities;
import org.rapidoid.jpa.JPA;
import org.rapidoid.setup.App;
import org.rapidoid.setup.On;

import javax.validation.Valid;
import java.util.HashMap;

public class Main {

    public static void main(String... args) {
        App.bootstrap(args).jpa();
        On.defaults().wrappers((req, next) -> {
            req.response().header("Access-Control-Allow-Origin", "*");
            req.response().header("Access-Control-Allow-Headers", "Content-Type");
            req.response().header("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
            return next.invoke();
        });

        On.options("/*").json(req -> req.response().code(204).plain(""));
        On.get("/todos").json(req -> JPA.of(Todo.class).all());
        On.post("/todos").json((@Valid Todo todo) -> JPA.save(todo.assignUUID()));
        On.delete("/todos").json(req -> {
            final Entities<Todo> todos = JPA.of(Todo.class);
            // todos.all().forEach(JPA::delete);
            JPA.transaction(() -> JPA.jpql("DELETE FROM Todo").execute());
            return todos.all();
        });

        On.get("/todos/{id}").json((String id) -> JPA.get(Todo.class, id));
        On.patch("/todos/{id}").json((@Valid Todo todo, String id, Req req) -> {
            final Todo old = JPA.get(Todo.class, id);
            if (old == null) {
                return req.response().code(404).plain("Not Found"); // FIXME : proper json
            }
            return JPA.merge(todo);
        });
        On.delete("/todos/{id}").json((Req req) -> {
            JPA.delete(Todo.class, req.param("id"));
            return req.response().code(204).plain("");
        });
    }

}
