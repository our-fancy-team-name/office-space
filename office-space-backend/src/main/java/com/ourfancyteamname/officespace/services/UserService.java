package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

  Page<UserDto> findAllByPaging(TableSearchRequest tableSearchRequest);
}
