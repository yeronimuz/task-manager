package com.lankheet.taskmanager;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TaskItem {

    protected static final int NR_OF_ITEMS = 5;
    
    private String name;
    
    private String description;
    
    private Date start;
    
    private Date end;
    
    private boolean isCompleted;
    
    public TaskItem(String name, String description, Date start, Date end, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.isCompleted = isCompleted;
    }
    
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
    
    public void setStart(Date start) {
        // TODO: Check start is before end time
        this.start = start;
    }
    
    public void setEnd(Date end) {
        // TODO: Check end time is after start time
        this.end = end;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Date getStart() {
        return start;
    }
    
    public Date getEnd() {
        return end;
    }
    
    public void setIsCompleted(boolean isCompleted) {
       this.isCompleted = isCompleted;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat(TaskManager.DATE_FORMAT + " a");

        return new String("Task { Name = \"" + name + 
            "\", description = \"" + description + 
            "\", start = " + fmt.format(start) + 
            ", end = " + fmt.format(end) + 
            ", isCompleted = " + (isCompleted?"true":"false") + " }");
    }
}
