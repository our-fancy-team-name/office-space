package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.postgres.entities.Account;
import com.ourfancyteamname.officespace.postgres.repos.AccountRepository;
import com.ourfancyteamname.officespace.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public List<Account> findAll() {
    return accountRepository.findAll();
  }
}
