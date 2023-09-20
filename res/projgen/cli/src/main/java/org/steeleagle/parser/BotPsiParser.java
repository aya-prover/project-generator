// This is a generated file. Not intended for manual editing.
package org.steeleagle.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.steeleagle.parser.BotPsiElementTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class BotPsiParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return bro(b, l + 1);
  }

  /* ********************************************************** */
  // (PLUS | MINUS) NUMBER
  public static boolean addNumber(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addNumber")) return false;
    if (!nextTokenIs(b, "<add number>", MINUS, PLUS)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADD_NUMBER, "<add number>");
    r = addNumber_0(b, l + 1);
    r = r && consumeToken(b, NUMBER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PLUS | MINUS
  private static boolean addNumber_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addNumber_0")) return false;
    boolean r;
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    return r;
  }

  /* ********************************************************** */
  // NUMBER addNumber*
  static boolean bro(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bro")) return false;
    if (!nextTokenIs(b, NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMBER);
    r = r && bro_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // addNumber*
  private static boolean bro_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bro_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!addNumber(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "bro_1", c)) break;
    }
    return true;
  }

}
