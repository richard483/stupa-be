package com.stupa.stupa_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StupaBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(StupaBackendApplication.class, args);
  }

}
