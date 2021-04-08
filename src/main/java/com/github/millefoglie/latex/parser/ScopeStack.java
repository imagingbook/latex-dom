package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.AbstractLatexNode;
import com.github.millefoglie.latex.node.CompoundLatexNode;
import com.github.millefoglie.latex.node.LatexChildNode;
import com.github.millefoglie.latex.node.LatexNode;
import com.github.millefoglie.latex.node.LatexNodeType;
import com.github.millefoglie.latex.node.SimpleLatexNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

class ScopeStack {
    private final Deque<ScopeFrame> scopeStack = new LinkedList<>();

    void openScope(CompoundLatexNode node) {
        Objects.requireNonNull(node);
        ScopeFrame scopeFrame = new ScopeFrame(node);

        if (node.getType() == LatexNodeType.ENVIRONMENT) {
            scopeFrame.setBracketsNodeAllowed(true);
        }

        scopeStack.push(scopeFrame);
    }

    void closeScope(LatexNodeType nodeType) throws LatexParserException {
        Objects.requireNonNull(nodeType);

        if (scopeStack.isEmpty()) {
            throw new RuntimeException("No open scopes present");
        } else if ((scopeStack.size() == 1) && (nodeType != LatexNodeType.ROOT)) {
            throw new RuntimeException("Cannot close scope");
        }

        ensureScope(nodeType);

        ScopeFrame currentScope = scopeStack.pop();
        CompoundLatexNode scopeNode = currentScope.getNode();

        if (nodeType == scopeNode.getType()) {
            if (scopeStack.isEmpty()) {
                return;
            }

            ScopeFrame enclosingScope = scopeStack.peek();
            CompoundLatexNode enclosingScopeNode = enclosingScope.getNode();
            Emitter emitter = enclosingScope.getEmitter();

            if (emitter.canEmit(enclosingScopeNode, scopeNode)) {
                emitter.emit(enclosingScopeNode, scopeNode);
            } else {
                throw new IllegalStateException();  // TODO
            }
        }
    }

    void ensureScope(LatexNodeType nodeType) throws LatexParserException {
        if (scopeStack.isEmpty()) {
            throw new RuntimeException("No open scopes present");
        }

        ScopeFrame currentFrame = scopeStack.peek();
        CompoundLatexNode currentNode = currentFrame.getNode();

        if (currentNode.getType() == nodeType) {
            return;
        } else if (currentNode.getType() != LatexNodeType.BRACKETS) {
            throw new IllegalStateException("Cannot close scope");
        }

        scopeStack.pop();
        ScopeFrame enclosingFrame = scopeStack.peek();

        if (enclosingFrame == null) {
            throw new RuntimeException("Cannot close scope");
        }

        CompoundLatexNode enclosingNode = enclosingFrame.getNode();
        Emitter emitter = enclosingFrame.getEmitter();
        LatexChildNode openingBracketNode = new SimpleLatexNode(LatexNodeType.TEXT, "[");

        if (emitter.canEmit(enclosingNode, openingBracketNode)) {
            emitter.emit(enclosingNode, openingBracketNode);

            for (LatexChildNode e : currentNode.getChildren()) {
                emitter.emit(enclosingNode, e);
            }
        }
    }

    ScopeFrame getCurrentScopeFrame() {
        return scopeStack.peek();
    }

    void emitNode(AbstractLatexNode node) throws LatexParserException {
        if (scopeStack.isEmpty()) {
            throw new RuntimeException("Cannot emit node");
        }

        ScopeFrame scopeFrame = scopeStack.peek();
        Emitter emitter = scopeFrame.getEmitter();
        LatexNode parentNode = scopeFrame.getNode();

        if (emitter.canEmit(parentNode, node)) {
            emitter.emit(parentNode, node);
        } else {
            closeScope(parentNode.getType());
            emitNode(node);
        }
    }
}
