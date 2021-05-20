package com.example.sem4_andproject.ui.seeTasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sem4_andproject.data.tasks.Task;
import com.example.sem4_andproject.data.tasks.TaskRepository;
import com.example.sem4_andproject.data.users.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Task>> tasksByDate;
    private UserRepository userRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = TaskRepository.getInstance(application);
        allTasks = repository.getAllTasks();
        userRepository = UserRepository.getInstance(application);
        tasksByDate = repository.getTasksByDate(currentDateTaskFormat());

    }

    public void create(Task task) {
        repository.create(task);
    }

    public List<Task> getListOfTasksByDate(String date)
    {
        return repository.getListTasksByDate(date);
    }
    public void delete(Task task) {
        repository.delete(task);
    }

    public void update(Task task) {
        repository.update(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<Task>> getTasksByDate(String date)
    {
        tasksByDate = repository.getTasksByDate(date);
        return tasksByDate;
    }

    public String getCurrentDate(String format) {
        Date date = new Date();
        String day = new SimpleDateFormat("EEEE").format(date);
        String currentdate = "";
        if (format.equals("yyyy/mm/dd"))
            currentdate = new SimpleDateFormat("yyyy/MM/dd").format(date);
        else if (format.equals("mm/dd/yyyy"))
            currentdate = new SimpleDateFormat("MM/dd/yyyy").format(date);
        else
        currentdate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String displayedDate = day + ", " + currentdate;
        return displayedDate;
    }
    public String getPlusDateViewText(int i, String format)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +i);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        if(format.equals("mm/dd/yyyy"))
            return month + "/" + day + "/" + year;
        else if(format.equals("yyyy/mm/dd"))
            return year + "/" + month + "/" + day;
        else return day + "/" + month + "/" + year;

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
        calendar.add(Calendar.DATE, i);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String date = day + "/" + month;
        return date;
    }
    public String getCurrentDisplayedDatePlusOne(int i)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +i);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date= day + "/" + month;
        return date;
    }
    public void setDueTime(String dueTime, int taskId) {
        repository.setDueTime(dueTime, taskId);
    }
    public LiveData<FirebaseUser> getCurrentUser()
    {
        return userRepository.getCurrentUser();
    }
    public void signOut()
    {
        userRepository.signOut();
    }
    public void setPriority(Task task)
    {
        if(task.getHasPriority() == 0)
        {
            repository.hasPriority(task.getId());
        }
        else {
            repository.notHasPriority(task.getId());
        }
    }
    public void setCheck(Task task)
    {
        if (task.isCompleted() == 0)
            repository.check(task.getId());
        else
            repository.uncheck(task.getId());
    }
}