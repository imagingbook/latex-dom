package com.github.millefoglie.latex.node;

import java.util.ArrayList;
import java.util.List;

public class CompoundLatexNode extends AbstractLatexNode {
    private static final List<LatexNodeType> ALLOWED_TYPES = List.of(
            LatexNodeType.ROOT, LatexNodeType.BRACES, LatexNodeType.BRACKETS,
            LatexNodeType.CUSTOM
    );

    private final List<LatexNode> children = new ArrayList<>(0);

    public CompoundLatexNode(LatexNodeType type) {
        super(type);
    }

    @Override
    protected void checkType(LatexNodeType type) {
        if (!ALLOWED_TYPES.contains(type)) {
            String msg = String.format("Cannot create %s node with type %s",
                                       getClass(), type);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public void append(LatexNode child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public List<LatexNode> getChildren() {
        return children;
    }
}
