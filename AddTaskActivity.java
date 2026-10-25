package com.example.taskmanagerapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        EditText title = findViewById(R.id.editTitle);
        EditText description = findViewById(R.id.editDescription);
        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String t = title.getText().toString().trim();
            String d = description.getText().toString().trim();
            if (t.isEmpty()) { title.setError("Enter a task title"); return; }
            new TaskStore(this).addTask(new Task(t, d, false));
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
