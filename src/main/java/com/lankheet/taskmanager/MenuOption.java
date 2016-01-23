package com.lankheet.taskmanager;

public enum MenuOption {
    /** Create a new task with a name and description */
    CREATE_TASK("Create task", "Create a new task"),
    /** Read all tasks */
    READ_ALL_TASKS("Read all tasks", "Read all tasks"),
    /** Modify task with a given taskName (or part of the task name for version 2.0)*/
    MODIFY_TASK("Modify task", "Modify a specific task"), 
    /** Delete task with a given taskName (or part of the task name for version 2.0)*/
    DELETE_TASK("Delete a task", "Delete a specific task"),
    DONE("Done", "Quitting task manager");
    
    String taskName;
    String taskDescription;
    
    private MenuOption(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }
    
    public String getTaskName() {
        return this.taskName;
    }
    
    public String getDescription() {
        return taskDescription;
    }
}

