package com.example.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> tasks;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        ListView taskList = findViewById(R.id.task_list);

        taskAdapter = new TaskAdapter(this, tasks);
        taskList.setAdapter(taskAdapter);

        findViewById(R.id.btn_add_task).setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        final EditText input = viewInflated.findViewById(R.id.input_task);
        builder.setView(viewInflated);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String task = input.getText().toString().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
