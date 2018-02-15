package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for code enclosed in braces, such as command arguments.
 */
public class BracesNode extends AbstractNode{

    public BracesNode() {}

    @Override
    public String stringify() {
        return "{" + super.stringify() + "}";
    }

}
