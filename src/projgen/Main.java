package projgen;

import kala.template.TemplateEngine;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
  public static void main(String... args) throws IOException {
    var path = Paths.get("output");
    gen(path, "canonical");
  }

  private static void gen(Path path, String projName) throws IOException {
    projName = projName.toLowerCase();
    doGen(path, Map.ofEntries(
        Map.entry("javaVersion", "17"),
        Map.entry("classPrefix", "SExpr"),
        Map.entry("project", projName),
        Map.entry("package", toPkgName(projName))
    ));
  }

  private static void doGen(
      Path path,
      Map<String, Object> map
  ) throws IOException {
    var templateEngine = TemplateEngine.builder()
        .tag("{| ", " |}")
        .build();
    JarCompat.mkdirsDashP(path);
    JarCompat.write("template", path, ".glavo-kala", (inS, outS, applyTemplate) -> {
      if (!applyTemplate) inS.transferTo(outS);
      else try (var input = new InputStreamReader(inS);
                var output = new OutputStreamWriter(outS)) {
        templateEngine.process(input, output, map);
      }
    });
  }

  private static CharSequence toPkgName(String projectName) {
    return projectName.chars()
        .filter(Character::isAlphabetic)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
  }
}
