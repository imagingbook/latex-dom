package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for a generic text string.
 */
public class TextNode extends AbstractNode {

    public TextNode() {}

    public TextNode(String content) {
        super(content);
    }

}
