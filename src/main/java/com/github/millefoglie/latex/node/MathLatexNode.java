package com.github.millefoglie.latex.node;

import java.util.List;

public class MathLatexNode extends CompoundLatexNode {
    private static final List<LatexNodeType> ALLOWED_TYPES = List.of(
            LatexNodeType.DISPLAY_MATH, LatexNodeType.INLINE_MATH, LatexNodeType.CUSTOM
    );

    private boolean plainTexOpening;
    private boolean plainTexClosing;

    public MathLatexNode(LatexNodeType type) {
        super(type);
    }

    @Override
    protected void checkType(LatexNodeType type) {
        if (!ALLOWED_TYPES.contains(type)) {
            String msg = String.format("Cannot create %s node with type %s",
                                       getClass(), type);
            throw new IllegalArgumentException(msg);
        }
    }

    public boolean isPlainTexOpening() {
        return plainTexOpening;
    }

    public void setPlainTexOpening(boolean plainTexOpening) {
        this.plainTexOpening = plainTexOpening;
    }

    public boolean isPlainTexClosing() {
        return plainTexClosing;
    }

    public void setPlainTexClosing(boolean plainTexClosing) {
        this.plainTexClosing = plainTexClosing;
    }
}
