package com.github.millefoglie.latex.document;

import com.github.millefoglie.latex.node.LatexNode;
import com.github.millefoglie.latex.node.LatexNodeType;

import java.util.Objects;

public class BasicLatexDocument implements LatexDocument {
    private final LatexNode root;

    public BasicLatexDocument(LatexNode root) {
        Objects.requireNonNull(root);

        if (root.getType() != LatexNodeType.ROOT) {
            throw new IllegalArgumentException("Root node required");
        }

        this.root = root;
    }

    @Override
    public LatexNode getRoot() {
        return root;
    }

}
