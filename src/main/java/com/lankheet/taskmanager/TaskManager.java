package com.lankheet.taskmanager;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.NumberFormatException;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TaskManager {

    public static final String DATE_FORMAT = "dd-MM-yy hh:mm";
    
    private Path taskRepositoryLocation;
    
    private List<TaskItem> taskList = new ArrayList();

    public static void main(String[] args) {
    
        if (args.length != 1) {
            System.out.println("Usage: TaskManager <task-repo-location>");
            System.exit(0);
        } 
        
        TaskManager taskMan = new TaskManager();
        
        taskMan.setRepositoryLocation(Paths.get(args[0]).normalize().toAbsolutePath());
        
        taskMan.readFromRepository();
        
        boolean quit = false;
        while (!quit) {
            // Present menu
            taskMan.presentMenu();
            // Wait for input
            System.out.print("Your choice: " );
            int choise = -1;
            try {
                choise = taskMan.readMenuChoise();
            } catch (NumberFormatException ex) {
                System.out.println("That is not a legal choise: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Unable to read from keyboard" + ex.getMessage());
            }
            quit = taskMan.processAction(choise);
        }
    }
    
    private void readFromRepository() {
        File[] taskFiles = taskRepositoryLocation.toFile().listFiles();
        if (taskFiles.length == 0) {
            System.out.println("Task repo is empty");
            return;
        }
        for (File file: taskFiles) {
            System.out.println("Task: " + file.getName());
            // Read file
            try(FileReader fr = new FileReader(file.getAbsolutePath()); BufferedReader br = new BufferedReader(fr)) {
                String[] lines = new String[TaskItem.NR_OF_ITEMS];
                for (int i = 0; i < TaskItem.NR_OF_ITEMS; i++) {
		            lines[i] = br.readLine();
			        System.out.println("\t" + lines[i]);
		        }
		        // Convert txt to Date
                SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
                		        
                taskList.add(new TaskItem(lines[0], lines[1], fmt.parse(lines[2]), fmt.parse(lines[3]), 
                    (lines[4].equals("true")? true : false)));
	        } catch (IOException |ParseException e) {
		        e.printStackTrace();
		        System.exit(0);
		    }
            // Convert to task
            // Store in memory
        }        
    }
    
    private void saveToRepository() {
        // Save each task in it's own file
        for (TaskItem task: taskList) {
            File taskFile = taskRepositoryLocation.resolve(task.getName()).toFile();
            System.out.println(taskFile);
            try(FileWriter fw = new FileWriter(taskFile.getAbsolutePath() + ".task"); BufferedWriter bw = new BufferedWriter(fw)) {
                SimpleDateFormat fmt = new SimpleDateFormat(TaskManager.DATE_FORMAT);
                bw.write(task.getName());
                bw.newLine();
                bw.write(task.getDescription());
                bw.newLine();
                bw.write(fmt.format(task.getStart()));
                bw.newLine();
                bw.write(fmt.format(task.getEnd()));
                bw.newLine();
                bw.write(task.isCompleted()?"true":"false");
                bw.newLine();
                bw.flush();
                fw.flush();
            } catch (IOException ex) {
                System.out.println("Cannot write to file: " + taskFile);
            }
        }
    }
    
    private void presentMenu() {
        int i = 0;
        for (MenuOption menuOption: MenuOption.values()) {
            System.out.println(i++ + ": " + menuOption.getTaskName());
        }
    }
    
    private int readMenuChoise() throws IOException, NumberFormatException {
        int choise = Integer.parseInt(getStringFromKeyboard());
        System.out.println(MenuOption.values()[choise].getDescription());
        return choise;
    }
    
    private String getStringFromKeyboard() {
        String retVal = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            retVal = br.readLine();
        } catch (IOException ex) {
            System.out.println("Fatal error reading keyboard... exiting");
            System.exit(0);
        }
        return retVal;
    }
    /** 
     * process the menu action, return whether to quit or not
     */
    private boolean processAction(int choise) {
        boolean quit = false;
        MenuOption option = MenuOption.values()[choise];
        switch(option) {
            case CREATE_TASK:
                // Create task
                TaskItem task = createTask();
                storeNewTask(task);
                // Store task
                break;
            case READ_ALL_TASKS:
                readAndDisplayTasks();
                break;
            case MODIFY_TASK:
                modifyTask();
                break;
            case DELETE_TASK:
                deleteTask();
                break;
            case DONE:
                quit = true;
                saveToRepository();
                break;
            default:
                System.out.println("Fatal error in handling action: " + option);
                processAction(MenuOption.DONE.ordinal());
        }
        return quit;
    }
 
    private TaskItem createTask() {
        System.out.print("Task name: ");
        String taskName = getStringFromKeyboard();
        System.out.print("Task description: ");
        String taskDescription = getStringFromKeyboard();
        Date start = readDateInput("Start", null);
        Date end = readDateInput("End", null);
        
        TaskItem task = new TaskItem(taskName, taskDescription, start, end, false);
        
        return task;
    }
    
    private void modifyTask() {
        System.out.print("Task to change: ");
        String taskName = getStringFromKeyboard();
        TaskItem task = getTaskItemByName(taskName);
        if (task == null) {
            System.out.println("ERROR: Task \"" + taskName + "\" not found");
            return;
        }
        System.out.print("new Description or return to accept (" + task.getDescription() + ")");
        String str = getStringFromKeyboard();
        if (!str.isEmpty()) {
            task.setDescription(str);
        }
        task.setStart(readDateInput("Enter new start: ", task.getStart()));   
        task.setEnd(readDateInput("Enter new end: ", task.getEnd()));
        System.out.print("isCompleted: ");
        String isCompleted = getStringFromKeyboard();
        if (isCompleted.equals("true")) {
            task.setIsCompleted(true);
        } else if (isCompleted.equals("false")) {
            task.setIsCompleted(false);
        } else {
            System.out.println("Not a legal value for isCompleted: " + isCompleted);
        }
    }
    
    private boolean deleteTask() {
        System.out.print("Enter the task to be deleted: ");
        String taskName = getStringFromKeyboard();
        TaskItem task = getTaskItemByName(taskName);
        if (task == null) {
            return false;
        }
        taskList.remove(task);
        // TODO: Remove from repo as well
        return true;
    }
    
    private TaskItem getTaskItemByName(String taskName) {
        for (TaskItem task: taskList) {
            if (task.getName().equals(taskName)) {
                return task;
            }
        }
        return null;
    }
    
    /**
     * @param text Text that comes before " date <FORMAT>:"
     * @param oldValue Value displayed
     */
    private Date readDateInput(String text, Date oldValue) {
        boolean dateIsFalse = true;
        Date date = null;
        do {
            SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
            System.out.print(text + " date " + DATE_FORMAT + 
                    ((oldValue==null)?"": "(" + fmt.format(oldValue) + ")") + ":");
            String strDate = getStringFromKeyboard();
            if (strDate.isEmpty()) {
                return oldValue;
            }
            try {
                date = fmt.parse(strDate);
                dateIsFalse = false;
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        } while (dateIsFalse);
        
        return date;
    }
   
    private boolean setRepositoryLocation(Path location) {
        // TODO: Check if this is a valid repository location
        if (location.toFile().exists()) {
            this.taskRepositoryLocation = location;
            System.out.println("Using repository: " + location);
            return true;
        } else {
            System.out.println("ERROR: No such repository: " + location);
        }
        return false;
    }
    
    private void readAndDisplayTasks() {
        // TODO: Read from disk
        if (taskList.isEmpty()) {
            System.out.println("No task in list");
        }
        for (TaskItem task: taskList) {
            System.out.println(task);
        }
    }
    
    private void storeNewTask(TaskItem task) {
        // TODO: Check for existence; don't store two same tasks
        taskList.add(task);
    }
}
