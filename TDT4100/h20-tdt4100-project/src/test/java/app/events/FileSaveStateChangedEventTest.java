package app.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.Model;
import app.testing.EventTestTemplate;

public class FileSaveStateChangedEventTest extends EventTestTemplate {

  @Test
  @DisplayName("Check that model gets changed on constructor")
  public void checkModel() {
    new FileSaveStateChangedEvent(true);
    this.mockModel.verify(() -> Model.setFileIsSaved(true));
  }
  
}
