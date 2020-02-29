package com.ourfancyteamname.officespace.controller;

import com.ourfancyteamname.officespace.data.Account;
import com.ourfancyteamname.officespace.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountServiceController {

  @Autowired
  private AccountService accountService;

  @GetMapping
  public List<Account> getAll() {
    return accountService.findAll();
  }
}
