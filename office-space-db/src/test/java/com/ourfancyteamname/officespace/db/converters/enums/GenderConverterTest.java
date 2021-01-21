package com.ourfancyteamname.officespace.db.converters.enums;


import com.ourfancyteamname.officespace.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GenderConverterTest {

  @InjectMocks
  private GenderConverter genderConverter;

  @Test
  public void convertToDatabaseColumn_success() {
    String gender = genderConverter.convertToDatabaseColumn(Gender.MALE);
    Assertions.assertEquals(gender, Gender.MALE.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String gender = genderConverter.convertToDatabaseColumn(null);
    Assertions.assertEquals(null, gender);
  }

  @Test
  public void convertToEntityAttribute_success() {
    Gender gender = genderConverter.convertToEntityAttribute(Gender.MALE.name());
    Assertions.assertEquals(Gender.MALE, gender);
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void convertToEntityAttribute_errorInvalid() {
//    genderConverter.convertToEntityAttribute("dang");
//  }

  @Test
  public void convertToEntityAttribute_errorNull() {
    Gender gender = genderConverter.convertToEntityAttribute(null);
    Assertions.assertEquals(null, gender);
  }
}
