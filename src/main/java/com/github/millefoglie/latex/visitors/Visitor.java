package com.github.millefoglie.latex.visitors;

import com.github.millefoglie.latex.nodes.Node;

public interface Visitor {

    public void visit(Node node);

}
