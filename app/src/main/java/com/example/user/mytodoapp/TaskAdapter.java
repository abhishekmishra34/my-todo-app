package com.example.user.mytodoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {


    private Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context) {
        this.context = context;
        taskList = new DbHandler(context).getAllTask();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(context).inflate(R.layout.list_tasks, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        Task task = taskList.get(i);

        taskViewHolder.textViewTitle.setText(task.getTitle());
        taskViewHolder.textViewDesc.setText(task.getDesc());
        taskViewHolder.textViewFinishBy.setText(task.getFinishBy());
        taskViewHolder.textViewFinished.setText(task.isFinished() ? "Finished" : "Not Finished");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDesc, textViewFinishBy, textViewFinished;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);
            textViewFinished = itemView.findViewById(R.id.textViewFinished);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure you want to delete it?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DbHandler(context).deleteTask(taskList.get(getAdapterPosition()).getId());
                            taskList = new DbHandler(context).getAllTask();

                            notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog ad = builder.create();
                    ad.show();

                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = taskList.get(getAdapterPosition());
                    showTaskUpdateDialog(task);
                }
            });
        }

        private void showTaskUpdateDialog(final Task task) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_update_task, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view);
            final AlertDialog ad = builder.create();
            ad.show();

            final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
            final EditText editTextDesc = view.findViewById(R.id.editTextDesc);
            final EditText editTextFinishBy = view.findViewById(R.id.editTextFinishBy);
            final CheckBox checkBox = view.findViewById(R.id.checkBoxFinished);

            editTextTitle.setText(task.getTitle());
            editTextDesc.setText(task.getDesc());
            editTextFinishBy.setText(task.getFinishBy());

            view.findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String title = editTextTitle.getText().toString().trim();
                    String desc = editTextDesc.getText().toString().trim();
                    String finishBy = editTextFinishBy.getText().toString().trim();

                    boolean finished = checkBox.isChecked();

                    Task taskUpdated = new Task(task.getId(), title, desc, finishBy, finished);

                    DbHandler dbHandler = new DbHandler(context);
                    dbHandler.updateTask(taskUpdated);

                    Toast.makeText(context, "Task Updated", Toast.LENGTH_LONG).show();

                    ad.dismiss();

                    taskList = new DbHandler(context).getAllTask();

                    notifyDataSetChanged();

                }
            });

        }
    }
}
