package com.example.sem4_andproject.data.tasks;

import android.app.Application;
import android.os.AsyncTask;
import android.text.GetChars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.data.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskRepository
{
    private TaskDAO taskDAO;
    private LiveData<List<Task>> tasks;

    private LiveData<List<Task>> checkTask;
    private LiveData<List<Task>> uncheckTask;

    private LiveData<List<Task>> taskByDate;

    private LiveData<List<Task>> weekCheckTask;
    private LiveData<List<Task>> weekUncheckTask;

    private static TaskRepository instance;

    private TaskRepository(Application application)
    {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDAO = database.taskDAO();
        tasks = taskDAO.getAllTasks();

        taskByDate = taskDAO.getTasksByDate(getCurrentDate());

        checkTask = taskDAO.getCheckedTasks(getCurrentDate());
        uncheckTask = taskDAO.getUncheckedTasks(getCurrentDate());

        weekCheckTask = taskDAO.getWeeklyCheckedTasks(getStartDate(), getCurrentDate());
        weekUncheckTask = taskDAO.getWeeklyUncheckedTasks(getStartDate(), getCurrentDate());
    }
    public static synchronized TaskRepository getInstance(Application application)
    {
        if (instance == null)
        {
            instance = new TaskRepository(application);
        }
        return instance;
    }


    public void create(Task task)
    {
        new CreateTaskAsyncTask(taskDAO).execute(task);
    }
    public void delete(Task task)
    {
        new DeleteTaskAsyncTask(taskDAO).execute(task);
    }
    public void update(Task task)
    {
        new UpdateTaskAsyncTask(taskDAO).execute(task);
    }


    public LiveData<List<Task>> getTasksByDate(String date){
        try{
            taskByDate = new GetTasksByDate(taskDAO, date).execute().get();
        return taskByDate;}
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public LiveData<List<Task>> getAllTasks()
    {
        return tasks;
    }
    public List<Task> getListTasksByDate(String date)
    {
        try
        {
            return new ListOfTasksByDate(taskDAO, date).execute().get();
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public LiveData<List<Task>> getUncheckedTasks(String date){
        try
        {
            uncheckTask = new ChartsUnTasksByDate(taskDAO, date).execute().get();
            return uncheckTask;
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public LiveData<List<Task>> getCheckedTasks(String date)
    {
        try {
            checkTask = new ChartsComplTasksByDate(taskDAO, date).execute().get();
            return checkTask;
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public LiveData<List<Task>> getWeeklyCheckedTasks(String startDate, String endDate)
    {
        try
        {
            weekCheckTask = new WeekTasksByDate(taskDAO, startDate, endDate).execute().get();
            return weekCheckTask;
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public LiveData<List<Task>> getWeeklyUnCheckedTasks(String startDate, String endDate)
    {
        try
        {
            weekUncheckTask = new WeekUnTasksByDate(taskDAO, startDate, endDate).execute().get();
            return weekUncheckTask;
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return null;
    }
    public void deleteAll()
    {
        new DeleteAllTasksAsyncTask(taskDAO).execute();
    }

    public void check(int taskId) {new CheckAsyncTask(taskDAO).execute(taskId);}
    public void uncheck(int taskId) { new UnCheckAsyncTask(taskDAO).execute(taskId);}
    public void hasPriority(int taskId) { new HasPriorityAsyncTask(taskDAO).execute(taskId);}
    public void notHasPriority(int taskId) { new NotHasPriorityAsyncTask(taskDAO).execute(taskId);}
    public void setDueTime(String dueTime, int taskId) {new SetTimeAsyncTask(taskDAO, dueTime, taskId).execute();}
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = day + "/" + month;
        return date;
    }
    public String getStartDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +(-6));
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = day + "/" + month;
        return date;
    }
    private static class CreateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private CreateTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO=taskDAO;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.create(tasks[0]);
            return null;
        }
    }
    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private UpdateTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO=taskDAO;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.update(tasks[0]);
            return null;
        }
    }
    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private DeleteTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO=taskDAO;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.delete(tasks[0]);
            return null;
        }
    }
    private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDAO taskDAO;

        private DeleteAllTasksAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO=taskDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDAO.deleteAll();
            return null;
        }
    }
    private static class CheckAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TaskDAO taskDAO;

        private CheckAsyncTask(TaskDAO taskDAO) { this.taskDAO=taskDAO;}

        @Override
        protected Void doInBackground(Integer... integers) {
            taskDAO.check(integers[0]);
            return null;
        }
    }
    private static class UnCheckAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TaskDAO taskDAO;

        private UnCheckAsyncTask(TaskDAO taskDAO) { this.taskDAO=taskDAO;}

        @Override
        protected Void doInBackground(Integer... integers) {
            taskDAO.uncheck(integers[0]);
            return null;
        }
    }
    private static class HasPriorityAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TaskDAO taskDAO;

        private HasPriorityAsyncTask(TaskDAO taskDAO) { this.taskDAO=taskDAO;}

        @Override
        protected Void doInBackground(Integer... integers) {
            taskDAO.hasPriority(integers[0]);
            return null;
        }
    }
    private static class NotHasPriorityAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TaskDAO taskDAO;

        private NotHasPriorityAsyncTask(TaskDAO taskDAO) { this.taskDAO=taskDAO;}

        @Override
        protected Void doInBackground(Integer... integers) {
            taskDAO.notHasPriority(integers[0]);
            return null;
        }
    }
    private static class SetTimeAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private TaskDAO taskDAO;
        private String time;
        private int taskId;

        private SetTimeAsyncTask(TaskDAO taskDAO, String time, int taskId) {this.taskDAO=taskDAO;
        this.time=time;
        this.taskId=taskId;}

        @Override
        protected Void doInBackground(Void... voids) {
            taskDAO.setDueTime(time, taskId);
            return null;
        }
    }

    private static class GetTasksByDate extends AsyncTask<String, Void, LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;
        private String date;

        private GetTasksByDate(TaskDAO taskDAO, String date)
        {
            this.taskDAO=taskDAO;
            this.date=date;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... strings) {
            return taskDAO.getTasksByDate(date);
        }
    }
    private static class ChartsComplTasksByDate extends AsyncTask<String, Void, LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;
        private String date;

        private ChartsComplTasksByDate(TaskDAO taskDAO, String date)
        {
            this.taskDAO=taskDAO;
            this.date=date;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... strings) {
            return taskDAO.getCheckedTasks(date);
        }
    }
    private static class ChartsUnTasksByDate extends AsyncTask<String, Void, LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;
        private String date;

        private ChartsUnTasksByDate(TaskDAO taskDAO, String date)
        {
            this.taskDAO=taskDAO;
            this.date=date;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... strings) {
            return taskDAO.getUncheckedTasks(date);
        }
    }
    private static class WeekUnTasksByDate extends AsyncTask<String, Void, LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;
        private String startDate;
        private String endDate;

        private WeekUnTasksByDate(TaskDAO taskDAO, String startDate, String endDate)
        {
            this.taskDAO=taskDAO;
            this.startDate=startDate;
            this.endDate=endDate;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... strings) {
            return taskDAO.getWeeklyUncheckedTasks(startDate, endDate);
        }
    }
    private static class WeekTasksByDate extends AsyncTask<String, Void, LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;
        private String startDate;
        private String endDate;

        private WeekTasksByDate(TaskDAO taskDAO, String startDate, String endDate)
        {
            this.taskDAO=taskDAO;
            this.startDate=startDate;
            this.endDate=endDate;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... strings) {
            return taskDAO.getWeeklyCheckedTasks(startDate, endDate);
        }
    }
    private static class ListOfTasksByDate extends AsyncTask<String, Void, List<Task>>
    {
        private TaskDAO taskDAO;
        private String date;

        private ListOfTasksByDate(TaskDAO taskDAO, String date)
        {
            this.taskDAO=taskDAO;
            this.date=date;
        }

        @Override
        protected List<Task> doInBackground(String... strings) {
            return taskDAO.getListOfTasks(date);
        }
    }
}
