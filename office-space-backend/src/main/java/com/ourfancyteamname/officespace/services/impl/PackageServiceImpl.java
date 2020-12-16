package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.PackageConverter;
import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PackageServiceImpl implements PackageService {

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private PackageConverter packageConverter;

  @Override
  public Package create(PackageDto packageDto) {
    Assert.isTrue(!packageRepository.existsBySerialNumber(packageDto.getSerialNumber()), ErrorCode.DUPLICATED.name());
    return packageRepository.save(packageConverter.toEntity(packageDto));
  }
}
