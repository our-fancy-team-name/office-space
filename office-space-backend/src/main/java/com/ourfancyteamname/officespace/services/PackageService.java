package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.dtos.PackageDto;

public interface PackageService {

  Package create(PackageDto productDto);
  
}
