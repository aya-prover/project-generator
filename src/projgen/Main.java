package projgen;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
  public static void main(String... args) throws IOException {
    var path = Paths.get("output");
    JarCompat.mkdirsDashP(path);
    JarCompat.write("template", path);
  }
}
