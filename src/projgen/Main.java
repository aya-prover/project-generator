package projgen;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.jar.JarFile;

public class Main {
  // https://stackoverflow.com/a/20073154/7083401
  public void write(final String path, Writer out) throws IOException {
    var myClazz = getClass();
    final var jarFile = new File(myClazz.getProtectionDomain().getCodeSource().getLocation().getPath());

    if (jarFile.isFile()) {
      final var jar = new JarFile(jarFile);
      final var entries = jar.entries();
      while (entries.hasMoreElements()) {
        var element = entries.nextElement();
        final var name = element.getName();
        if (name.startsWith(path + "/")) {
          System.out.println(name);
        }
      }
      jar.close();
    } else { // Run with IDE
      final var url = myClazz.getResource("/" + path);
      if (url != null) {
        try {
          final var apps = new File(url.toURI());
          for (var app : Objects.requireNonNull(apps.listFiles())) {
            System.out.println(app);
          }
        } catch (URISyntaxException ex) {
          // never happens
        }
      }
    }
  }

  public static void main(String... args) throws IOException {
    var path = Paths.get("out");
    if (Files.notExists(path)) Files.createDirectories(path);
  }
}
