package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for comments.
 */
public class CommentNode extends AbstractNode {

    public CommentNode() {}

    public CommentNode(String content) {
        super(content);
    }

}
