package com.ourfancyteamname.officespace.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.db.entities.Log;
import com.ourfancyteamname.officespace.db.repos.LogRepository;
import com.ourfancyteamname.officespace.db.services.LogService;

@Service
public class LogServiceImplementation implements LogService {

  @Autowired
  private LogRepository logRepository;

  @Override
  public Log insertLog(Log log) {
    return logRepository.save(log);
  }
}
