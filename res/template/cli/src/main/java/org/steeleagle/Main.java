package org.steeleagle;

import kala.collection.immutable.ImmutableSeq;
import org.aya.intellij.GenericNode;
import org.steeleagle.parser.BotPsiElementTypes;
import org.steeleagle.psi.DslParserImpl;
import org.steeleagle.psi.StreamReporter;

public class Main {
  public static void main(String[] args) {
    var node = new DslParserImpl(new StreamReporter(System.out))
        .parseNode("code : here");
    System.out.println(node.toDebugString());
  }
}
