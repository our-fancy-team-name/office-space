package com.ourfancyteamname.officespace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import com.ourfancyteamname.officespace.db.OfficeSpaceRepositoryApplication;

import lombok.Generated;

@EnableCaching
@SpringBootApplication
@Import(OfficeSpaceRepositoryApplication.class)
public class OfficeSpaceApplication {

  @Generated
  public static void main(String[] args) {
    SpringApplication.run(OfficeSpaceApplication.class);
  }

}
