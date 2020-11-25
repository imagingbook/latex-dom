package com.github.millefoglie.latex.node;

import com.github.millefoglie.latex.visitor.LatexNodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An abstract LaTeX DOM node.
 * <p>
 * Represents some textual content, which can have different semantic meaning
 * depending on the node sub-type.
 */
public abstract class AbstractLatexNode implements LatexNode {
    private LatexNode parent;
    private final LatexNodeType type;

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
    public void setContent(String content) {}

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public void append(LatexNode child) {}

    @Override
    public List<LatexNode> getChildren() {
        return Collections.emptyList();
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
