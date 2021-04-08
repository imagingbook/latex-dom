package com.github.millefoglie.latex.node;

import java.util.List;

public class EnvironmentLatexNode extends CompoundLatexNode {
    private static final List<LatexNodeType> ALLOWED_TYPES = List.of(
            LatexNodeType.ENVIRONMENT, LatexNodeType.CUSTOM
    );

    private CompoundLatexNode opening;
    private CompoundLatexNode closing;

    public EnvironmentLatexNode(LatexNodeType type) {
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

    public CompoundLatexNode getOpening() {
        return opening;
    }

    public CompoundLatexNode getClosing() {
        return closing;
    }

    public void appendOpening(LatexChildNode node) {
        if (opening == null) {
            opening = new CompoundLatexNode(LatexNodeType.ROOT);
        }

        opening.appendChild(node);
    }

    public void appendClosing(LatexChildNode node) {
        if (closing == null) {
            closing = new CompoundLatexNode(LatexNodeType.ROOT);
        }

        closing.appendChild(node);
    }
}
