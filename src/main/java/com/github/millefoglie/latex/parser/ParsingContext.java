package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.lexer.LatexLexer;
import com.github.millefoglie.latex.lexer.LatexToken;
import com.github.millefoglie.latex.node.AbstractLatexNode;
import com.github.millefoglie.latex.node.LatexNode;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

class ParsingContext {
    private final LatexLexer lexer;
    private final ScopeStack scopeStack = new ScopeStack();
    private final Deque<LatexToken> tokenQueue = new LinkedList<>();
    private final StringBuilder contentBuilder = new StringBuilder();

    private boolean atLetterEnabled;

    public ParsingContext(LatexLexer lexer) {
        this.lexer = lexer;
    }

    public ScopeStack getScopeStack() {
        return scopeStack;
    }

    public LatexToken getNextToken() throws IOException {
        return tokenQueue.isEmpty() ? lexer.next() : tokenQueue.poll();
    }

    public void queueToken(LatexToken token) {
        tokenQueue.offer(token);
    }

    public void appendContent(String content) {
        contentBuilder.append(content);
    }

    public boolean hasContent() {
        return contentBuilder.length() > 0;
    }

    public String getContent() {
        return contentBuilder.toString();
    }

    public void clearContent() {
        contentBuilder.delete(0, contentBuilder.length());
    }

    public boolean isAtLetterEnabled() {
        return atLetterEnabled;
    }

    public void setAtLetterEnabled(boolean atLetterEnabled) {
        this.atLetterEnabled = atLetterEnabled;
    }

    public void emitNode(AbstractLatexNode node) throws LatexParserException {
        scopeStack.emitNode(node);
        updateScopeFrameFlags(node);
    }

    private void updateScopeFrameFlags(LatexNode node) {
        switch (node.getType()) {
        case COMMAND -> {
            scopeStack.getCurrentScopeFrame().setBracketsNodeAllowed(true);
        }
        case BRACES, BRACKETS, WHITESPACE -> {}
        default -> {
            scopeStack.getCurrentScopeFrame().setBracketsNodeAllowed(false);
        }
        }
    }
}
