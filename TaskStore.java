package com.example.taskmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class TaskStore {
    private static final String PREFS = "task_manager_prefs";
    private static final String KEY = "tasks";
    private final SharedPreferences prefs;
    public TaskStore(Context context) { prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE); }
    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(prefs.getString(KEY, "[]"));
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                result.add(new Task(o.optString("title"), o.optString("description"), o.optBoolean("done")));
            }
        } catch (Exception ignored) { }
        return result;
    }
    public void addTask(Task task) {
        List<Task> tasks = getTasks(); tasks.add(task); save(tasks);
    }
    public void toggleTask(int index) {
        List<Task> tasks = getTasks();
        if (index >= 0 && index < tasks.size()) { tasks.get(index).done = !tasks.get(index).done; save(tasks); }
    }
    public void deleteTask(int index) {
        List<Task> tasks = getTasks();
        if (index >= 0 && index < tasks.size()) { tasks.remove(index); save(tasks); }
    }
    private void save(List<Task> tasks) {
        JSONArray arr = new JSONArray();
        try {
            for (Task t : tasks) {
                JSONObject o = new JSONObject();
                o.put("title", t.title); o.put("description", t.description); o.put("done", t.done); arr.put(o);
            }
        } catch (Exception ignored) { }
        prefs.edit().putString(KEY, arr.toString()).apply();
    }
}
