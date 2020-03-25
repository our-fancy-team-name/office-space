package com.ourfancyteamname.officespace.enums;

import lombok.Getter;

@Getter
public enum DataBaseOperation {
  LESS_THAN,
  LESS_THAN_OR_EQUAL_TO,
  GREATER_THAN,
  GREATER_THAN_OR_EQUAL_TO,
  EQUAL,
  LIKE,
  NOT_EQUAL;
}
