package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import app.model.Model;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class FileTreeOperationsTest {

  @TempDir
  File tmp;

  private void populateTmpDir() throws IOException {
    List<String> files = List.of(
      "test.java",
      "test.md",
      "test.txt",
      "test.unknown"
    );
    for (String f : files) {
      new File(tmp, f).createNewFile();
    }
  }

  private void createRecursiveSymlink() throws IOException {
    File dirs = new File(tmp, "test/innerFolder/");
    dirs.mkdirs();

    Path target = Paths.get(tmp.toPath().toString(), "test");
    Path link = Paths.get(tmp.toPath().toString(), "test/innerFolder/test");

    Files.createSymbolicLink(link, target);
  }

  @Test
  @DisplayName("Test generateTree")
  public void testGenerateTree() {
    try {
      populateTmpDir();
      createRecursiveSymlink();

      CheckBoxTreeItem<String> parent = new CheckBoxTreeItem<>("Parent");
      FiletreeOperations.generateTree(tmp, parent);
    } catch (IOException e) {
      fail(e);
    }

  }

  private void generateTreeItemsFromPathsAndChild(Path root, Path filePath, TreeItem<String> file) {
    Path traversalPath = filePath;
    TreeItem<String> child = file;
      while (!traversalPath.equals(root)) {
        traversalPath = traversalPath.getParent();
        TreeItem<String> parent = new TreeItem<>(traversalPath.getFileName().toString());
        parent.getChildren().add(child);
        child = parent;
      }
  }

  @Test
  @DisplayName("Test getPathOftreeItem")
  public void testGetPathOfTreeItem() {

    try (MockedStatic<Model> model = Mockito.mockStatic(Model.class)) {
      TreeItem<String> treeItem = new TreeItem<>("file.txt");

      model.when(Model::getProjectPath).thenReturn(Optional.empty());      
      assertThrows(IllegalStateException.class, () -> FiletreeOperations.getPathOfTreeItem(treeItem));

      Path root = Paths.get("/tmp");
      Path filePath = Paths.get("/tmp/testfolder/folder1/folder2/file.txt");
      generateTreeItemsFromPathsAndChild(root, filePath, treeItem);

      model.when(Model::getProjectPath).thenReturn(Optional.of(root));

      Path pathOfTreeItem = FiletreeOperations.getPathOfTreeItem(treeItem);
      assertEquals(filePath, pathOfTreeItem);

      Path illegalRoot = Paths.get("/var");
      model.when(Model::getProjectPath).thenReturn(Optional.of(illegalRoot));
      assertThrows(FileNotFoundException.class, () -> FiletreeOperations.getPathOfTreeItem(treeItem));

    } catch (FileNotFoundException e) {
      fail("Paths did not match!");
    }
  }
  
}
