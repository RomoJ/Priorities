package com.romo.reminders.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romo.reminders.R;
import com.romo.reminders.data.Reminder;
import com.romo.reminders.util.DatabaseUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RemindersFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Reminder>> {

    private static final int REMINDERS_LOADER_ID = 0;

    @BindView(R.id.reminders) RecyclerView reminders;

    private ReminderAdapter adapter;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        reminders.setLayoutManager(layoutManager);

        getActivity().getSupportLoaderManager()
                .initLoader(REMINDERS_LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<List<Reminder>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Reminder>>(getActivity()) {

            private List<Reminder> reminderList;

            @Override
            protected void onStartLoading() {
                if (reminderList != null) {
                    deliverResult(reminderList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Reminder> loadInBackground() {
                return DatabaseUtils.getReminders(getContext());
            }

            @Override
            public void deliverResult(List<Reminder> data) {
                reminderList = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Reminder>> loader, List<Reminder> data) {
        if (adapter == null) {
            adapter = new ReminderAdapter(data);
            reminders.setAdapter(adapter);
        } else {
            adapter.setReminderList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Reminder>> loader) {
        adapter.setReminderList(null);
    }

    public void addReminder(Reminder reminder) {
        // Intentionally left blank
    }
}
