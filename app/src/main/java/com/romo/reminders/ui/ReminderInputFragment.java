package com.romo.reminders.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.romo.reminders.R;
import com.romo.reminders.data.Reminder;
import com.romo.reminders.util.ReminderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

public class ReminderInputFragment extends Fragment {

    OnReminderAddedListener callback;

    @BindView(R.id.completion_input) CheckBox completionInput;
    @BindView(R.id.title_input) EditText titleInput;
    @BindView(R.id.priority_input) ImageView priorityInput;

    private Unbinder unbinder;

    private boolean inputMode;

    private boolean completed;
    private int priority;

    interface OnReminderAddedListener {
        void onReminderAdded(Reminder reminder);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnReminderAddedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_input, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        if (inputMode) {
            getActivity().getMenuInflater()
                    .inflate(R.menu.fragment_reminder_input, menu);
        } else {
            super.onPrepareOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                clearInputs();
                return true;
            case R.id.add_reminder:
                addReminder();
                clearInputs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnCheckedChanged(R.id.completion_input)
    protected void completionChangeListener(boolean isCompleted) {
        completed = isCompleted;
    }

    @SuppressWarnings("ConstantConditions")
    @OnFocusChange(R.id.title_input)
    protected void inputModeListener(boolean hasFocus) {
        AppCompatActivity parent = (AppCompatActivity) getActivity();

        if (hasFocus) {
            inputMode = true;
            parent.getSupportActionBar()
                    .setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
            parent.getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
        } else {
            inputMode = false;
            parent.getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(false);
        }

        getActivity().invalidateOptionsMenu();
    }

    @OnEditorAction(R.id.title_input)
    protected boolean titleInputEditorActionListener(int actionId) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                addReminder();
                clearInputs();
                return true;
            default:
                return false;
        }
    }

    @OnClick(R.id.priority_input)
    protected void priorityChangeListener() {
        if (++priority > ReminderUtils.MAX_PRIORITY_LEVEL) {
            priority = 0;
        }

        priorityInput.setColorFilter(ContextCompat.getColor(getActivity(),
                ReminderUtils.getPriorityStripColorRes(priority))
        );
    }

    private void addReminder() {
        Reminder reminder = new Reminder(titleInput.getText().toString(),
                priority, completed);
        callback.onReminderAdded(reminder);
    }

    private void clearInputs() {

        // Hide the keyboard
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        titleInput.setText("");
        titleInput.clearFocus();

        completionInput.setChecked(false);

        priority = ReminderUtils.MIN_PRIORITY_LEVEL;
        priorityInput.setColorFilter(ContextCompat.getColor(getActivity(),
                ReminderUtils.getPriorityStripColorRes(priority))
        );
    }
}
