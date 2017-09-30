package com.github.millefoglie.latex.parser;

/**
 * A parser cursor position representing line and column indices.
 */
public class Position {

    private final int line;
    private final int column;

    public Position(int line, int column) {
        super();
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Line: " + line + ", Column: " + column;
    }

}
