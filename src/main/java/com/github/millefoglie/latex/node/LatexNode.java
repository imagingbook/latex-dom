package com.github.millefoglie.latex.node;

import com.github.millefoglie.latex.visitor.LatexNodeVisitor;

/**
 * A LaTeX DOM tree node.
 */
public interface LatexNode {
    LatexNodeType getType();
    void setContent(String content);
    String getContent();
    LatexNode insertBefore(LatexChildNode newChild, LatexChildNode refChild);
    LatexNode appendChild(LatexChildNode child);
    LatexChildNode getFirstChild();
    LatexChildNode getLastChild();
    LatexNode getParent();
    LatexChildNode getNextSibling();
    LatexChildNode getPreviousSibling();
    void accept(LatexNodeVisitor visitor);
}
