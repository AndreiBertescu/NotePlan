package com.noteplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotePlanApplication {

    /**
     * web app starting point.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(final String[] args) {
        SpringApplication.run(NotePlanApplication.class, args);
    }

}
