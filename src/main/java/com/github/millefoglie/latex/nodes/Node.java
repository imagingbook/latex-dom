package com.github.millefoglie.latex.nodes;

import com.github.millefoglie.latex.visitors.Visitor;

import java.util.List;

/**
 * A LaTeX DOM tree node.
 */
public interface Node {

    public String stringify();
    public String getContent();
    public void setContent(String content);
    public List<Node> getChildren();
    public void setChildren(List<Node> children);
    public boolean addChild(Node child);
    public void accept(Visitor visitor);

}
