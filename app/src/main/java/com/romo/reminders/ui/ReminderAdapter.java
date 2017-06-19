package com.romo.reminders.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.romo.reminders.R;
import com.romo.reminders.data.Reminder;
import com.romo.reminders.util.ReminderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminderList;

    public void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
        notifyDataSetChanged();
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int itemViewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderAdapter.ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);

        holder.priorityStrip.setBackgroundResource(
                ReminderUtils.getPriorityStripColorRes(reminder.getPriority())
        );

        holder.completionCheckBox.setChecked(reminder.isCompleted());

        holder.titleField.setText(reminder.getTitle());
    }

    @Override
    public int getItemCount() {
        if (reminderList == null) {
            return 0;
        }
        return reminderList.size();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.priority_strip) View priorityStrip;
        @BindView(R.id.completion_check_box) CheckBox completionCheckBox;
        @BindView(R.id.title_field) TextView titleField;
        @BindView(R.id.handle) ImageView handle;
        @BindView(R.id.divider) View divider;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
