package com.example.foodknowledgehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // List to do:
    /*
    * Image for micro and vitamin
    * bulk api for vitamin, micro and macro
    * unit test for micro, macro and vitamin service
    * unit test for micro, macro and vitamin API's
    * Login lock wall before  viewing full data / first 5 rows free
    * pagination / loading
    * rate limiting
    * improve image loading, current problem data + image loads longer
     * */
}
