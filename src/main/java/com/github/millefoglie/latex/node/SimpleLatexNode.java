package com.github.millefoglie.latex.node;

import java.util.List;

public class SimpleLatexNode extends AbstractLatexNode {
    private static final List<LatexNodeType> ALLOWED_TYPES = List.of(
            LatexNodeType.TEXT, LatexNodeType.WHITESPACE, LatexNodeType.COMMAND, LatexNodeType.COMMENT,
            LatexNodeType.CARET, LatexNodeType.UNDERSCORE, LatexNodeType.AMPERSAND, LatexNodeType.AT,
            LatexNodeType.CUSTOM
    );

    private String content;

    public SimpleLatexNode(LatexNodeType type) {
        this(type, null);
    }

    public SimpleLatexNode(LatexNodeType type, String content) {
        super(type);
        this.content = content;
    }

    @Override
    protected void checkType(LatexNodeType type) {
        if (!ALLOWED_TYPES.contains(type)) {
            String msg = String.format("Cannot create %s node with type %s",
                                       getClass(), type);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
