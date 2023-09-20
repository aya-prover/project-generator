package org.{| package |}.parser;

import com.intellij.psi.tree.IElementType;
import org.{| package |}.psi.{| classPrefix |}Language;

public class {| classPrefix |}PsiTokenType extends IElementType {
  public {| classPrefix |}PsiTokenType(String plus) {
    super(plus, {| classPrefix |}Language.INSTANCE);
  }
}
