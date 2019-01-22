package com.example.user.mytodoapp;

public class Task {

    private int id;
    private String title;
    private String desc;
    private String finishBy;
    private boolean finished;

    public Task(int id, String title, String desc, String finishBy, boolean finished) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.finishBy = finishBy;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getFinishBy() {
        return finishBy;
    }

    public boolean isFinished() {
        return finished;
    }
}
