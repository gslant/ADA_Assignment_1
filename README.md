# ADA_Assignment_1
A Java Threadpool implementation made for COMP611

A task can be made to be executed by the threadpool simply by extending the Task class, and overriding the run method. The CipherTask example I have created utilises the Observer Design Pattern, whereby CipherTask notifies all subscribed listeners upon completing the task with the final encrypted message
