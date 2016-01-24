# task-manager
JCP exercise (code a task manager without an IDE) 
Requirements:
1. Console app, no fancy UI
2. Code with a plain text editor
3. It shall present a menu with the following options:
  0) Add a task
  1) List all tasks
  2) Modify a task
  3) Delete a task
  4) Exit
4. Use the following classes: FileWriter, FileReader, BufferedReader, BufferedWriter, Path, Paths
5. A task has a name, start and end time
6. Added by me: A task has a description and a completed flag
7. Added by me: The tool uses a taskrepository. It's location will be used as a command line argument
8. Added by me: The tool reads the task repository on startup and imports all files in the taskRepo into tasks
9. Added by me: The tool writes to the task repository when exiting the tool. Existing task files will be overridden.

TODO:
1. When deleting a task, the task file must also be deleted. 
2. When creating a new task, a check on whether it already exists must be done
