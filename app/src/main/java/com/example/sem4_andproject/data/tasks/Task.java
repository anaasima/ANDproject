package com.example.sem4_andproject.data.tasks;

import android.nfc.cardemulation.HostApduService;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int hasPriority;

    private String date;

    private int isCompleted;

    private String dueTime;


    @Ignore
    public Task(String title) {
        this.title = title;
        hasPriority = 0;
        date = getCurrentDate();
        dueTime = "";
        isCompleted = 0;

    }

    public Task(String title, String description, String date, int isCompleted, int hasPriority, String dueTime) {
        this.title = title;
        this.description = description;
        this.hasPriority = hasPriority;
        this.date=date;
        this.dueTime = dueTime;
        this.isCompleted=isCompleted;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHasPriority(int hasPriority) {
        this.hasPriority = hasPriority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }


    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getHasPriority() {
        return hasPriority;
    }

    public String getDate() {
        return date;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = day + "/" + month;
        return date;
    }

    public String getDueTime() {
        return dueTime;
    }

    public int isCompleted() {
        return isCompleted;
    }
}
