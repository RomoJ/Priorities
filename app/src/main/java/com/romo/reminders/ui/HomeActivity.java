package com.romo.reminders.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.romo.reminders.R;
import com.romo.reminders.data.Reminder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements ReminderInputFragment.OnReminderAddedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            return;
        }

        RemindersFragment remindersFragment = new RemindersFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.reminders_container, remindersFragment).commit();

        ReminderInputFragment reminderInputFragment = new ReminderInputFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.reminder_input_container, reminderInputFragment).commit();
    }

    @Override
    public void onReminderAdded(Reminder reminder) {
        Toast.makeText(this, "Reminder Added", Toast.LENGTH_SHORT).show();
    }
}
