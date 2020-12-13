package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.AbstractLatexNode;
import com.github.millefoglie.latex.node.CompoundLatexNode;
import com.github.millefoglie.latex.node.LatexNode;
import com.github.millefoglie.latex.node.LatexNodeType;
import com.github.millefoglie.latex.node.SimpleLatexNode;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

class ScopeStack {
    private final Deque<ScopeFrame> scopeStack = new LinkedList<>();

    void openScope(CompoundLatexNode node) {
        Objects.requireNonNull(node);

        scopeStack.push(new ScopeFrame(node));
    }

    <T extends LatexNode> void closeScope(LatexNodeType nodeType) {
        Objects.requireNonNull(nodeType);

        if (scopeStack.isEmpty()) {
            throw new RuntimeException("No open scopes present");
        } else if ((scopeStack.size() == 1) && (nodeType != LatexNodeType.ROOT)) {
            throw new RuntimeException("Cannot close scope");
        }

        ScopeFrame currentScope = scopeStack.pop();
        CompoundLatexNode scopeNode = currentScope.getNode();

        if (nodeType == scopeNode.getType()) {
            if (scopeStack.isEmpty()) {
                appendAll(scopeNode, currentScope.getChildren());
                return;
            }

            ScopeFrame enclosingScope = scopeStack.peek();

            appendAll(scopeNode, currentScope.getChildren());
            enclosingScope.getChildren().add(scopeNode);
        } else {
            if (scopeStack.isEmpty()) {
                throw new RuntimeException("No open scopes present");
            } else if (scopeNode.getType() != LatexNodeType.BRACKETS) {
                throw new RuntimeException("Cannot close scope");
            }

            ScopeFrame enclosing = scopeStack.peek();

            if (enclosing == null) {
                throw new RuntimeException("Cannot close scope");
            }

            enclosing.getChildren().add(new SimpleLatexNode(LatexNodeType.TEXT, "["));
            enclosing.getChildren().addAll(currentScope.getChildren());
            closeScope(nodeType);
        }
    }

    ScopeFrame getCurrentScopeFrame() {
        return scopeStack.peek();
    }

    private void appendAll(LatexNode scopeNode, Collection<AbstractLatexNode> children) {
        for (AbstractLatexNode child : children) {
            scopeNode.appendChild(child);
        }
    }

    void emitNode(AbstractLatexNode node) {
        if (scopeStack.isEmpty()) {
            throw new RuntimeException("Cannot emit node");
        }

        scopeStack.peek().getChildren().add(node);
    }
}
