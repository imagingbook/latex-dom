package com.github.millefoglie.latex.node;

public class VerbatimLatexNode extends AbstractLatexNode {
    private String content;

    public VerbatimLatexNode() {
        this(null);
    }

    public VerbatimLatexNode(String content) {
        super(LatexNodeType.VERBATIM);
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
