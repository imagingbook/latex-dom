package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.nodes.BracesNode;
import com.github.millefoglie.latex.nodes.CommandNode;
import com.github.millefoglie.latex.nodes.TextNode;

import java.io.IOException;

/**
 * A parser state for processing simple text chunks, punctuation and other kinds
 * of nodes that do not fall under other states.
 * <p>
 * Note that parser can switch from this state to many others, depending on
 * the processed character.
 * Also, parser in this state can produce other kinds of nodes, in particular
 * braces, brackets and commands.
 */
class TextState implements ParserState {

    private static final TextState INSTANCE = new TextState();

    private TextState() {}

    static TextState getInstance() {
        return INSTANCE;
    }

    @Override
    public void process(LatexParser parser, char chr) throws IOException {
        if (!(parser.peek() instanceof TextNode)) {
            parser.flushAndCollapse();
            parser.push(new TextNode());
        }

        switch (chr) {
        case '%':
            parser.setState(CommentState.getInstance());
            parser.process(chr);
            break;
        case '\\':
            parser.setState(CommandState.getInstance());
            parser.process(chr);
            break;
        case '{':
            parser.flushAndCollapse();
            parser.push(new BracesNode());
            parser.push(new TextNode());
            break;
        case '}':
            parser.flushAndCollapse();

            assert (parser.peek() instanceof BracesNode) :
            String.format(
                    "Expected BracesNode at %s, but the top node was %s.",
                    parser.getPosition(),
                    parser.peek().getClass().getSimpleName());

            parser.collapse();
            parser.push(new TextNode());
            break;
        case '$':
            parser.setState(MathState.getInstance());
            parser.process(chr);
            break;
        case '_':
        case '^':
            parser.flushAndCollapse();
            parser.push(new CommandNode());
            parser.appendToBuffer(chr);
            parser.flushAndCollapse();
            parser.push(new TextNode());
            break;
        default:
            if (Character.isWhitespace(chr)) {
                parser.setState(SeparatorState.getInstance());
                parser.process(chr);
            } else {
                processAsText(parser, chr);
            }
        }
    }

    private void processAsText(LatexParser parser, char chr) {
        if (Character.isLetterOrDigit(chr)) {
            parser.appendToBuffer(chr);
        } else {
            parser.flushAndCollapse();
            parser.push(new TextNode());
            parser.appendToBuffer(chr);
            parser.flushAndCollapse();
            parser.push(new TextNode());
        }
    }

}
