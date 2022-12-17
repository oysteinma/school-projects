package app.events;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.Model;
import app.testing.EventTestTemplate;

public class OpenFileEventTest extends EventTestTemplate {

  @Test
  @DisplayName("Check that model gets changed on constructor")
  private void checkModel() {
      new OpenFileEvent(Optional.empty());
      this.mockModel.verify(() -> Model.setActiveFilePath(Optional.empty()));
  }
    
}
