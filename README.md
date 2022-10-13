# SchedulingApplication
A contact management and scheduling application created using JavaFX and mySQL.

  A Java application that uses JavaFX to create GUI elements and mySQL Connector to push SQL queries to a backend server. Capable of accessing and maintaining customer records as well as appointments. 

  Users begin at a login page that includes error control messages. Login attempts are logged to an internal file including time of attempted login, success, and time zone of attempted login.
  
  All recorded times are automatically translated to local user time zones. Appointments are able to be viewed by calender month and week. Exception control is implemented to prevent scheduling overlapping appointments. This application also uses two lambda expressions for filtering lists and removing appointment by ID. Users are given reminder popups 15 minutes in advance of an appointment, based on the user that is logged in and which appointments are scheduled for them.
