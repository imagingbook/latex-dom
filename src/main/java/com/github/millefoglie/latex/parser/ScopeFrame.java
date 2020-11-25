package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.CompoundLatexNode;
import com.github.millefoglie.latex.node.LatexNode;

import java.util.LinkedList;
import java.util.List;

class ScopeFrame {
    private CompoundLatexNode node;
    private final List<LatexNode> children = new LinkedList<>();
    private boolean bracketsNodeAllowed;

    public ScopeFrame(CompoundLatexNode node) {
        this.node = node;
    }

    public CompoundLatexNode getNode() {
        return node;
    }

    public void setNode(CompoundLatexNode node) {
        this.node = node;
    }

    public List<LatexNode> getChildren() {
        return children;
    }

    public boolean isBracketsNodeAllowed() {
        return bracketsNodeAllowed;
    }

    public void setBracketsNodeAllowed(boolean bracketsNodeAllowed) {
        this.bracketsNodeAllowed = bracketsNodeAllowed;
    }
}
