package com.ourfancyteamname.officespace;

import com.ourfancyteamname.officespace.db.OfficeSpaceRepositoryApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import(OfficeSpaceRepositoryApplication.class)
public class OfficeSpaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OfficeSpaceApplication.class);
  }

}
