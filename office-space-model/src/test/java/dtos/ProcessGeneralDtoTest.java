package dtos;

import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessGeneralDtoTest {

  @Test
  void processGeneralDtoTest() {
    ProcessGeneralDto processGeneralDto = new ProcessGeneralDto(1, "code", "name", "description");
    Assertions.assertEquals(1, processGeneralDto.getId());
    Assertions.assertEquals("code", processGeneralDto.getCode());
    Assertions.assertEquals("name", processGeneralDto.getName());
    Assertions.assertEquals("description", processGeneralDto.getDescription());
  }
}
