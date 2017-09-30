package com.github.millefoglie.latex.parser;

import java.io.IOException;

import com.github.millefoglie.latex.nodes.SeparatorNode;

/**
 * A parser state for processing separators, such as line breaks, spaces and
 * tabs.
 */
class SeparatorState implements ParserState {
    
    private static final SeparatorState INSTANCE = new SeparatorState();

    private SeparatorState() {}
    
    static SeparatorState getInstance() {
        return INSTANCE;
    }

    @Override
    public void process(LatexParser parser, String chr) throws IOException {
        if (!(parser.peek() instanceof SeparatorNode)) {
            parser.flushAndCollapse();
            parser.push(new SeparatorNode());
        }

        if (chr.matches("\\s")) {
            parser.appendToBuffer(chr);
        } else {
            parser.setState(TextState.getInstance());
            parser.process(chr);
        }
    }

}
