package dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessGeneralDtoTest {

  @Test
  void processGeneralDtoTest() {
    ProcessGeneralDto processGeneralDto = new ProcessGeneralDto(1, "code", "name", "description");
    assertEquals(1, processGeneralDto.getId());
    assertEquals("code", processGeneralDto.getCode());
    assertEquals("name", processGeneralDto.getName());
    assertEquals("description", processGeneralDto.getDescription());
  }
}
