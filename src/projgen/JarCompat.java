package projgen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.jar.JarFile;

public interface JarCompat {
  static void mkdirsDashP(Path path) throws IOException {
    if (Files.notExists(path)) Files.createDirectories(path);
  }

  @FunctionalInterface
  interface IOConsumer {
    void transfer(InputStream inS, OutputStream outS, boolean applyTemplate) throws IOException;
  }

  /**
   * <a href="https://stackoverflow.com/a/20073154/7083401">source</a>
   *
   * @param path relative path in the resource root
   * @param out  output root
   * @param pref class name prefix that we need to rename things into
   * @param f    callback for transferring stream content
   */
  static void write(final String path, Path out, String pref, IOConsumer f) throws IOException {
    var myClazz = JarCompat.class;
    final var jarFile = new File(myClazz.getProtectionDomain().getCodeSource().getLocation().getPath());

    record Preprocess(Path resolve, boolean applyTemplate) {
    }
    class $ {
      Preprocess preprocess(Path resolve) throws IOException {
        var parent = resolve.getParent();
        mkdirsDashP(parent);
        var applyTemplate = false;
        var fileName = resolve.getFileName().toString();
        if (fileName.endsWith(Main.KALA)) {
          applyTemplate = true;
          resolve = parent.resolve(fileName.replace(Main.KALA, ""));
        }
        if (fileName.startsWith(Main.PREFIX)) {
          applyTemplate = true;
          resolve = parent.resolve(fileName.replace(Main.PREFIX, pref));
        }
        return new Preprocess(resolve, applyTemplate);
      }
    }

    if (jarFile.isFile()) {
      final var jar = new JarFile(jarFile);
      final var entries = jar.entries();
      while (entries.hasMoreElements()) {
        var element = entries.nextElement();
        final var name = element.getName();
        if (element.isDirectory()) continue;
        if (name.startsWith(path + "/")) {
          var result = new $().preprocess(out.resolve(name));
          try (var inS = myClazz.getResourceAsStream("/" + name);
               var outS = Files.newOutputStream(result.resolve, StandardOpenOption.CREATE)) {
            assert inS != null;
            f.transfer(inS, outS, result.applyTemplate);
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
            var result = new $().preprocess(out.resolve(root.relativize(file)));
            try (var inS = Files.newInputStream(file);
                 var outS = Files.newOutputStream(result.resolve, StandardOpenOption.CREATE)) {
              f.transfer(inS, outS, result.applyTemplate);
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
