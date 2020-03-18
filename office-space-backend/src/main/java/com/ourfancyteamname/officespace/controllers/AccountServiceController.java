package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.postgres.entities.Account;
import com.ourfancyteamname.officespace.annotations.CanDeleteUser;
import com.ourfancyteamname.officespace.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/account")
public class AccountServiceController {

  @Autowired
  private AccountService accountService;

  @GetMapping
  public List<Account> getAll() {
    return accountService.findAll();
  }

  @CanDeleteUser
  @DeleteMapping(path = "/{id}")
  public void deleteUser(@PathVariable Integer id) {
    log.info("Deleted User");
  }
}
