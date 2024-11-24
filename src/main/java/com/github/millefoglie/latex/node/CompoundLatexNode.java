package com.github.millefoglie.latex.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompoundLatexNode extends AbstractLatexNode {
    private static final List<LatexNodeType> ALLOWED_TYPES = List.of(
            LatexNodeType.ROOT, LatexNodeType.BRACES, LatexNodeType.BRACKETS,
            LatexNodeType.CUSTOM
    );

    private LatexChildNode firstChild;
    private LatexChildNode lastChild;

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
    public LatexNode insertBefore(LatexChildNode newChild, LatexChildNode refChild) {
        checkChildPresent(refChild);

        if (newChild == refChild) {
            throw new LatexDomException("Cannot insert an already present node");
        }

        if (firstChild == null) {
            firstChild = newChild;
        }

        if (lastChild == null) {
            lastChild = newChild;
        }

        if (refChild == null) {
            if (lastChild == newChild) {
                return newChild;
            } else {
                lastChild.setNextSibling(newChild);
                newChild.setPreviousSibling(lastChild);
                newChild.setParent(this);
                lastChild = newChild;
                return newChild;
            }
        } else {
            LatexChildNode prev = refChild.getPreviousSibling();
            prev.setNextSibling(newChild);
            newChild.setPreviousSibling(prev);
            newChild.setNextSibling(refChild);
            refChild.setPreviousSibling(newChild);
            newChild.setParent(this);
            return newChild;
        }
    }

    private void checkChildPresent(LatexChildNode child) {
        if (child == null) {
            return;
        }

        for (LatexNode node = firstChild; node != null; node = node.getNextSibling()) {
            if (child == node) {
                return;
            }
        }

        String msg = String.format("Node %s is not a child node of %s", child, this);
        throw new LatexDomException(msg);
    }

    @Override
    public LatexNode appendChild(LatexChildNode child) {
        return insertBefore(child, null);
    }

    @Override
    public LatexChildNode getFirstChild() {
        return firstChild;
    }

    @Override
    public LatexChildNode getLastChild() {
        return lastChild;
    }

    public List<LatexChildNode> getChildren() {
        if (firstChild == null) {
            return Collections.emptyList();
        }

        LatexChildNode child = firstChild;
        List<LatexChildNode> children = new ArrayList<>();

        do {
            children.add(child);
            child = child.getNextSibling();
        } while (child != null);

        return children;
    }
}
