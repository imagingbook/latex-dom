package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.document.BasicLatexDocument;
import com.github.millefoglie.latex.document.LatexDocument;
import com.github.millefoglie.latex.lexer.LatexLexer;
import com.github.millefoglie.latex.lexer.LatexToken;
import com.github.millefoglie.latex.lexer.LatexTokenType;
import com.github.millefoglie.latex.node.CompoundLatexNode;
import com.github.millefoglie.latex.node.EnvironmentLatexNode;
import com.github.millefoglie.latex.node.LatexNodeType;
import com.github.millefoglie.latex.node.MathLatexNode;
import com.github.millefoglie.latex.node.SimpleLatexNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * LaTeX DOM parser.
 */
public class DefaultLatexParser implements LatexParser {
    public DefaultLatexParser() {}

    @Override
    public LatexDocument parse(InputStream inputStream) throws LatexParserException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        LatexLexer lexer = new LatexLexer(reader);
        ParsingContext context = new ParsingContext(lexer);
        CompoundLatexNode root = new CompoundLatexNode(LatexNodeType.ROOT);

        context.getScopeStack().openScope(root);

        try {
            parseTokens(context);
        } catch (IOException e) {
            throw new LatexParserException(e);
        }

        context.getScopeStack().closeScope(LatexNodeType.ROOT);
        return new BasicLatexDocument(root);
    }

    private void parseTokens(ParsingContext context) throws IOException, LatexParserException {
        LatexToken token = context.getNextToken();
        ScopeStack scopeStack = context.getScopeStack();

        while (token != null) {
            switch (token.getType()) {
            case WHITESPACE -> {
                flushText(context);
                context.emitNode(new SimpleLatexNode(LatexNodeType.WHITESPACE, token.getValue()));
            }
            case BACKSLASH -> {
                flushText(context);
                parseBackslashChunk(context);
            }
            case OPENING_BRACE -> {
                flushText(context);
                scopeStack.openScope(new CompoundLatexNode(LatexNodeType.BRACES));
            }
            case CLOSING_BRACE -> {
                flushText(context);
                scopeStack.closeScope(LatexNodeType.BRACES);
            }
            case OPENING_BRACKET -> {
                flushText(context);

                if (scopeStack.getCurrentScopeFrame().isBracketsNodeAllowed()) {
                    scopeStack.openScope(new CompoundLatexNode(LatexNodeType.BRACKETS));
                } else {
                    appendText(context, token.getValue());
                }
            }
            case CLOSING_BRACKET -> {
                flushText(context);

                if (context.getScopeStack().getCurrentScopeFrame().getNode().getType() == LatexNodeType.BRACKETS) {
                    context.getScopeStack().closeScope(LatexNodeType.BRACKETS);
                } else {
                    appendText(context, token.getValue());
                }
            }
            case DOLLAR -> {
                flushText(context);
                parseDollar(context);
            }
            case CARET -> {
                flushText(context);
                context.emitNode(new SimpleLatexNode(LatexNodeType.CARET, token.getValue()));
            }
            case UNDERSCORE -> {
                flushText(context);
                context.emitNode(new SimpleLatexNode(LatexNodeType.UNDERSCORE, token.getValue()));
            }
            case PERCENT -> {
                flushText(context);
                parseComment(context);
            }
            case AMPERSAND -> {
                flushText(context);
                context.emitNode(new SimpleLatexNode(LatexNodeType.AMPERSAND, token.getValue()));
            }
//            case AT -> {
//                // TODO check latex rules
//                flushText();
//                context.emitNode(new SimpleLatexNode(LatexNodeType.AT, token.getValue()));
//            }
            default -> appendText(context, token.getValue());
            }

            token = context.getNextToken();
        }
    }

    private void appendText(ParsingContext context, String str) throws LatexParserException {
        if ((str == null) || (str.length() == 0)) {
            throw new IllegalArgumentException("Empty token value encountered");
        }

        if (Character.isLetterOrDigit(str.charAt(0))) {
            context.appendContent(str);
        } else {
            flushText(context);
            context.appendContent(str);
            flushText(context);
        }
    }

    private void flushText(ParsingContext context) throws LatexParserException {
        if (context.hasContent()) {
            context.emitNode(new SimpleLatexNode(LatexNodeType.TEXT, context.getContent()));
        }

        context.clearContent();
    }

    // TODO add environments handling
    private void parseBackslashChunk(ParsingContext context) throws IOException, LatexParserException {
        LatexToken token = context.getNextToken();

        if (token == null) {
            throw new LatexParserException("Expected command, but no more tokens present");
        }

        switch (token.getType()) {
        case OPENING_BRACKET -> {
            MathLatexNode mathNode = new MathLatexNode(LatexNodeType.DISPLAY_MATH);
            context.getScopeStack().openScope(mathNode);
        }
        case CLOSING_BRACKET -> {
            context.getScopeStack().closeScope(LatexNodeType.DISPLAY_MATH);
        }
        case OPENING_PARENTHESIS -> {
            MathLatexNode mathNode = new MathLatexNode(LatexNodeType.INLINE_MATH);
            context.getScopeStack().openScope(mathNode);
        }
        case CLOSING_PARENTHESIS -> {
            context.getScopeStack().closeScope(LatexNodeType.INLINE_MATH);
        }
        default -> {
            switch (token.getValue()) {
            case "begin":
                EnvironmentLatexNode envNode = new EnvironmentLatexNode(LatexNodeType.ENVIRONMENT);
                context.getScopeStack().openScope(envNode);
                context.getScopeStack()
                       .getCurrentScopeFrame()
                       .setEmitter(new EnvironmentNodeEmitter(envNode));
                break;
            case "end":
                context.getScopeStack().ensureScope(LatexNodeType.ENVIRONMENT);
            default:
                context.emitNode(new SimpleLatexNode(LatexNodeType.COMMAND, token.getValue()));
            }

            context.clearContent();
        }
        }
    }

    // TODO check if line endings are processed correctly
    private void parseComment(ParsingContext context) throws IOException, LatexParserException {
        LatexToken token = context.getNextToken();

        while ((token != null) && (token.getValue().indexOf('\n') < 0)) {
            context.appendContent(token.getValue());
            token = context.getNextToken();
        }

        if (token != null) {
            context.queueToken(token);
        }

        context.emitNode(new SimpleLatexNode(LatexNodeType.COMMENT, context.getContent()));
        context.clearContent();
    }

    private void parseDollar(ParsingContext context) throws IOException, LatexParserException {
        CompoundLatexNode scopeNode = context.getScopeStack().getCurrentScopeFrame().getNode();
        LatexToken nextToken = context.getNextToken();

        if (nextToken.getType() == LatexTokenType.DOLLAR) {
            if (scopeNode.getType() == LatexNodeType.INLINE_MATH) {
                throw new LatexParserException("Could not process dollar");
            } else if (scopeNode.getType() == LatexNodeType.DISPLAY_MATH) {
                ((MathLatexNode) scopeNode).setPlainTexClosing(true);
                context.getScopeStack().closeScope(LatexNodeType.DISPLAY_MATH);
            } else {
                MathLatexNode mathNode = new MathLatexNode(LatexNodeType.DISPLAY_MATH);
                mathNode.setPlainTexOpening(true);
                context.getScopeStack().openScope(mathNode);
            }
        } else {
            if (scopeNode.getType() == LatexNodeType.INLINE_MATH) {
                ((MathLatexNode) scopeNode).setPlainTexClosing(true);
                context.getScopeStack().closeScope(LatexNodeType.INLINE_MATH);
            } else if (scopeNode.getType() == LatexNodeType.DISPLAY_MATH) {
                throw new LatexParserException("Could not process dollar");
            } else {
                MathLatexNode mathNode = new MathLatexNode(LatexNodeType.INLINE_MATH);
                mathNode.setPlainTexOpening(true);
                context.getScopeStack().openScope(mathNode);
            }
        }

        if (nextToken.getType() != LatexTokenType.DOLLAR) {
            context.queueToken(nextToken);
        }
    }
}
