package com.example.taskmanagerapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiDemoActivity extends Activity {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private TextView output;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_api_demo);
        output = findViewById(R.id.txtApiResult);
        findViewById(R.id.btnFetch).setOnClickListener(v -> fetchSampleTask());
    }
    private void fetchSampleTask() {
        output.setText("Loading sample data...");
        executor.execute(() -> {
            HttpURLConnection connection = null; String result;
            try {
                connection = (HttpURLConnection) new URL("https://jsonplaceholder.typicode.com/todos/1").openConnection();
                connection.setRequestMethod("GET"); connection.setConnectTimeout(10000); connection.setReadTimeout(10000);
                int status = connection.getResponseCode();
                if (status < 200 || status >= 300) throw new Exception("HTTP " + status);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder body = new StringBuilder(); String line;
                while ((line = reader.readLine()) != null) body.append(line);
                reader.close(); JSONObject json = new JSONObject(body.toString());
                result = "Sample API task\n\nTitle: " + json.optString("title") + "\nCompleted: " + json.optBoolean("completed")
                    + "\n\nDemo endpoint only; this does not sync your saved local tasks.";
            } catch (Exception e) {
                result = "Could not load API data. Check internet and retry.\n\nDetails: " + e.getMessage();
            } finally { if (connection != null) connection.disconnect(); }
            String message = result; runOnUiThread(() -> output.setText(message));
        });
    }
    @Override protected void onDestroy() { super.onDestroy(); executor.shutdownNow(); }
}
