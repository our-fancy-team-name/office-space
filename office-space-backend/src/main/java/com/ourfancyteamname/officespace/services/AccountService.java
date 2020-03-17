package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.postgres.entities.Account;

import java.util.List;

public interface AccountService {

  List<Account> findAll();
}
