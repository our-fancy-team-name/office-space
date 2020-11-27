package com.ourfancyteamname.officespace;

import com.ourfancyteamname.officespace.db.OfficeSpaceRepositoryApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OfficeSpaceRepositoryApplication.class)
public class OfficeSpaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OfficeSpaceApplication.class);
  }

}
