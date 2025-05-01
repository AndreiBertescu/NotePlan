# NotePlan
![image](https://github.com/AndreiBertescu/NotePlan/assets/126001291/b532aee9-1a74-48ac-9c0a-934355d98981)

NotePlan is an app where you can centralize all your events and notes. The app itself is built using Spring Boot and Spring Security. All data is stored on a MySQL server, and communication between the database and the Java applet is facilitated by Hibernate. I have designed all the various web pages for the app, except for the semantic calendar, which was inspired by a YouTube video. [Video link](https://www.youtube.com/watch?v=6EVgmpm4z5U&t=1939s).

## Notes
- To access the site locally without Docker, uncomment the `spring.profiles.active = dev` line, set the required environment variables, build the project using Maven, and open the following URL in your browser: <http://localhost:8080/>.
- To run the project locally, a MySQL database must be created and running on your machine. Additionally, the ${EMAIL} and ${EMAIL_PASSWORD} placeholders in the `application.properties` file need to be replaced with actual values.
- An easier alternative is to use the provided `docker-compose.yml` file, which sets up both the application and the MySQL database. Once running, access the app at the same URL.
- The project is also deployed on Railway for a limited time at the following link: [NotePlan](https://noteplan-production.up.railway.app/).
- Development was done in **Eclipse IDE for Java EE Developers**, with the **Spring Tool Suite 4.29.0** plugin installed. The SQL components were developed using MySQL Server and MySQL Workbench.
- To deploy the project on Railway, a MySQL service must be created. Then, within the project’s Railway service, all relevant environment variables from `application.properties` must be configured.

## Roadmap
- Add Junit tests
- Make the site compatible with smaller screen displays.
- Develop a mobile app version of NotePlan.

## Version 1.5
- Successfully dockerized the application. Created a `Dockerfile` to containerize the Spring Boot app and a `docker-compose.yml` file that includes a MySQL service, streamlining local development and testing.
- Set up a CI workflow using GitHub Actions to automatically build the Docker image and publish it to the [GitHub Container Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry).
- Ensured environment variables and MySQL connection configuration are properly passed at runtime via Docker Compose or Railway.
- Addressed memory usage issues that impacted deployment on platforms with limited resources like Railway.

## Version 1.4
- Added GitHub Actions and Git rules: they lint the entire codebase, verify that the latest commit is configured for the production environment, and wait for reviewer approval before allowing deployment.
- Added various performance tests.
- Included VisualVM profiler results in the performance_reports directory.
- Added JMeter tests to measure HTTPS request response times for login, data insertion into the MySQL database, and logout. These results have been compiled into an HTML dashboard available in the performance_reports directory.

## Version 1.3
- Added two working profiles: production and development.
- Added remember me functionality.
- Added error mesages for reused email on the register page and invalid email or password for the login page
- Added view password button to the various password inputs.
- Fixed a bug with the descriptions not being shown with line breaks.

## Version 1.2
- Added a profile page where you can view your email, name, and preferences.
- Added the ability to delete your account.
- Added preferences options: time format and theme.
- The time format allows you to choose between 'military' time and am/pm time.
- There are two available themes: light and dark.

## Version 1.1
- Added a proper URL name
- Added small features to the web page.
- Made central calendar responsive to the events on the left.

## Version 1.0
- Achieved minimum viable product!
- Added "save event" and "save note" functionality.
- Added input size limits on the HTML pages and sql database.
- Added ability to view events and notes.
- Added ability to delete and update events and notes.
- Small visual changes

## Version 0.9
- The project was added on GitHub.
- The account system was fully completed: you can now register using only an email address and password. An email verification system was added to ensure there is only one account per email address.
- The HTML dashboard page, the main page of the project, is mostly finished. Small modifications can still be made, though.

## Images
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
