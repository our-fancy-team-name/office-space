package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageConverter {

  PackageDto toDto(Package packageDto);

  Package toEntity(PackageDto packageDto);
}
