package app.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import app.model.Model;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class containing operations for handling files withing a GUI filetree
 */
public class FiletreeOperations {

  private static int iconSize = 20;

  private FiletreeOperations() {}

  /**
   * Generate a filetree recursively, find icons for every file and append them to
   * the root tree node.
   * 
   * @param file The root directory to be at the top of the tree
   * @param parent The root tree item to append children to
   */
  public static void generateTree(File file, CheckBoxTreeItem<String> parent) {

    Image folder = new Image(FiletreeOperations.class.getResourceAsStream("/graphics/folder.png"));

    if (file.isDirectory()) {
      ImageView icon = new ImageView(folder);
      icon.setFitHeight(iconSize);
      icon.setFitWidth(iconSize);

      CheckBoxTreeItem<String> element = new CheckBoxTreeItem<>(file.getName(), icon);
      parent.getChildren().add(element);

      List<File> dirList = new ArrayList<>();
      List<File> fileList = new ArrayList<>();

      sortFiles(dirList, fileList, file);

      for (File f : dirList) {
        generateTree(f, element);
      }

    } else {

      try {
        ImageView icon = new ImageView(getIconForFile(file));
        icon.setFitHeight(iconSize);
        icon.setFitWidth(iconSize);

        CheckBoxTreeItem<String> element = new CheckBoxTreeItem<>(file.getName(), icon);
        parent.getChildren().add(element);

      } catch (Exception e) {
        System.err.println("[ERROR] Default file icon not found");
        System.err.println(e);
      }
    }
  }

  /**
   * A helping function to sort the files/directories in the fileTree so that it shows
   * in the correct order.
   * 
   * @param dirList The list of directories to append all directories to
   * @param fileList The list of files to append all the files to
   * @param file The directory of which children to sort
   */
  private static void sortFiles(List<File> dirList, List<File> fileList, File file) {
    for (File f : file.listFiles()) {
      if (f.isDirectory())
        dirList.add(f);
      else
        fileList.add(f);
    }
    dirList.addAll(fileList);

  }

  /**
   * A function to get an icon for a file based on its mimetype.
   * If no such icon is found, the function will return a default icon
   * 
   * @param file The file to probe for a mimetype
   * @return An image containing the icon
   * @throws IOException if the default icon was not found. This should not happen
   */
  private static Image getIconForFile(File file) throws IOException {
    Image icon;

    try {
      String mimeType = Files.probeContentType(file.toPath());
      if (mimeType == null) throw new IOException();

      String iconPath = "/graphics/filetreeicons/" + mimeType.replace('/', '-') + ".png";
      InputStream imageData = FiletreeOperations.class.getResourceAsStream(iconPath);
      if (imageData == null) throw new IOException();

      icon = new Image(imageData);

    } catch (IOException e) {
      System.err.println("[WARNING] Icon not found: " + file.getPath());

      // String iconPath = "/graphics/filetreeicons/file.png";
      String iconPath = "/graphics/filetreeicons/unknown.png";

      InputStream imageData = FileOperations.class.getResourceAsStream(iconPath);
      if (imageData == null) throw new IOException();

      icon = new Image(imageData);
    }
      
    return icon;
  }

  /**
   * Determine the absolute path of a file within the filetree.
   * @param item The treeitem that represents the file of which path is needed
   * @return The absolute path of the file that the treeitem represents
   * @throws FileNotFoundException if no path was found within the filetree such that the
   *         item could be connected with the root tree item. This should not happen
   */
  public static Path getPathOfTreeItem(TreeItem<String> item) throws FileNotFoundException {
    Path projectPath = 
      Model
        .getProjectPath()
        .orElseThrow(() -> new IllegalStateException());

    final String rootDirName =
      projectPath
        .getFileName()
        .toString();

    String path = "";
    while (!rootDirName.equals(item.getValue())) {
      path = File.separator + item.getValue() + path;
      item = item.getParent();
      if (item == null)
        throw new FileNotFoundException();
    }

    path = projectPath.toString() + path;

    Path pathToString = Paths.get(path);

    return pathToString;
  }  
}
