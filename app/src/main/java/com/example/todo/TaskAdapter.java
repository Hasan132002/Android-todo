package com.example.todo;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskAdapter extends android.widget.BaseAdapter {

    private final Context context;
    private final ArrayList<String> tasks;

    public TaskAdapter(Context context, ArrayList<String> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        TextView taskName = convertView.findViewById(R.id.task_name);
        ImageView editTask = convertView.findViewById(R.id.btn_edit_task);
        ImageView deleteTask = convertView.findViewById(R.id.btn_delete_task);

        taskName.setText(tasks.get(position));

        editTask.setOnClickListener(v -> showEditTaskDialog(position));

        deleteTask.setOnClickListener(v -> {
            tasks.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    private void showEditTaskDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Task");

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null);
        final EditText input = viewInflated.findViewById(R.id.input_task);
        input.setText(tasks.get(position));
        builder.setView(viewInflated);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String updatedTask = input.getText().toString().trim();
            if (!updatedTask.isEmpty()) {
                tasks.set(position, updatedTask);
                notifyDataSetChanged();
                Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}