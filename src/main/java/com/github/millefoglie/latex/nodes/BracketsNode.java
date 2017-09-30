package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for code enclosed in brackets, mainly commands optional
 * arguments.
 */
public class BracketsNode extends AbstractParentNode{

    public BracketsNode() {}

    @Override
    public String stringify() {
        return "[" + super.stringify() + "]";
    }

}
