Feature: On startup it reads tasks from a given repository
	As a user 
	I want to read tasks from disk
	So that I don't have to enter all tasks again each time

Background:
	Given a directory containing a task repository
	And the task repository contains the following tasks
	|name | description | start         | end            | isCompleted |
	| 1st | do something|01-01-16 07:00 | 01-01-16 16:00 | false       |
	| 2nd | do things   |01-02-16 08:00 | 01-02-16 16:30 | false       |
	
Scenario:
	Given the user starts the task manager
	When the user performs the action 'display'
	Then the tasks from the default task repo are shown