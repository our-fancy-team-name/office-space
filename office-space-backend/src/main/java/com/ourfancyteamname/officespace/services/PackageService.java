package com.ourfancyteamname.officespace.services;

import org.springframework.data.domain.Page;

import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.db.entities.view.PackageListView;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface PackageService {

  Package create(PackageDto productDto);

  Package update(PackageDto packageDto);

  void delete(Integer packageId);

  Page<PackageListView> getListView(TableSearchRequest tableSearchRequest);

}
