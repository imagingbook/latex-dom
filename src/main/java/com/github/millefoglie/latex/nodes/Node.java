package com.github.millefoglie.latex.nodes;

import com.github.millefoglie.latex.visitors.Visitor;

/**
 * A LaTeX DOM tree node.
 */
public interface Node {

    public String stringify();
    public String getContent();
    public void setContent(String content);
    public void accept(Visitor visitor);

}
