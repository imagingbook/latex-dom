package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.node.CompoundLatexNode;
import com.github.millefoglie.latex.node.EnvironmentLatexNode;
import com.github.millefoglie.latex.node.LatexChildNode;
import com.github.millefoglie.latex.node.LatexNode;
import com.github.millefoglie.latex.node.LatexNodeType;

import java.util.List;
import java.util.Optional;

public class EnvironmentNodeEmitter implements Emitter {
    private enum State {
        OPENING, CHILDREN, CLOSING
    }

    private static final List<LatexNodeType> ALLOWED_OPENING_TYPES = List.of(
            LatexNodeType.WHITESPACE, LatexNodeType.BRACES, LatexNodeType.BRACKETS,
            LatexNodeType.COMMENT, LatexNodeType.CUSTOM
    );

    private static final List<LatexNodeType> ALLOWED_CLOSING_TYPES = List.of(
            LatexNodeType.WHITESPACE, LatexNodeType.BRACES, LatexNodeType.COMMENT,
            LatexNodeType.CUSTOM
    );

    private final EnvironmentLatexNode envNode;
    private State state = State.OPENING;

    public EnvironmentNodeEmitter(EnvironmentLatexNode envNode) {
        this.envNode = envNode;
    }

    @Override
    public boolean canEmit(LatexNode parentNode, LatexChildNode childNode) {
        return (parentNode == envNode)
                && ((state != State.CLOSING) || canEmitClosing(childNode));
    }

    private boolean canEmitClosing(LatexChildNode childNode) {
        return ALLOWED_CLOSING_TYPES.contains(childNode.getType())
                && Optional.of(envNode)
                           .map(EnvironmentLatexNode::getClosing)
                           .map(CompoundLatexNode::getLastChild)
                           .map(LatexNode::getType)
                           .orElse(null) != LatexNodeType.BRACES;
    }

    @Override
    public void emit(LatexNode parentNode, LatexChildNode childNode) throws LatexParserException {
        if (parentNode != envNode) {
            throw new IllegalStateException();  // TODO
        }

        switch (state) {
        case OPENING:
            if (ALLOWED_OPENING_TYPES.contains(childNode.getType())) {
                envNode.appendOpening(childNode);
                break;
            } else {
                validateOpeningStatusExit();
                state = State.CHILDREN;
            }
        case CHILDREN:
            if ((childNode.getType() == LatexNodeType.COMMAND) && ("end".equals(childNode.getContent()))) {
                state = State.CLOSING;
            } else {
                envNode.appendChild(childNode);
            }
            break;
        case CLOSING:
            envNode.appendClosing(childNode);
            // TODO validate opening matches closing
        }
    }

    private void validateOpeningStatusExit() throws LatexParserException {
        CompoundLatexNode opening = envNode.getOpening();

        if ((opening == null) || (opening.getFirstChild() == null)) {
            throw new LatexParserException("Environment must have an opening with a name");    // TODO
        }

        LatexNode node = opening.getFirstChild();

        while ((node != null) && (node.getType() == LatexNodeType.WHITESPACE)) {
            node = node.getNextSibling();
        }

        if ((node == null) || (node.getType() != LatexNodeType.BRACES)) {
            throw new LatexParserException("Environment must have an opening with a name");    // TODO
        }
    }
}
