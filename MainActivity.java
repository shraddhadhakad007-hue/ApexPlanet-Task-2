package com.example.taskmanagerapp;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int REQ_NOTIFICATIONS = 42;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        findViewById(R.id.btnAdd).setOnClickListener(v -> startActivity(new Intent(this, AddTaskActivity.class)));
        findViewById(R.id.btnTasks).setOnClickListener(v -> startActivity(new Intent(this, TaskListActivity.class)));
        findViewById(R.id.btnApi).setOnClickListener(v -> startActivity(new Intent(this, ApiDemoActivity.class)));
        updateCount();
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIFICATIONS);
    }
    @Override protected void onResume() { super.onResume(); updateCount(); }
    private void updateCount() {
        TextView count = findViewById(R.id.txtCount);
        if (count != null) count.setText("Saved tasks: " + new TaskStore(this).getTasks().size());
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("task_updates", "Task updates", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }
}
