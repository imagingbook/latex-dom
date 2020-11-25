package com.github.millefoglie.latex.node;

import com.github.millefoglie.latex.visitor.LatexNodeVisitor;

import java.util.List;

/**
 * A LaTeX DOM tree node.
 */
public interface LatexNode {
    LatexNodeType getType();
    void setContent(String content);
    String getContent();
    void append(LatexNode child);
    List<LatexNode> getChildren();
    void setParent(LatexNode parent);
    LatexNode getParent();
    void accept(LatexNodeVisitor visitor);
}
