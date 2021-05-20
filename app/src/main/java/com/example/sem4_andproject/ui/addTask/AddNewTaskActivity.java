package com.example.sem4_andproject.ui.addTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sem4_andproject.R;

public class AddNewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.sem4_andproject.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.sem4_andproject.EXTRA_DESCRIPTION";
    public static final String EXTRA_ID = "com.example.sem4_andproject.EXTRA_ID";
    public static final String EXTRA_DATE = "com.example.sem4.andproject.EXTRA_DATE";
    public static final String EXTRA_COMPLETED = "com.example.sem4.andproject.EXTRA_COMPLETED";
    public static final String EXTRA_PRIORITY = "com.example.sem4.andproject.EXTRA_PRIORITY";
    public static final String EXTRA_DUETIME = "com.example.sem4.andproject.EXTRA_TIME";
    public static final String EXTRA_TYPE = "com.example.sem4.andproject.EXTRA_TYPE";

    EditText editTextTitle;
    EditText editTextDescription;
    CalendarView calendarView;
    String date1;
    String taskDate;
    String priority;
    String completed;
    String dueTime;

    AddNewTaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        setViewModel();
        setElements();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_goback);

        checkTypeOfIntent();

    }
    public void checkTypeOfIntent()
    {
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            taskDate = intent.getStringExtra(EXTRA_DATE);
            completed = intent.getStringExtra(EXTRA_COMPLETED);
            priority = intent.getStringExtra(EXTRA_PRIORITY);
            dueTime = intent.getStringExtra(EXTRA_DUETIME);
        }
        else if (intent.getStringExtra(EXTRA_TYPE).equals("ADD"))
            setTitle("Add Task");

    }
    public void setElements()
    {
        date1 = viewModel.currentDateTaskFormat();
        calendarView = findViewById(R.id.taskCalendar);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edittext_description);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int month1 = month + 1;
                String date = dayOfMonth + "/" + month1;
                if (!date.equals(viewModel.currentDateTaskFormat())) {
                    taskDate = dayOfMonth + "/" + month1;
                }
                date1 = dayOfMonth + "/" + month1;
            }
        });

    }
    public void setViewModel()
    {
        viewModel = ViewModelProviders.of(this).get(AddNewTaskViewModel.class);
    }
    private void createUpdateTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Title or description are empty", Toast.LENGTH_LONG);
            return;
        }
        Intent data = new Intent();
        String type = getIntent().getStringExtra(EXTRA_TYPE);
        if (type.equals("ADD"))
        {
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_DATE, date1);
        }
//        data.putExtra(EXTRA_DATE, date1);
        else if (type.equals("UPDATE")) {
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_TITLE, title);
                data.putExtra(EXTRA_DESCRIPTION, description);
                data.putExtra(EXTRA_ID, id);
                data.putExtra(EXTRA_DATE, taskDate);
                data.putExtra(EXTRA_COMPLETED, completed);
                data.putExtra(EXTRA_PRIORITY, priority);
                data.putExtra(EXTRA_DUETIME, dueTime);
            }
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_task:
                createUpdateTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}