package projgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.jar.JarFile;

public class Main {
  // https://stackoverflow.com/a/20073154/7083401
  public void write(final String path, Path out) throws IOException {
    var myClazz = getClass();
    final var jarFile = new File(myClazz.getProtectionDomain().getCodeSource().getLocation().getPath());

    if (jarFile.isFile()) {
      final var jar = new JarFile(jarFile);
      final var entries = jar.entries();
      while (entries.hasMoreElements()) {
        var element = entries.nextElement();
        final var name = element.getName();
        if (name.startsWith(path + "/")) {
          try (var inS = myClazz.getResourceAsStream("/" + name);
               var outS = Files.newOutputStream(out.resolve(name), StandardOpenOption.CREATE)) {
            assert inS != null;
            inS.transferTo(outS);
          }
        }
      }
      jar.close();
    } else { // Run with IDE
      final var url = myClazz.getResource("/" + path);
      if (url != null) try {
        var root = Paths.get(url.toURI());
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
          @Override public FileVisitResult
          visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            var resolve = out.resolve(root.relativize(file));
            var parent = resolve.getParent();
            if (Files.notExists(parent)) Files.createDirectories(parent);
            try (var inS = Files.newInputStream(file);
                 var outS = Files.newOutputStream(resolve, StandardOpenOption.CREATE)) {
              inS.transferTo(outS);
            }
            return FileVisitResult.CONTINUE;
          }
        });
      } catch (URISyntaxException ex) {
        // never happens
      }
    }
  }

  public static void main(String... args) throws IOException {
    var path = Paths.get("output");
    if (Files.notExists(path)) Files.createDirectories(path);
    new Main().write("template", path);
  }
}
