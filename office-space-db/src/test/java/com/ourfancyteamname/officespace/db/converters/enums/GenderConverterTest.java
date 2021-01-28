package com.ourfancyteamname.officespace.db.converters.enums;


import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.enums.Gender;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class GenderConverterTest {

  @InjectMocks
  private GenderConverter genderConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String gender = genderConverter.convertToDatabaseColumn(Gender.MALE);
    assertEquals(gender, Gender.MALE.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String gender = genderConverter.convertToDatabaseColumn(null);
    assertNull(gender);
  }

  @Test
  void convertToEntityAttribute_success() {
    Gender gender = genderConverter.convertToEntityAttribute(Gender.MALE.name());
    assertEquals(Gender.MALE, gender);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    assertThrowIllegal(() -> genderConverter.convertToEntityAttribute("dang"));
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    Gender gender = genderConverter.convertToEntityAttribute(null);
    assertNull(gender);
  }
}
