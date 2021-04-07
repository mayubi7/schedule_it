# Schedule App



**Project Proposal:**

The application I am designing is a scheduling app. This app will help keep track of events and todo lists; hence,
boosting one's productivity. A user can schedule tasks into a 24-hour schedule. In the future steps of the project 
I would like to create a weekly view spread which would show the schedule for the entire week. Additionally, I would like 
to add tags which can be set for a certain colour so that tasks have a certain colour representing if they are school,
 work, events, etc. 
 
 Some Features I'd like to add throughout the term:
 - weekly view showing schedule of the week ahead
 - tags and colours so that the tasks can be organized by category
 - possibly a habit tracker that helps track daily, weekly or monthly habits of one's choice
 
 
 This app can be used by anyone who wants to organize their time. It may be especially useful for students in helping 
 them keep track of assignments and schedule their study time. This project interests me because I have seen the 
 benefits of using a planner and scheduling time in my own life. The act has always been important in boosting 
 productivity; however, with the current pandemic and the shift towards online classes it's usefulness has only 
 increased. I have found that todo lists can be useful; however, the act of scheduling each task to a certain
  time has a significant impact on one's productivity and allows for more free-time! 
 
 ##User Stories
 - As a user, I want to be able to any number of tasks with their times to my schedule
 - As a user, I want to be able to mark a task as completed on my weekly schedule
 - As a user, I want to be able to reschedule a task to a different time on my schedule
 - As a user, I want to be able to delete a task from my schedule
 - As a user, I want to be able to view a list of all incomplete tasks for the day
 
 
##Phase 2 User Stories
- As a user, I want to be able to save my schedule to file
- As a user, I want to be able to load my schedule from file

##Phase 4: Task 2
- The class Schedule is robust, methods addNewTask, reschedule and deleteTask have been modified 
 
 ##Phase 4: Task 3
 - In order to improve SRP, I would pull out buttons from ScheduleItApplication and give it a new class.
 The ScheduleItApplication will then be only responsible for adding each part to the JFrame. 
 This would also improve the readability of the class. 
 
 - The buttons have lots of duplicated code, so I would design a helper function that would hold these lines of code. 
 Then, the functions making the buttons can call this helper function instead. 
 
 
 
 
 
 