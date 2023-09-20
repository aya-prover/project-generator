package org.{| package |}.psi;

import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.{| package |}.parser.{| classPrefix |}PsiParser;
import org.{| package |}.parser._{| classPrefix |}PsiLexer;

public abstract class {| classPrefix |}ParserDefinitionBase implements ParserDefinition {
  @Override public @NotNull Lexer createLexer(Project project) {
    return new FlexAdapter(new _{| classPrefix |}PsiLexer());
  }

  @Override public @NotNull PsiParser createParser(Project project) {
    return new {| classPrefix |}PsiParser();
  }

  @Override public @NotNull TokenSet getCommentTokens() {
    return TokenSet.EMPTY;
  }

  @Override public @NotNull TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }
}
