package com.ourfancyteamname.officespace.postgres.converters;


import com.ourfancyteamname.officespace.postgres.enums.Gender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GenderConverterTest {

  private Gender.HibernateConverter genderConverter = new Gender.HibernateConverter();


  @Test
  public void convertToDatabaseColumn_success() {
    String gender = genderConverter.convertToDatabaseColumn(Gender.MALE);
    Assert.assertEquals(gender, Gender.MALE.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String gender = genderConverter.convertToDatabaseColumn(null);
    Assert.assertEquals(null, gender);
  }

  @Test
  public void convertToEntityAttribute_success() {
    Gender gender = genderConverter.convertToEntityAttribute(Gender.MALE.name());
    Assert.assertEquals(Gender.MALE, gender);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorInvalid() {
    Gender gender = genderConverter.convertToEntityAttribute("dang");
    Assert.assertEquals(Gender.MALE, gender);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorNull() {
    Gender gender = genderConverter.convertToEntityAttribute(null);
    Assert.assertEquals(Gender.MALE, gender);
  }
}
