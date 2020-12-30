package dtos;

import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import org.junit.Assert;
import org.junit.Test;

public class ProcessGeneralDtoTest {

  @Test
  public void processGeneralDtoTest() {
    ProcessGeneralDto processGeneralDto = new ProcessGeneralDto(1, "code", "name", "description");
    Assert.assertEquals(1, processGeneralDto.getId());
    Assert.assertEquals("code", processGeneralDto.getCode());
    Assert.assertEquals("name", processGeneralDto.getName());
    Assert.assertEquals("description", processGeneralDto.getDescription());
  }
}
