package com.example.sem4_andproject.ui.charts;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sem4_andproject.R;
import com.example.sem4_andproject.data.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartsFragment extends Fragment {

    PieChartView pieChartView;
    ChartsViewModel viewModel;

    List<Task> checkedTasks;
    List<Task> uncheckedTasks;

    List<Task> weeklyCheckedTasks;
    List<Task> weeklyUncheckedTasks;

    TextView checked;
    TextView unchecked;

    public static final int CHECKED = -16850210;
    public static final int UNCHECKED = -18059920;

    TextView week;
    TextView today;


    public ChartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        setUpViewModel();
        setElements(view);

        return view;
    }

    public void setElements(View view) {
        checkedTasks = new ArrayList<>();
        uncheckedTasks = new ArrayList<>();

        weeklyCheckedTasks = new ArrayList<>();
        weeklyUncheckedTasks = new ArrayList<>();

        checked = view.findViewById(R.id.checkedView);
        unchecked = view.findViewById(R.id.uncheckedView);
        checked.setTextColor(CHECKED);
        unchecked.setTextColor(UNCHECKED);


        pieChartView = view.findViewById(R.id.pieChart);

        week = view.findViewById(R.id.weekCharts);
        today = view.findViewById(R.id.todaysCharts);

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeeklyStats();
            }
        });
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpViewModel();
            }
        });

    }

    public void setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChartsViewModel.class);
        viewModel.getCheckedTasks(viewModel.currentDateTaskFormat()).observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                checkedTasks = tasks;
                putDataInPieForToday();
            }
        });
        viewModel.getUncheckedTasks(viewModel.currentDateTaskFormat()).observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                uncheckedTasks = tasks;
                putDataInPieForToday();
            }
        });
    }

    public void putDataInPieForToday() {
        List pieData = new ArrayList();
        pieData.add(new SliceValue(checkedTasks.size(), CHECKED));
        pieData.add(new SliceValue(uncheckedTasks.size(), UNCHECKED));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartView.setPieChartData(pieChartData);
    }

    public void getWeeklyStats() {
        viewModel.getWeekCheckTask(viewModel.getCurrentDisplayedDateMinusOne(-6), viewModel.currentDateTaskFormat()).observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                weeklyCheckedTasks = tasks;
                putInWeeklyDate();
            }
        });
        viewModel.getWeekUncheckTask(viewModel.getCurrentDisplayedDateMinusOne(-6), viewModel.currentDateTaskFormat()).observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                weeklyUncheckedTasks = tasks;
                putInWeeklyDate();
            }
        });
    }

    public void putInWeeklyDate() {
        List pieData = new ArrayList();
        pieData.add(new SliceValue(weeklyCheckedTasks.size(), CHECKED));
        pieData.add(new SliceValue(weeklyUncheckedTasks.size(), UNCHECKED));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartView.setPieChartData(pieChartData);
    }

}