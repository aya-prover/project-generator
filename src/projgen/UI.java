package projgen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.Consumer;

import static projgen.Main.toPkgName;

public class UI {
  private JTextField projName;
  private JTextField classPrefix;
  private JTextField javaVersion;
  private JButton create;
  private JTextField outputPath;
  private JButton browse;
  private JPanel root;

  public JPanel getRoot() {
    return root;
  }

  public void configure(Consumer<Map<String, CharSequence>> apply) {
    create.addActionListener(e -> {
      var projNameText = projName.getText().toLowerCase();
      apply.accept(Map.ofEntries(
          Map.entry("javaVersion", javaVersion.getText()),
          Map.entry("classPrefix", classPrefix.getText()),
          Map.entry("project", projNameText),
          Map.entry("package", toPkgName(projNameText))
      ));
    });
  }
}
