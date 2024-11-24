package com.github.millefoglie.latex.node;

import com.github.millefoglie.latex.visitor.LatexNodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A LaTeX DOM tree node.
 */
public interface LatexNode {
    LatexNodeType getType();
    void setContent(String content);
    String getContent();
    LatexNode insertBefore(LatexChildNode newChild, LatexChildNode refChild);
    LatexNode appendChild(LatexChildNode child);
    LatexChildNode getFirstChild();
    LatexChildNode getLastChild();
    LatexNode getParent();
    LatexChildNode getNextSibling();
    LatexChildNode getPreviousSibling();
    void accept(LatexNodeVisitor visitor);

    // by wilbur
    default String getString() {
        return (this.getClass().getSimpleName() +
                "(" +
                getType() + ", " +
                getContent() +
                ")");
    }

    default List<LatexNode> getSiblings() {
        List<LatexNode> sibs = new ArrayList<>();
        LatexNode cur = this.getNextSibling();
        while (cur != null) {
            sibs.add(cur);
            cur = this.getNextSibling();
        }
        return sibs;
    }

    // wilbur: copied from CompoundLatexNode
    default List<LatexNode> getChildrenWB() {
        LatexNode firstChild = this.getFirstChild();
        if (firstChild == null) {
            return Collections.emptyList();
        }

        LatexNode child = firstChild;
        List<LatexNode> children = new ArrayList<>();
        do {
            children.add(child);
            child = child.getNextSibling();
        } while (child != null);
        return children;
    }
}
