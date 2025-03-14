# NotePlan
![image](https://github.com/AndreiBertescu/NotePlan/assets/126001291/b532aee9-1a74-48ac-9c0a-934355d98981)

NotePlan is an app where you can centralize all your events and notes. The app itself is built using Spring Boot and Spring Security. All data is stored on a MySQL server, and communication between the database and the Java applet is facilitated by Hibernate. I have designed all the various web pages for the app, except for the semantic calendar, which was inspired by a YouTube video. [Video link](https://www.youtube.com/watch?v=6EVgmpm4z5U&t=1939s).

# Notes:
- To access the website on the development profile use the URL: http://localhost/noteplan.ro/
- The project is also deployed for a limitied amount of time at this link: [NotePlan](https://noteplan-production.up.railway.app/)
- To run the project locally, a MYSQL database needs to be created according to the specifications in the "application-dev.properties" file. Additionally, the variables ${EMAIL} and ${EMAIL_PASSWORD} in the "application.properties" file need to be replaced with actual values.
- The project was developed in **Eclipse IDE for Java EE Developers**, with the **Spring Tool Suite 4.29.0** package installed. The sql aspect of the app was made using MYSQL Server and Workbench apps.
- To deploy the project on Railway, a MySQL service needs to be created. Additionally, within the project service, the environment variables from the "application.properties" file need to be added.

# Roadmap:
- Add CI/CD Actions & Git Rules
- Performance tests and profiling the application
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

# Images:
- Main dashboard page
![image](https://github.com/AndreiBertescu/NotePlan/assets/126001291/13c381ef-0e38-4ecc-830b-8c79e23e0c81)<br>

- Profile page
![image](https://github.com/AndreiBertescu/NotePlan/assets/126001291/1c285a17-9405-4685-a596-1d48802476b3)<br>

- Edit event and edit note overlays
<p align="center">
  <img src="https://github.com/AndreiBertescu/NotePlan/assets/126001291/0ae7f7f1-8471-4548-b105-78eae28db51c" height="400" alt="Image 1">
  <img src="https://github.com/AndreiBertescu/NotePlan/assets/126001291/bc40c94a-c51b-499f-a02b-a9dcb7bc0e23" height="400" alt="Image 2">
</p><br>

- Register and login pages
<p align="center">
  <img src="https://github.com/AndreiBertescu/NotePlan/assets/126001291/94bf61ef-cb5f-4483-9e72-230bc78fba96" height="400" alt="Image 3">
  <img src="https://github.com/AndreiBertescu/NotePlan/assets/126001291/04e7c755-29f5-490a-9b24-e79959998269" height="400" alt="Image 4">
</p>
