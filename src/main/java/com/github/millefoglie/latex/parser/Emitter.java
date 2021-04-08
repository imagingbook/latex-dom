package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.LatexChildNode;
import com.github.millefoglie.latex.node.LatexNode;

public interface Emitter {
    boolean canEmit(LatexNode parentNode, LatexChildNode childNode);
    void emit(LatexNode parentNode, LatexChildNode childNode) throws LatexParserException;
}
