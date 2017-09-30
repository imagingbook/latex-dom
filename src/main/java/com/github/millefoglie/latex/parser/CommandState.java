package com.github.millefoglie.latex.parser;

import java.io.IOException;

import com.github.millefoglie.latex.nodes.CommandNode;
import com.github.millefoglie.latex.nodes.DisplayMathNode;
import com.github.millefoglie.latex.nodes.InlineMathNode;
import com.github.millefoglie.latex.nodes.TextNode;

/**
 * A parser state for processing commands, like "\cmd" and also sub-scripts _
 * and super-scripts ^.
 */
class CommandState implements ParserState {

    private static final CommandState INSTANCE = new CommandState();

    private CommandState() {}

    static CommandState getInstance() {
        return INSTANCE;
    }

    @Override
    public void process(LatexParser parser, String chr) throws IOException {
        if (!(parser.peek() instanceof CommandNode)) {
            parser.flushAndCollapse();
            parser.push(new CommandNode());
        }

        if (parser.getBufferLength() == 0) {
            assert ("\\".equals(chr));
            parser.appendToBuffer(chr);
        } else if (chr.matches("[\\p{L}]")) {
            parser.appendToBuffer(chr);
        } else if (parser.getBufferLength() == 1) {
            assert ("\\".equals(parser.getBufferContent()));

            switch (chr) {
            case "(":
                parser.pop();
                parser.push(new InlineMathNode());
                ((InlineMathNode) parser.peek()).setContentOpening("\\(");
                parser.push(new TextNode());
                parser.setState(TextState.getInstance());
                parser.clearBuffer();
                break;
            case ")":
                parser.pop();

                assert (parser.peek() instanceof InlineMathNode) :
                    String.format("Expected InlineMathNode at %s, but top node was %s.",
                                parser.getPosition(),
                                parser.peek().getClass().getSimpleName());

                ((InlineMathNode) parser.peek()).setContentClosing("\\)");
                parser.setState(TextState.getInstance());
                break;
            case "[":
                parser.pop();
                parser.push(new DisplayMathNode());
                ((DisplayMathNode) parser.peek()).setContentOpening("\\[");
                parser.push(new TextNode());
                parser.setState(TextState.getInstance());
                parser.clearBuffer();
                break;
            case "]":
                parser.pop();

                assert (parser.peek() instanceof DisplayMathNode) :
                    String.format("Expected DisplayMathNode at %s, but top node was %s",
                                parser.getPosition(),
                                parser.peek().getClass().getSimpleName());

                ((DisplayMathNode) parser.peek()).setContentClosing("\\]");
                parser.setState(TextState.getInstance());
                parser.clearBuffer();
                break;
            default:
                parser.appendToBuffer(chr);
                parser.setState(TextState.getInstance());
            }
        } else {
            parser.setState(TextState.getInstance());
            parser.process(chr);
        }
    }

}
