package org.steeleagle.parser;

import com.intellij.psi.tree.IElementType;
import org.steeleagle.psi.BotLanguage;

public class BotPsiElementType extends IElementType {
  public BotPsiElementType(String plus) {
    super(plus, BotLanguage.INSTANCE);
  }
}
