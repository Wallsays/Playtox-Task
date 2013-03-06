Test task for Playtox company

You'll need to have Maven installed to run this application.  

To run the application, type: 

    mvn jetty:run-war
    
To view the running application, visit the following URL in your web browser:

    http://localhost:8080/TestTask
    
The application uses PostgreSQL & Hibernate   
Connection settings can be found in hibernate.cfg.xml where "DemoTask" your DB in PSQL, "manager"/"123" - username/password. 
Also you must change settings in applicationContextPSQL.xml file accordingly.

(After deploying in eclipse you can start app via Start file in test/java/com.wicket and add test-records using FillingContactBook from package com.tools)