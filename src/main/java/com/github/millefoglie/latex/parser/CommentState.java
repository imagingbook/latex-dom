package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.nodes.CommentNode;

import java.io.IOException;

/**
 * A parser state for processing comments, like "% comment".
 */
class CommentState implements ParserState {
    
    private static final CommentState INSTANCE = new CommentState();
    
    private CommentState() {}
    
    static CommentState getInstance() {
        return INSTANCE;
    }

    @Override
    public void process(LatexParser parser, char chr) throws IOException {
        if (!(parser.peek() instanceof CommentNode)) {
            parser.flushAndCollapse();
            parser.push(new CommentNode());
        }

        if ((chr == '\r') || (chr == '\n')) {
            parser.setState(SeparatorState.getInstance());
            parser.process(chr);
        } else {
            parser.appendToBuffer(chr);
        }
    }

}
