package com.manar.testass;

public class Task {
    private String taskName;
    private boolean stutas;


    public Task(String taskName) {
        this.taskName = taskName;
        this.stutas=false;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isStutas() {
        return stutas;
    }

    public void setStutas(boolean stutas) {
        this.stutas = stutas;
    }

    @Override
    public String toString() {
        return taskName;
    }




}

