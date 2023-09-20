package org.steeleagle.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.steeleagle.parser.BotPsiElementTypes.*;

%%

%{
  public _BotPsiLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _BotPsiLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

NUMBER=[0-9]+

%%
<YYINITIAL> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  "+"                 { return PLUS; }
  {NUMBER}            { return NUMBER; }
}

[^] { return BAD_CHARACTER; }
