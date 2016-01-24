# task-manager
JCP exercise (code a task manager without an IDE)<BR>
<BR>
Requirements:<BR>
1. Console app, no fancy UI<BR>
2. Code with a plain text editor<BR>
3. It shall present a menu with the following options:<BR>
    0) Add a task<BR>
    1) List all tasks<BR>
    2) Modify a task<BR>
    3) Delete a task<BR>
    4) Exit<BR>
4. Use the following classes: FileWriter, FileReader, BufferedReader, BufferedWriter, Path, Paths<BR>
5. A task has a name, start and end time<BR>
6. Added by me: A task has a description and a completed flag<BR>
7. Added by me: The tool uses a taskrepository. It's location will be used as a command line argument<BR>
8. Added by me: The tool reads the task repository on startup and imports all files in the taskRepo into tasks<BR>
9. Added by me: The tool writes to the task repository when exiting the tool. Existing task files will be overridden.<BR>
<BR>
TODO:<BR>
1. When deleting a task, the task file must also be deleted.<BR>
2. When creating a new task, a check on whether it already exists must be done<BR>
