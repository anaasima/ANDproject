package com.example.sem4_andproject.ui.seeTasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sem4_andproject.R;
import com.example.sem4_andproject.ui.addTask.AddNewTaskActivity;
import com.example.sem4_andproject.data.tasks.Task;
import com.example.sem4_andproject.ui.popUp.PopUpFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class TaskFragment extends Fragment {

    static final int ADD_TASK_REQUEST = 1;
    static final int EDIT_TASK_REQUEST = 2;

    RecyclerView recyclerView;
    TaskAdapter adapter;
    TaskViewModel taskViewModel;

    FrameLayout homeLayout;
    FloatingActionButton buttonAddTask;
    TextView dateTextView;
    ImageView dateBack;
    ImageView datePlus;

    Task globalTask;
    int dateCount = 0;
    String value;

    SharedPreferences sharedPreferences;

    public TaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        setUpRecyclerViewAndAdapter(view);
        setUpViewModel();
        setUpButtons(view);
        swipeToDelete();

        return view;
    }

    public void setUpRecyclerViewAndAdapter(View view)
    {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
            }

            @Override
            public void onEditClick(Task task) {
                editTask(task);
            }

            @Override
            public void onCheckClick(Task task) {
                checkItem(task);
            }

            @Override
            public void onPriorityClick(Task task) {

                                priorityItem(task);
            }

            @Override
            public void onDateClick(Task task) {
                PopUpFragment popUpFragment = new PopUpFragment(view, TaskFragment.this, task, homeLayout);

                    globalTask = null;
                    homeLayout.getForeground().setAlpha(220);
                    popUpFragment.showPopupWindow(view);
                    globalTask = task;
                }
        });
    }

    public void setUpButtons(View view)
    {
        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        value = sharedPreferences.getString("dateFormat", "dd/mm/yyyy");
        String theme = sharedPreferences.getString("themeFormat", "dusk");

        homeLayout = view.findViewById(R.id.homeFrameLayout);

        dateBack = view.findViewById(R.id.dateBack);
        datePlus = view.findViewById(R.id.datePlus);
        dateTextView = view.findViewById(R.id.dateTextView);
        buttonAddTask = view.findViewById(R.id.button_add_task);
        dateTextView.setText(taskViewModel.getCurrentDate(value));

        homeLayout.getForeground().setAlpha(0);
        if (theme.equals("dusk")) {
            homeLayout.setBackgroundResource(R.drawable.background);
        }
        else
            homeLayout.setBackgroundResource(R.drawable.another_blurred_background);

            buttonAddTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTask();
                }
            });
        dateBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateCount--;
                adapter.setTasks(taskViewModel.getListOfTasksByDate(taskViewModel.getCurrentDisplayedDateMinusOne(dateCount)));
                dateTextView.setText(taskViewModel.getPlusDateViewText(dateCount, value));


            }
        });
        datePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCount++;
                adapter.setTasks(taskViewModel.getListOfTasksByDate(taskViewModel.getCurrentDisplayedDatePlusOne(dateCount)));
                dateTextView.setText(taskViewModel.getPlusDateViewText(dateCount, value));


            }
        });

    }
    public void checkItem(Task task)
    {
        taskViewModel.setCheck(task);
    }
    public void priorityItem(Task task)
    {
        taskViewModel.setPriority(task);
    }

    public void addTask()
    {
        Intent intent = new Intent(getActivity(), AddNewTaskActivity.class);
        intent.putExtra(AddNewTaskActivity.EXTRA_TYPE, "ADD");
        startActivityForResult(intent, ADD_TASK_REQUEST);
    }
    public void editTask(Task task)
    {
        Intent intent = new Intent(getActivity(), AddNewTaskActivity.class);
        intent.putExtra(AddNewTaskActivity.EXTRA_TYPE, "UPDATE");
        intent.putExtra(AddNewTaskActivity.EXTRA_ID, task.getId());
        intent.putExtra(AddNewTaskActivity.EXTRA_TITLE, task.getTitle());
        intent.putExtra(AddNewTaskActivity.EXTRA_DESCRIPTION, task.getDescription());
        intent.putExtra(AddNewTaskActivity.EXTRA_DATE, task.getDate());
        intent.putExtra(AddNewTaskActivity.EXTRA_COMPLETED, String.valueOf(task.isCompleted()));
        intent.putExtra(AddNewTaskActivity.EXTRA_PRIORITY, String.valueOf(task.getHasPriority()));
        intent.putExtra(AddNewTaskActivity.EXTRA_DUETIME, task.getDueTime());
        startActivityForResult(intent, EDIT_TASK_REQUEST);
    }

    public void setUpViewModel()
    {
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        taskViewModel.getTasksByDate(taskViewModel.currentDateTaskFormat()).observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK)
        {
            handleAddRequest(data);
            dateTextView.setText(taskViewModel.getCurrentDate(value));
            dateCount = 0;
        }
        else if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK)
        {
            handleUpdateRequest(data);
        }
        else {
            Toast.makeText(getActivity(), "Task not saved", Toast.LENGTH_SHORT).show();
            dateTextView.setText(taskViewModel.getCurrentDate(value));
            dateCount = 0;
        }

    }
    public void handleAddRequest(@Nullable Intent data)
    {
        String title = data.getStringExtra(AddNewTaskActivity.EXTRA_TITLE);
        String description = data.getStringExtra(AddNewTaskActivity.EXTRA_DESCRIPTION);
        String date = data.getStringExtra(AddNewTaskActivity.EXTRA_DATE);



        Task task = new Task(title, description, date, 0, 0, "");
        taskViewModel.create(task);
        Toast.makeText(getActivity(), "Task saved", Toast.LENGTH_SHORT).show();
    }
    public void handleUpdateRequest(@Nullable Intent data)
    {
        int id = data.getIntExtra(AddNewTaskActivity.EXTRA_ID, -1);

        if(id == -1)
        {
            Toast.makeText(getActivity(), "Task cannot be updated", Toast.LENGTH_SHORT).show();
            return;
        }
        String title = data.getStringExtra(AddNewTaskActivity.EXTRA_TITLE);
        String description = data.getStringExtra(AddNewTaskActivity.EXTRA_DESCRIPTION);
        String date = data.getStringExtra(AddNewTaskActivity.EXTRA_DATE);
        int isCompleted = Integer.parseInt(data.getStringExtra(AddNewTaskActivity.EXTRA_COMPLETED));
        int hasPriority = Integer.parseInt(data.getStringExtra(AddNewTaskActivity.EXTRA_PRIORITY));
        String dueTime = data.getStringExtra(AddNewTaskActivity.EXTRA_DUETIME);

        Task task = new Task(title, description, date, isCompleted, hasPriority, dueTime);
        task.setId(id);
        taskViewModel.update(task);
        Toast.makeText(getActivity(), "Task updated", Toast.LENGTH_SHORT).show();
    }

    public void swipeToDelete() { {
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    switch (direction) {
                        case ItemTouchHelper.LEFT:
                            taskViewModel.delete(adapter.getTaskAtPosition(viewHolder.getAdapterPosition()));
                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }).attachToRecyclerView(recyclerView);
        }
    }


}