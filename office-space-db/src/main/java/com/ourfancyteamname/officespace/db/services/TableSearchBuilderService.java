package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface TableSearchBuilderService<T> {

  T from(TableSearchRequest tableSearchRequest);

}
