package com.ourfancyteamname.officespace.db.converters.enums;


import com.ourfancyteamname.officespace.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenderConverterTest {

  @InjectMocks
  private GenderConverter genderConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String gender = genderConverter.convertToDatabaseColumn(Gender.MALE);
    Assertions.assertEquals(gender, Gender.MALE.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String gender = genderConverter.convertToDatabaseColumn(null);
    Assertions.assertNull(gender);
  }

  @Test
  void convertToEntityAttribute_success() {
    Gender gender = genderConverter.convertToEntityAttribute(Gender.MALE.name());
    Assertions.assertEquals(Gender.MALE, gender);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> genderConverter.convertToEntityAttribute("dang")
    );
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    Gender gender = genderConverter.convertToEntityAttribute(null);
    Assertions.assertNull(gender);
  }
}
