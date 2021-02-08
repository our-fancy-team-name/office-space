package com.ourfancyteamname.officespace.dtos;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RdfObject {
  private String namespace;
  private String localName;

  public RdfObject(String raw) {
    if (StringUtils.containsNone(raw, '#')) {
      this.namespace = raw;
      return;
    }
    String[] split = StringUtils.split(raw, "#");
    this.localName = split[split.length - 1];
    this.namespace = StringUtils.join(Arrays.copyOfRange(split, 0, split.length - 1), '#');
  }
}
