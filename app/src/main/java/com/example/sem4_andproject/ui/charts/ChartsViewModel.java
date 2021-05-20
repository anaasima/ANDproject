package com.example.sem4_andproject.ui.charts;

import android.app.Application;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sem4_andproject.data.tasks.Task;
import com.example.sem4_andproject.data.tasks.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartsViewModel extends AndroidViewModel
{
    private TaskRepository repository;
    private LiveData<List<Task>> checkedTasks;
    private LiveData<List<Task>> uncheckedTasks;

    private LiveData<List<Task>> weekCheckTask;
    private LiveData<List<Task>> weekUncheckTask;

    public ChartsViewModel(@NonNull Application application) {
        super(application);
        repository = TaskRepository.getInstance(application);

        checkedTasks = repository.getCheckedTasks(currentDateTaskFormat());
        uncheckedTasks = repository.getUncheckedTasks(currentDateTaskFormat());

        weekCheckTask = repository.getWeeklyCheckedTasks(getCurrentDisplayedDateMinusOne(-6), currentDateTaskFormat());
        weekUncheckTask = repository.getWeeklyUnCheckedTasks(getCurrentDisplayedDateMinusOne(-6), currentDateTaskFormat());
    }
    public LiveData<List<Task>> getWeekCheckTask(String startDate, String endDate)
    {
        weekCheckTask = repository.getWeeklyCheckedTasks(startDate, endDate);
        return weekCheckTask;
    }
    public LiveData<List<Task>> getWeekUncheckTask(String startDate, String endDate)
    {
        weekUncheckTask = repository.getWeeklyUnCheckedTasks(startDate, endDate);
        return weekUncheckTask;
    }
    public LiveData<List<Task>> getCheckedTasks(String date)
    {
        checkedTasks = repository.getCheckedTasks(date);
        return checkedTasks;
    }
    public LiveData<List<Task>> getUncheckedTasks(String date)
    {
        uncheckedTasks = repository.getUncheckedTasks(date);
        return uncheckedTasks;
    }
    public String currentDateTaskFormat()
    {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        String date = day + "/" + month;
        return date;
    }
    public String getCurrentDisplayedDateMinusOne(int i)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +i);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String date = day + "/" + month;
        return date;
    }

}
