package com.ourfancyteamname.officespace.db.entities;

import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Log {

  @Id
  protected ObjectId id;

  @Field(value = "user_id")
  int userId;

  @Field(value = "milliseconds_time")
  long millisecondsTime;

  @Field(value = "message")
  String message;
}
