package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.nodes.SeparatorNode;

import java.io.IOException;

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
    public void process(LatexParser parser, char chr) throws IOException {
        if (!(parser.peek() instanceof SeparatorNode)) {
            parser.flushAndCollapse();
            parser.push(new SeparatorNode());
        }

        if (Character.isWhitespace(chr)) {
            parser.appendToBuffer(chr);
        } else {
            parser.setState(TextState.getInstance());
            parser.process(chr);
        }
    }

}
