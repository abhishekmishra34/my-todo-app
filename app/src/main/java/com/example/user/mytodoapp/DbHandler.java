package com.example.user.mytodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyTodoApp";
    private static final int DB_VERSION = 1;

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tasks(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\ttitle VARCHAR(255),\n" +
                "\tdescription VARCHAR(255),\n" +
                "\tfinishBy VARCHAR(255),\n" +
                "\tfinished INTEGER\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("title", task.getTitle());
        cv.put("description", task.getDesc());
        cv.put("finishBy", task.getFinishBy());
        cv.put("finished", task.isFinished());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("tasks", null, cv);
        return true;
    }

    public List<Task> getAllTask() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);

        List<Task> taskList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                Task task = new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) != 0
                );

                taskList.add(task);

            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public boolean updateTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE tasks SET " +
                        "title = ?, " +
                        "description = ?, " +
                        "finishBy = ?, " +
                        "finished = ? WHERE id = ?;",
                new String[]{
                        task.getTitle(),
                        task.getDesc(),
                        task.getFinishBy(),
                        String.valueOf(task.isFinished()),
                        String.valueOf(task.getId())
                });
        return true;
    }
}
