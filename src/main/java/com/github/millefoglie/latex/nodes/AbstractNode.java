package com.github.millefoglie.latex.nodes;

import com.github.millefoglie.latex.visitors.Visitor;

/**
 * An abstract LaTeX DOM node.
 * <p>
 * Represents some textual content, which can have different semantic meaning
 * depending on the node sub-type.
 */
public abstract class AbstractNode implements Node {

    private String content;

    public AbstractNode() {}

    public AbstractNode(String content) {
        this.content = content;
    }

    /**
     * Produces a LaTeX string corresponding to this node.
     *
     * @return a LaTeX string corresponding to this node
     */
    @Override
    public String stringify() {
        return content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
