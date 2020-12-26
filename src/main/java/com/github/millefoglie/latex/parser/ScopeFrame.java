package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.CompoundLatexNode;

class ScopeFrame {
    private CompoundLatexNode node;
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

    public boolean isBracketsNodeAllowed() {
        return bracketsNodeAllowed;
    }

    public void setBracketsNodeAllowed(boolean bracketsNodeAllowed) {
        this.bracketsNodeAllowed = bracketsNodeAllowed;
    }
}
