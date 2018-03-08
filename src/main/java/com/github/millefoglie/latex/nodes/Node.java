package com.github.millefoglie.latex.nodes;

import com.github.millefoglie.latex.visitors.Visitor;

import java.util.List;

/**
 * A LaTeX DOM tree node.
 */
public interface Node {

    void setContent(String content);
    String getContent();
    void setChildren(List<Node> children);
    List<Node> getChildren();
    void append(Node child);
    void setParent(Node parent);
    Node getParent();
    String stringify();
    void accept(Visitor visitor);

}
