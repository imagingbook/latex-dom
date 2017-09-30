package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for separators, like spaces, line-breaks and tabulations.
 * <p>
 * Used mainly for preserving spacing.
 */
public class SeparatorNode extends AbstractNode {

    public SeparatorNode() {}

    public SeparatorNode(String content) {
        super(content);
    }

}
