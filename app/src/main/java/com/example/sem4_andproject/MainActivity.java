package com.example.sem4_andproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sem4_andproject.data.tasks.Task;
import com.example.sem4_andproject.ui.addTask.AddNewTaskActivity;

import com.example.sem4_andproject.ui.charts.ChartsFragment;
import com.example.sem4_andproject.ui.quotes.QuoteFragment;
import com.example.sem4_andproject.ui.seeTasks.TaskAdapter;
import com.example.sem4_andproject.ui.settings.SettingsFragment;
import com.example.sem4_andproject.ui.seeTasks.TaskFragment;
import com.example.sem4_andproject.ui.seeTasks.TaskViewModel;
import com.example.sem4_andproject.ui.signIn.SignInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity  {
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;

    public static final String CHANNEL_ID = "Channel Id";

    private TaskViewModel taskViewModel;

    private TextView username;

    private MenuItem home;
    private MenuItem charts;
    private MenuItem settings;
    private MenuItem signOut;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViewModel();
        setButtons();
        checkIfSignedIn();
        openHomePage();

    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {

    if(toggle.onOptionsItemSelected(item))
        return true;

    return super.onOptionsItemSelected(item);
}
    public void setButtons()
    {
        home = findViewById(R.id.homeMenu);
        charts = findViewById(R.id.chartsMenu);
        settings = findViewById(R.id.settingsMenu);
        signOut = findViewById(R.id.signoutMenu);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nv);
        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.username);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.homeMenu:
                        openHomePage();
                        break;
                    case R.id.chartsMenu:
                        openCharts();
                        break;
                    case R.id.quoteMenu:
                        openQuote();
                        break;
                    case R.id.settingsMenu:
                        openSettings();
                        break;
                    case R.id.signoutMenu:
                        signOut();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void openQuote() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new QuoteFragment(), "QuoteFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void openSettings()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new SettingsFragment(), "SettingsFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void setUpViewModel()
    {
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
    }
    private void checkIfSignedIn()
    {
        taskViewModel.getCurrentUser().observe(this, user -> {
            if(user != null)
            {
                username.setText(user.getDisplayName());
            }
            else
                startLoginActivity();
        });
    }
    private void startLoginActivity()
    {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
    public void signOut()
    {
        taskViewModel.signOut();
    }
    public void openHomePage()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new TaskFragment(), "TaskFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void openCharts()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new ChartsFragment(), "ChartsFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

}