package com.ourfancyteamname.officespace.db.converters.dtos;

import org.mapstruct.Mapper;

import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.dtos.PackageDto;

@Mapper
public interface PackageConverter {

  PackageDto toDto(Package aPackage);

  Package toEntity(PackageDto packageDto);
}
