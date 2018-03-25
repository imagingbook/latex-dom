package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.nodes.DisplayMathNode;
import com.github.millefoglie.latex.nodes.InlineMathNode;
import com.github.millefoglie.latex.nodes.TextNode;

import java.io.IOException;

/**
 * A parser state for processing math states which are opened and closed with
 * "$" or "$$".
 * <p>
 * Note that math nodes can also be created with corresponding commands, like
 * "\\(" or "\\]".
 */
class MathState implements ParserState {

    private static final MathState INSTANCE = new MathState();

    private MathState() {}

    static MathState getInstance() {
        return INSTANCE;
    }

    @Override
    public void process(LatexParser parser, char chr) throws IOException {
        if (!(parser.peek() instanceof InlineMathNode
                || parser.peek() instanceof DisplayMathNode)) {
            parser.flushAndCollapse();
        }

        if (chr == '$') {
            switch (parser.getBufferLength()) {
            case 0:
                if (parser.peek() instanceof InlineMathNode) {
                    ((InlineMathNode) parser.peek()).setContentOpening("$");
                    parser.setState(TextState.getInstance());
                    parser.clearBuffer();
                } else {
                    parser.push(new InlineMathNode());
                    ((InlineMathNode) parser.peek()).setContentClosing("$");
                    parser.appendToBuffer(chr);
                }

                break;
            case 1:
                assert (parser.peek() instanceof InlineMathNode) :
                String.format(
                        "Expected InlineMathNode at %s, but the top node was %s.",
                        parser.getPosition(),
                        parser.peek().getClass().getSimpleName());

                parser.pop();

                if (parser.peek() instanceof DisplayMathNode) {
                    ((DisplayMathNode) parser.peek()).setContentClosing("$$");
                    parser.clearBuffer();
                    parser.setState(TextState.getInstance());
                } else {
                    parser.push(new DisplayMathNode());
                    ((DisplayMathNode) parser.peek()).setContentOpening("$$");
                    parser.clearBuffer();
                }

                break;
            default:
                // do nothing
            }
        } else {
            parser.clearBuffer();
            parser.setState(TextState.getInstance());
            parser.push(new TextNode());
            parser.process(chr);
        }
    }

}
