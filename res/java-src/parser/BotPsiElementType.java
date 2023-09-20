package org.{| package |}.parser;

import com.intellij.psi.tree.IElementType;
import org.{| package |}.psi.{| classPrefix |}Language;

public class {| classPrefix |}PsiElementType extends IElementType {
  public {| classPrefix |}PsiElementType(String plus) {
    super(plus, {| classPrefix |}Language.INSTANCE);
  }
}
