# NotePlan

![Image](https://github.com/AndreiBertescu/NotePlan/assets/126001291/b532aee9-1a74-48ac-9c0a-934355d98981)

---

NotePlan is an app where you can centralize all your events and notes. The app itself is built using Spring Boot and Spring Security. All data is stored on a MySQL server, and communication between the database and the Java applet is facilitated by Hibernate. I have designed all the various web pages for the app, except for the semantic calendar, which was inspired by a YouTube video. [Video link](https://www.youtube.com/watch?v=6EVgmpm4z5U&t=1939s).

# Notes:
- To access the website on the development profile use the URL: http://localhost/noteplan.ro/
- The project is also deployed for a limitied ammount of time at this link: [NotePlan](https://noteplan-production.up.railway.app/)
- To run the project locally, a database needs to be created according to the specifications in the "application-dev.properties" file. Additionally, the variables ${EMAIL} and ${EMAIL_PASSWORD} in the "application.properties" file need to be replaced with actual values.
- To deploy the project on Railway, a MySQL service needs to be created. Additionally, within the project service, the environment variables from the "application.properties" file need to be added.

# Roadmap:
- Make the website compatible with smaller screen displays.
- Develop a mobile app version of NotePlan.

# Version 1.3:
- Added two working profiles: production and development.
- Added remember me functionality.
- Added error mesages for reused email on the register page and invalid email or password for the login page
- Added view password button to the various password inputs.
- Fixed a bug with the descriptions not being shown with line breaks.

# Version 1.2:
- Added a profile page where you can view your email, name, and preferences.
- Added the ability to delete your account.
- Added preferences options: time format and theme.
- The time format allows you to choose between 'military' time and am/pm time.
- There are two available themes: light and dark.

# Version 1.1:
- Added a proper URL name
- Added small features to the web page.
- Made central calendar responsive to the events on the left.

# Version 1.0:
- Achieved minimum viable product!
- Added "save event" and "save note" functionality.
- Added input size limits on the html pages and sql database.
- Added ability to view events and notes.
- Added ability to delete and update events and notes.
- Small visual changes

# Version 0.9:
- The project was added on GitHub.
- The account system was fully completed: you can now register using only an email address and password. An email verification system was added to ensure there is only one account per email address.
- The HTML dashboard page, the main page of the project, is mostly finished. Small modifications can still be made, though.
