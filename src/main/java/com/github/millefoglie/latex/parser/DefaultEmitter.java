package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.LatexChildNode;
import com.github.millefoglie.latex.node.LatexNode;

class DefaultEmitter implements Emitter {
    static final DefaultEmitter INSTANCE = new DefaultEmitter();

    @Override
    public boolean canEmit(LatexNode parentNode, LatexChildNode childNode) {
        return true;
    }

    @Override
    public void emit(LatexNode parentNode, LatexChildNode childNode) {
        parentNode.appendChild(childNode);
    }
}
