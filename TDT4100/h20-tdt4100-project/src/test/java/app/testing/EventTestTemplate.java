package app.testing;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import app.model.Model;

public class EventTestTemplate extends FxTestTemplate {

  public MockedStatic<Model> mockModel;

  @BeforeEach
  public void openModel() {
    mockModel = mockStatic(Model.class);
  }

  @AfterEach
  public void closeModel() {
    mockModel.close();
  }
  
}
