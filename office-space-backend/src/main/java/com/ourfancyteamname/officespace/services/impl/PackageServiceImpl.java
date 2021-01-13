package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.PackageConverter;
import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.db.entities.view.PackageListView;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.db.repos.view.PackageListViewRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.CharConstants;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.enums.ErrorObject;
import com.ourfancyteamname.officespace.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PackageServiceImpl implements PackageService {

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private PackageConverter packageConverter;

  @Autowired
  private PackageListViewRepository packageListViewRepository;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private SortingService sortingService;

  @Autowired
  private ProcessListViewRepository processListViewRepository;

  @Override
  public Package create(PackageDto packageDto) {
    Assert.isTrue(!packageRepository.existsBySerialNumber(packageDto.getSerialNumber()), ErrorCode.DUPLICATED.name());
    return packageRepository.save(packageConverter.toEntity(packageDto));
  }

  @Override
  public Package update(PackageDto packageDto) {
    Package target = packageRepository.findById(packageDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));
    if (!packageRepository.findBySerialNumber(packageDto.getSerialNumber())
        .map(Package::getSerialNumber)
        .map(name -> name.equals(target.getSerialNumber()))
        .orElse(true)) {
      throw new IllegalArgumentException(
          String
              .join(CharConstants.DELIMITER.getValue(), ErrorObject.SERIAL.name(), ErrorCode.DUPLICATED.name()));
    }
    if (target.getProductId() != packageDto.getProductId()
        && processListViewRepository.existsBySerialAndClusterCurrentNotNull(target.getSerialNumber())) {
      throw new IllegalArgumentException(
          String.join(CharConstants.DELIMITER.getValue(), ErrorObject.CLUSTER.name(), ErrorCode.IN_USE.name()));
    }
    target.setDescription(packageDto.getDescription());
    target.setSerialNumber(packageDto.getSerialNumber());
    target.setProductId(packageDto.getProductId());
    return packageRepository.save(target);
  }

  @Override
  public void delete(Integer packageId) {
    packageRepository.deleteById(packageId);
  }

  @Override
  public Page<PackageListView> getListView(TableSearchRequest tableSearchRequest) {
    Specification<PackageListView> specification = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return packageListViewRepository.findAll(specification, pageable);
  }
}
