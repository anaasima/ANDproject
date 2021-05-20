package com.example.sem4_andproject.ui.addTask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Calendar;

public class AddNewTaskViewModel extends AndroidViewModel {
    public AddNewTaskViewModel(@NonNull Application application) {
        super(application);
    }
    public String currentDateTaskFormat()
    {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        String date = day + "/" + month;
        return date;
    }
}
