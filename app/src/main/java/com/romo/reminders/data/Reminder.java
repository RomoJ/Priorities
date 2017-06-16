package com.romo.reminders.data;

/**
 * Model class for a reminder.
 */
public class Reminder {

    private int id;
    private String title;
    private int priority;
    private boolean completed;
    private int listPosition;

    /**
     * Use this constructor to create a new Reminder.
     *
     * @param title     title of the reminder
     * @param priority  priority of the reminder
     * @param completed true if the reminder is completed, false otherwise
     */
    public Reminder(String title, int priority, boolean completed) {
        this.title = title;
        this.priority = priority;
        this.completed = completed;
    }

    /**
     * Use this constructor to create a new Reminder that has already been assigned an id and list
     * position.
     *
     * @param id           id of the reminder
     * @param title        title of the reminder
     * @param priority     priority of the reminder
     * @param completed    true if the reminder is completed, false otherwise
     * @param listPosition list position of the reminder
     */
    public Reminder(int id, String title, int priority, boolean completed, int listPosition) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = completed;
        this.listPosition = listPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}
