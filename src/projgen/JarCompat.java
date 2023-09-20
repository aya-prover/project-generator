package projgen;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.jar.JarFile;

public interface JarCompat {
  static void mkdirsDashP(Path path) throws IOException {
    if (Files.notExists(path)) Files.createDirectories(path);
  }

  // https://stackoverflow.com/a/20073154/7083401
  static void write(final String path, Path out) throws IOException {
    var myClazz = JarCompat.class;
    final var jarFile = new File(myClazz.getProtectionDomain().getCodeSource().getLocation().getPath());

    if (jarFile.isFile()) {
      final var jar = new JarFile(jarFile);
      final var entries = jar.entries();
      while (entries.hasMoreElements()) {
        var element = entries.nextElement();
        final var name = element.getName();
        if (element.isDirectory()) continue;
        if (name.startsWith(path + "/")) {
          var resolve = out.resolve(name);
          mkdirsDashP(resolve.getParent());
          try (var inS = myClazz.getResourceAsStream("/" + name);
               var outS = Files.newOutputStream(resolve, StandardOpenOption.CREATE)) {
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
          @Override
          public FileVisitResult
          visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            var resolve = out.resolve(root.relativize(file));
            mkdirsDashP(resolve.getParent());
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
}
