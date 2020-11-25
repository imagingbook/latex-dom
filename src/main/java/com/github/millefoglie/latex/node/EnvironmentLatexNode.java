package com.github.millefoglie.latex.node;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentLatexNode extends CompoundLatexNode {
    private static final List<LatexNodeType> ALLOWED_TYPES = List.of(
            LatexNodeType.ENVIRONMENT, LatexNodeType.CUSTOM
    );

    private final List<LatexNode> opening = new ArrayList<>(1);
    private final List<LatexNode> closing = new ArrayList<>(2);

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

    public void appendOpening(LatexNode node) {
        opening.add(node);
    }

    public void appendClosing(LatexNode node) {
        closing.add(node);
    }
}
