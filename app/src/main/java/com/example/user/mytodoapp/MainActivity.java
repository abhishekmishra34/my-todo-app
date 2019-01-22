package com.example.user.mytodoapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskDialog();
            }
        });
    }

    private void showTaskDialog(){
        View view = getLayoutInflater().inflate(R.layout.layout_add_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog ad = builder.create();
        ad.show();

        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDesc = view.findViewById(R.id.editTextDesc);
        final EditText editTextFinishBy = view.findViewById(R.id.editTextFinishBy);

        view.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTextTitle.getText().toString().trim();
                String desc = editTextDesc.getText().toString().trim();
                String finishBy = editTextFinishBy.getText().toString().trim();

                Task task = new Task(0, title, desc, finishBy, false);

                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.addTask(task);

                Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_LONG).show();

                ad.dismiss();
            }
        });

    }
}
