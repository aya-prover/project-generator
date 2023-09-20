package org.{| package |}.psi;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public final class {| classPrefix |}Language extends Language {
  public static final @NotNull {| classPrefix |}Language INSTANCE = new {| classPrefix |}Language();

  private {| classPrefix |}Language() {
    super("{| classPrefix |}");
  }
}
