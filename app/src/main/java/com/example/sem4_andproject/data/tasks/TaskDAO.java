package com.example.sem4_andproject.data.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sem4_andproject.data.tasks.Task;

import java.util.List;

@Dao
public interface TaskDAO
{
    @Insert
    void create(Task task);
    @Delete
    void delete(Task task);
    @Update
    void update(Task task);
    @Query("SELECT * FROM task_table ORDER BY hasPriority DESC")
    LiveData<List<Task>> getAllTasks();
    @Query("DELETE FROM task_table")
    void deleteAll();
    @Query("UPDATE task_table SET isCompleted = 1 WHERE id LIKE :taskId AND isCompleted = 0")
    void check(int taskId);
    @Query("UPDATE task_table SET isCompleted = 0 WHERE id LIKE :taskId AND isCompleted = 1")
    void uncheck(int taskId);
    @Query("UPDATE task_table SET hasPriority = 1 WHERE id LIKE :taskId AND hasPriority= 0")
    void hasPriority(int taskId);
    @Query("UPDATE task_table SET hasPriority = 0 WHERE id LIKE :taskId AND hasPriority= 1")
    void notHasPriority(int taskId);
    @Query("UPDATE task_table SET dueTime = :dueTime WHERE id LIKE :taskId")
    void setDueTime(String dueTime, int taskId);
    @Query("SELECT * FROM task_table WHERE isCompleted=1 AND date LIKE :date")
    LiveData<List<Task>> getCheckedTasks(String date);
    @Query("SELECT * FROM task_table where isCompleted=0 AND date LIKE :date")
    LiveData<List<Task>> getUncheckedTasks(String date);
    @Query("SELECT * FROM task_table WHERE date LIKE :date ORDER BY isCompleted ASC, hasPriority DESC")
    LiveData<List<Task>> getTasksByDate(String date);
    @Query("SELECT * FROM task_table WHERE date LIKE :date ORDER BY isCompleted ASC, hasPriority DESC")
    List<Task> getListOfTasks(String date);
    @Query("SELECT * FROM task_table where isCompleted=1 AND date BETWEEN :startDate AND :endDate")
    LiveData<List<Task>> getWeeklyCheckedTasks(String startDate, String endDate);
    @Query("SELECT * FROM task_table where isCompleted=0 AND date BETWEEN :startDate AND :endDate")
    LiveData<List<Task>> getWeeklyUncheckedTasks(String startDate, String endDate);


}
