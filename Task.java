package com.example.taskmanagerapp;

public class Task {
    public String title;
    public String description;
    public boolean done;
    public Task(String title, String description, boolean done) {
        this.title = title; this.description = description; this.done = done;
    }
}
