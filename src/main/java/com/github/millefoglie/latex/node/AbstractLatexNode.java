package com.github.millefoglie.latex.node;

import com.github.millefoglie.latex.visitor.LatexNodeVisitor;

import java.util.Objects;

/**
 * An abstract LaTeX DOM node.
 * <p>
 * Represents some textual content, which can have different semantic meaning
 * depending on the node sub-type.
 */
public abstract class AbstractLatexNode implements LatexNode, LatexChildNode {
    private LatexNode parent;
    private final LatexNodeType type;
    private LatexChildNode previousSibling;
    private LatexChildNode nextSibling;

    public AbstractLatexNode(LatexNodeType type) {
        Objects.requireNonNull(type);
        checkType(type);
        this.type = type;
    }

    protected void checkType(LatexNodeType type) {}

    @Override
    public LatexNodeType getType() {
        return type;
    }

    @Override
    public void setNextSibling(LatexChildNode nextSibling) {
        this.nextSibling = nextSibling;
    }

    @Override
    public void setPreviousSibling(LatexChildNode previousSibling) {
        this.previousSibling = previousSibling;
    }

    @Override
    public LatexChildNode getNextSibling() {
        return nextSibling;
    }

    @Override
    public LatexChildNode getPreviousSibling() {
        return previousSibling;
    }

    @Override
    public void setContent(String content) {}

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public LatexNode insertBefore(LatexChildNode newChild, LatexChildNode refChild) {
        return null;
    }

    @Override
    public LatexNode appendChild(LatexChildNode child) {
        return null;
    }

    @Override
    public LatexChildNode getFirstChild() {
        return null;
    }

    @Override
    public LatexChildNode getLastChild() {
        return null;
    }

    @Override
    public void setParent(LatexNode parent) {
        this.parent = parent;
    }

    @Override
    public LatexNode getParent() {
        return parent;
    }

    @Override
    public void accept(LatexNodeVisitor visitor) {
        visitor.visit(this);
    }
}
