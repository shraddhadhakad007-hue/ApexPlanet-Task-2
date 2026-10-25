package com.example.taskmanagerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends Activity {
    private TaskStore store;
    private ListView list;
    private TextView empty;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        store = new TaskStore(this); list = findViewById(R.id.listTasks); empty = findViewById(R.id.txtEmpty);
        list.setOnItemClickListener((parent, view, position, id) -> {
            store.toggleTask(position); refresh();
            Toast.makeText(this, "Task status updated", Toast.LENGTH_SHORT).show();
        });
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            store.deleteTask(position); refresh();
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show(); return true;
        });
        refresh();
    }
    private void refresh() {
        List<Task> tasks = store.getTasks(); List<String> labels = new ArrayList<>();
        for (Task t : tasks) labels.add((t.done ? "✓ " : "○ ") + t.title + (t.description.isEmpty() ? "" : "\n" + t.description));
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels));
        empty.setVisibility(tasks.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
