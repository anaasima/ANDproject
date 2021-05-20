package com.example.sem4_andproject.ui.popUp;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;

import com.example.sem4_andproject.R;
import com.example.sem4_andproject.ui.seeTasks.TaskViewModel;
import com.example.sem4_andproject.data.tasks.Task;

public class PopUpFragment{

    NumberPicker hourPicker;
    NumberPicker minutePicker;
    Button saveTime;
    TaskViewModel taskViewModel;

    PopupWindow popupWindow;

    public PopUpFragment(final View view, Fragment fragment, Task task, FrameLayout layout) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.fragment_pop_up, null);
        setViewModel(fragment);
        setUpElements(popUpView, task, layout);
        setNumberPickers();

        boolean focusable = false;
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        popupWindow = new PopupWindow(popUpView, width, height, focusable);
        popupWindow.setOutsideTouchable(false);

    }

    public void showPopupWindow(final View view)
    {
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    public void dismissPopupWindow(FrameLayout homeLayout)
    {
        popupWindow.dismiss();
        homeLayout.getForeground().setAlpha(0);
    }
    public void setUpElements(View view, Task task, FrameLayout layout)
    {
        hourPicker = view.findViewById(R.id.hourPicker);
        minutePicker = view.findViewById(R.id.minutePicker);
        saveTime = view.findViewById(R.id.saveTime);


        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTime(task);
                dismissPopupWindow(layout);
            }
        });
    }
    public void saveTime(Task task)
    {
        setTime(task.getId(), hourPicker.getValue(), minutePicker.getValue());
    }
    public void setTime(int taskId, int hour, int minute)
    {
        String stringHour = "";
        String stringMinute = "";
        if (hour<10)
            stringHour = "0" + hour;
        else
            stringHour = String.valueOf(hour);
        if(minute < 10)
            stringMinute = "0" + minute;
        else
            stringMinute = String.valueOf(minute);
        String time = stringHour + ":" + stringMinute;
        taskViewModel.setDueTime(time, taskId);;
    }
    public void setViewModel(Fragment fragment)
    {
        taskViewModel = ViewModelProviders.of(fragment).get(TaskViewModel.class);
    }
    public void setNumberPickers() {
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setDisplayedValues(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"});

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setDisplayedValues(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"
                , "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "55", "56", "57", "58", "59"});
    }

}