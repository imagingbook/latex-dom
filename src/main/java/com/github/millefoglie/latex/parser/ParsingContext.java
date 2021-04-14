package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.lexer.LatexLexer;
import com.github.millefoglie.latex.lexer.LatexToken;
import com.github.millefoglie.latex.node.AbstractLatexNode;
import com.github.millefoglie.latex.node.LatexNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class ParsingContext {
    private final LatexLexer lexer;
    private final ScopeStack scopeStack = new ScopeStack();
    private final Deque<LatexToken> tokenQueue = new LinkedList<>();
    private final StringBuilder contentBuilder = new StringBuilder();

    private boolean atLetterEnabled;
    private boolean verbatim;

    public ParsingContext(LatexLexer lexer) {
        this.lexer = lexer;
    }

    public ScopeStack getScopeStack() {
        return scopeStack;
    }

    public LatexToken getNextToken() throws IOException {
        return tokenQueue.isEmpty() ? lexer.next() : tokenQueue.poll();
    }

    public List<LatexToken> lookAhead(int size) throws IOException {
        if (size < 1) {
            throw new IllegalArgumentException("Cannot look ahead non-positive number of tokens");
        }

        List<LatexToken> result = new ArrayList<>(size);
        LatexToken token;
        int i = 0;

        do {
            token = getNextToken();

            result.add(token);
            i++;
        } while ((token != null) && (i < size));

        for (int j = result.size() - 1; j >= 0; j--) {
            returnToQueue(result.get(j));
        }

        return result;
    }

    public void returnToQueue(LatexToken token) {
        tokenQueue.addFirst(token);
    }

    public void skipTokens(int size) throws IOException {
        if (size < 1) {
            throw new IllegalArgumentException("Cannot skip non-positive number of tokens");
        }

        LatexToken token;
        int i = 0;

        do {
            token = getNextToken();
            i++;
        } while ((token != null) && (i < size));
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

    public boolean isVerbatim() {
        return verbatim;
    }

    public void setVerbatim(boolean verbatim) {
        this.verbatim = verbatim;
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
