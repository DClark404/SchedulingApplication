# SchedulingApplication
This project was awarded the Western Governors University Excellence Award for Software II - Advanced Java Concepts.

  A Java application that uses JavaFX to create GUI elements and mySQL Connector to push SQL queries to a backend server. Capable of accessing and maintaining customer records as well as appointments. This project was an exercise in building Model-view-controller patterns for applications. Includes utility classes for automatically translating language and time zone of the application and appointments to the settings of the host machine.

  Users begin at a login page that includes error control messages. Login attempts are logged to an internal file including time of attempted login, success, and time zone of attempted login.
  
  All recorded times are automatically translated to local user time zones. Reports are generated that list appointments by month and week. Exception control is implemented to prevent scheduling overlapping appointments. This application also uses two lambda expressions for filtering lists and removing appointment by ID. Users are given reminder popups 15 minutes in advance of an appointment, based on the user that is logged in and which appointments are scheduled for them. 
