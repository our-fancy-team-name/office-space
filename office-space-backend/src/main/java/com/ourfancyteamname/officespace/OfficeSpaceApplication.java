package com.ourfancyteamname.officespace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ourfancyteamname.officespace.postgres.converters")
public class OfficeSpaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OfficeSpaceApplication.class);
  }

}
