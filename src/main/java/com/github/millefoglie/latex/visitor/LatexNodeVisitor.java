package com.github.millefoglie.latex.visitor;

import com.github.millefoglie.latex.node.LatexNode;

public interface LatexNodeVisitor {
    void visit(LatexNode node);
}
