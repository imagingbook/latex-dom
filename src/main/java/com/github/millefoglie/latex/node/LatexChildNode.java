package com.github.millefoglie.latex.node;

public interface LatexChildNode extends LatexNode {
    void setParent(LatexNode parent);
    void setNextSibling(LatexChildNode nextSibling);
    void setPreviousSibling(LatexChildNode previousSibling);
}
